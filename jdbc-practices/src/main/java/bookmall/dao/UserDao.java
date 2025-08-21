package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import bookmall.vo.CartVo;
import bookmall.vo.UserVo;

public class UserDao {

	public int insert(UserVo vo) {
		int count = 0;

		try(
			Connection conn=getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("insert into user(name, email, password, phone) values(?, ?, ?, ?)");
			PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id()");
		) {
			/**
			 * pstmt1의 insert문의 실행으로 user 테이블의 pk_no에는 auto_increment된 값이 저장됨 자바는 그 값을 모름!
			 * setNo()를 위해 select last_insert_id()로 **같은 DB conn 내**에서 가장 최근에
			 * auto_increment된 값을 알아야 함
			 */
			// INSERT
			pstmt1.setString(1, vo.getName());
			pstmt1.setString(2, vo.getEmail());
			pstmt1.setString(3, vo.getPassword());
			pstmt1.setString(4, vo.getPhone());
			
			// SQL 쿼리를 DB에 실행
			count = pstmt1.executeUpdate();

			// SELECT LAST_INSERT_ID(자신의 pk_no)
			ResultSet rs = pstmt2.executeQuery();
			vo.setNo(rs.next() ? rs.getLong(1) : null);
			rs.close();
		} catch(SQLException e) {
			System.err.println("DB 연결에 실패했습니다.");
			System.err.println("오류: "+e.getMessage());
		}
		
		return count;
	}

	public List<CartVo> findAll() {
		return null;
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
			System.err.println("오류: "+e.getMessage());
		}

		return conn;
	}
}
