package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertEx02 {
	public static void main(String[] args) {
		insert("개발3팀");
		insert("개발4팀");
	}

	public static boolean insert(String department) {
		Connection con = null;
		PreparedStatement pstmt = null;

		boolean result = false;

		try {
			// 1. JDBC Driver 로드
			Class.forName("org.mariadb.jdbc.Driver");

			// 2. Connection 객체 생성, 연결
			String url = "jdbc:mariadb://192.168.0.181:3306/webdb";
			con = DriverManager.getConnection(url, "webdb", "webdb");

			// 3. Statement 준비
			// ? : 바인딩 파라미터, 나중에 값 설정 가능
			String sql = "insert into dept(name) values(?)";
			pstmt = con.prepareStatement(sql);

			// 4. Parameter Binding
			// 1번째 ? 에 department 값 설정
			pstmt.setString(1, department);

			// 5. SQL 실행
			int count = pstmt.executeUpdate();

			result = (count == 1);

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
