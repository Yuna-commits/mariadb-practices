package email;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmailDao {
	public int insert(EmailVo vo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		int result = 0;

		try {
			// 1. JDBC Driver 로드
			Class.forName("org.mariadb.jdbc.Driver");

			// 2. Connection 객체 생성, 연결
			String url = "jdbc:mariadb://192.168.0.181:3306/webdb";
			con = DriverManager.getConnection(url, "webdb", "webdb");

			// 3. Statement 준비
			String sql = "insert into email(first_name, last_name, email) values (?, ?, ?)";
			pstmt = con.prepareStatement(sql);

			// 4. Parameter Binding
			pstmt.setString(1, vo.getFirstName());
			pstmt.setString(2, vo.getLastName());
			pstmt.setString(3, vo.getEmail());

			// 5. SQL 실행
			result = pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			System.out.println("Driver Class Not Found");
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
