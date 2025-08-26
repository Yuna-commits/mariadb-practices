package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
			 * pstmt1의 insert문의 실행으로 user 테이블의 pk_no에는 auto_increment된 값이 저장, 자바는 그 값을 모름
			 * UserVo.setNo()를 위해 select last_insert_id()로
			 * **같은 DB conn 내**에서 **가장 마지막에 insert한 테이블**의 auto_increment된 값을 알아야 함
			 */
			// Parameter Binding
			pstmt1.setString(1, vo.getName());
			pstmt1.setString(2, vo.getEmail());
			pstmt1.setString(3, vo.getPassword());
			pstmt1.setString(4, vo.getPhone());
			
			// SQL 쿼리를 DB에 실행
			count = pstmt1.executeUpdate();
			
			/**
			 * UserVo.setNo()가 필요한 이유
			 * user는 orders, cart와 1:N 관계 -> OrderVo, CartVo에 userNo가 존재해야 함
			 * UserDao를 먼저 생성하여 userNo가 존재해야 test의 setUp()에서 두 Vo에 UserNo 저장 가능
			 */
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

	public List<UserVo> findAll() {
		List<UserVo> result = new ArrayList<UserVo>();
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select name, email, phone from user");
			ResultSet rs = pstmt.executeQuery();
		){
			while (rs.next()) {
				String name = rs.getString(1);
				String email = rs.getString(2);
				String phone = rs.getString(3);

				UserVo vo = new UserVo();
				vo.setName(name);
				vo.setEmail(email);
				vo.setPhone(phone);
				
				result.add(vo);
			}
		} catch (SQLException e) {
			System.err.println("DB 연결에 실패했습니다.");
			System.err.println("오류: " + e.getMessage());
		}
		
		return result;
	}

	// 인자로 받은 user의 no로 user 삭제
	public int deleteByNo(Long no) {
		int result = 0;

		try (
			Connection con = getConnection(); 
			PreparedStatement pstmt = con.prepareStatement("delete from user where no = ?");
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
