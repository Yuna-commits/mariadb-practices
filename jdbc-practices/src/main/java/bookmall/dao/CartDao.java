package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.CartVo;

public class CartDao {

	public int insert(CartVo vo) {
		int count = 0;

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("insert into cart(user_no, book_no, quantity) values(?, ?, ?)");
			PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id()");
		) {
			// INSERT
			pstmt1.setLong(1, vo.getUserNo());
			pstmt1.setLong(2, vo.getBookNo());
			pstmt1.setLong(3, vo.getQuantity());

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
	
	// 인자로 들어온 user의 no로 회원의 장바구니 조회
	public List<CartVo> findByUserNo(Long userNo) {
		List<CartVo> result = new ArrayList<CartVo>();
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("select book.no, book.title, cart.quantity, book.price "
							+ "from book join cart on book.no = cart.book_no join user on cart.user_no = user.no "
							+ "where cart.user_no = ?");
		){
			pstmt.setLong(1, userNo);

			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Long bookNo = rs.getLong(1);
				String bookTitle = rs.getString(2);
				int quantity = rs.getInt(3);
				int bookPrice = rs.getInt(4);

				CartVo vo = new CartVo();
				vo.setBookNo(bookNo);
				vo.setBookTitle(bookTitle);
				vo.setQuantity(quantity);
				vo.setBookPrice(bookPrice);

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
