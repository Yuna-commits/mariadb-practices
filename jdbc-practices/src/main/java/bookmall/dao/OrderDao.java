package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.OrderBookVo;
import bookmall.vo.OrderVo;

public class OrderDao {

	public int insert(OrderVo vo) {
		int count = 0;

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement(
					"insert into orders(user_no, number, payment, shipping, status) values(?, ?, ?, ?, ?)");
			PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id()");
		) {
			// INSERT
			pstmt1.setLong(1, vo.getUserNo());
			pstmt1.setString(2, vo.getNumber());
			pstmt1.setLong(3, vo.getPayment());
			pstmt1.setString(4, vo.getShipping());
			pstmt1.setString(5, vo.getStatus());

			// SQL 쿼리를 DB에 실행
			count = pstmt1.executeUpdate();

			// SELECT LAST_INSERT_ID(자신의 pk_no)
			ResultSet rs = pstmt2.executeQuery();
			vo.setNo(rs.next() ? rs.getLong(1) : null);
			rs.close();
		} catch (SQLException e) {
			System.err.println("DB 연결에 실패했습니다.");
			System.err.println("오류: " + e.getMessage());
		}

		return count;
	}
	
	public int insertBook(OrderBookVo bookVo) {
		int count = 0;

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement(
					"insert into orders_book(orders_no, book_no, quantity, price) values(?, ?, ?, ?)");
			PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id()");
		) {
			// INSERT
			pstmt1.setLong(1, bookVo.getOrderNo());
			pstmt1.setLong(2, bookVo.getBookNo());
			pstmt1.setInt(3, bookVo.getQuantity());
			pstmt1.setInt(4, bookVo.getPrice());

			// SQL 쿼리를 DB에 실행
			count = pstmt1.executeUpdate();

			// SELECT LAST_INSERT_ID(자신의 pk_no)
			ResultSet rs = pstmt2.executeQuery();
			bookVo.setNo(rs.next() ? rs.getLong(1) : null);
			rs.close();
		} catch (SQLException e) {
			System.err.println("DB 연결에 실패했습니다.");
			System.err.println("오류: " + e.getMessage());
		}

		return count;
	}
	
	// 인자로 받은 orders의 no와 user_no로 회원의 주문내역 조회
	public OrderVo findByNoAndUserNo(long no, Long userNo) {
		OrderVo result = null;

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select orders.number, user.name, user.email, orders.status, orders.payment, orders.shipping "
					+ "from orders join user on orders.user_no = user.no "
					+ "where orders.no = ? and user.no = ?");
		) {
			pstmt.setLong(1, no);
			pstmt.setLong(2, userNo);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {// 1건만 반환
				String number = rs.getString(1);
				String userName = rs.getString(2);
				String userEmail = rs.getString(3);
				String Status = rs.getString(4);
				int payment = rs.getInt(5);
				String shipping = rs.getString(6);

				result = new OrderVo();
				result.setNumber(number);
				result.setUserName(userName);
				result.setUserEmail(userEmail);
				result.setStatus(Status);
				result.setPayment(payment);
				result.setShipping(shipping);
			}

			rs.close();
		} catch (SQLException e) {
			System.err.println("DB 연결에 실패했습니다.");
			System.err.println("오류: " + e.getMessage());
		}

		return result;
	}

	// 인자로 받은 orders의 no와 user_no로 회원의 주문도서 내역 조회
	public List<OrderBookVo> findBooksByNoAndUserNo(Long no, Long userNo) {
		List<OrderBookVo> result = new ArrayList<OrderBookVo>();

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select orders.no, book.no, book.title, orders_book.quantity, orders_book.price "
					+ "from orders_book join book on orders_book.book_no = book.no join orders on orders_book.orders_no = orders.no "
					+ "where orders.no = ? and orders.user_no = ?");
		) {
			pstmt.setLong(1, no);
			pstmt.setLong(2, userNo);

			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long orderNo = rs.getLong(1);
				Long bookNo = rs.getLong(2);
				String bookTitle = rs.getString(3);
				int quantity = rs.getInt(4);
				int price = rs.getInt(5);
				
				OrderBookVo vo = new OrderBookVo();
				vo.setOrderNo(orderNo);
				vo.setBookNo(bookNo);
				vo.setBookTitle(bookTitle);
				vo.setQuantity(quantity);
				vo.setPrice(price);
				
				result.add(vo);
			}
			
			rs.close();
		} catch (SQLException e) {
			System.err.println("DB 연결에 실패했습니다.");
			System.err.println("오류: " + e.getMessage());
		}

		return result;
	}

	// Driver 로딩, Connection 연결 처리
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.0.181:3306/bookmall";
			conn = DriverManager.getConnection(url, "bookmall", "bookmall");

		} catch (ClassNotFoundException e) {
			System.err.println("JDBC 드라이버 클래스를 찾을 수 없습니다.");
			System.err.println("오류: " + e.getMessage());
		}

		return conn;
	}

}
