package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bookmall.vo.BookVo;

public class BookDao {

	public int insert(BookVo vo) {
		int count = 0;

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("insert into book(category_no, title, price) values(?, ?, ?)");
			PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id()");
		) {
			// Parameter Binding
			// Test setup에서 BookVo에 CategoryNo를 미리 저장했기 때문에 가능
			pstmt1.setInt(1, vo.getCategoryNo());
			pstmt1.setString(2, vo.getTitle());
			pstmt1.setInt(3, vo.getPrice());

			// SQL 쿼리를 DB에 실행
			count = pstmt1.executeUpdate();

			// SELECT LAST_INSERT_ID(자신의 pk_no)
			// book은 orders_book, cart와 1 : N 관계, OrderBookVo, CartVo의 bookNo를 위해 필요
			ResultSet rs = pstmt2.executeQuery();
			vo.setNo(rs.next() ? rs.getLong(1) : null);
			rs.close();
		} catch (SQLException e) {
			System.err.println("DB 연결에 실패했습니다.");
			System.err.println("오류: " + e.getMessage());
		}

		return count;
	}
	
	// 인자로 받은 book의 no로 book 삭제
	public int deleteByNo(Long no) {
		int result = 0;

		try (
			Connection con = getConnection(); 
			PreparedStatement pstmt = con.prepareStatement("delete from book where no = ?");
		) {
			pstmt.setLong(1, no);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
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
