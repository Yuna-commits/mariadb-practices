package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelectEx02 {

	public static void main(String[] args) {
		List<DeptVo> list = search("개발");

		for (DeptVo vo : list) {
			System.out.println(vo);
		}
	}

	private static List<DeptVo> search(String keyword) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<DeptVo> result = new ArrayList<>();

		try {
			// 1. JDBC Driver 로드
			Class.forName("org.mariadb.jdbc.Driver");

			// 2. Connection 객체 생성, 연결
			String url = "jdbc:mariadb://192.168.0.181:3306/webdb";
			con = DriverManager.getConnection(url, "webdb", "webdb");

			// 3. Statement 준비
			// ? : 바인딩 파라미터, 나중에 값 설정 가능
			String sql = "select id, name from dept where name like ?";
			pstmt = con.prepareStatement(sql);

			// 4. Parameter Binding
			// 1번째 ? 에 id 값 설정
			pstmt.setString(1, "%" + keyword + "%");

			// 5. SQL 실행
			rs = pstmt.executeQuery();

			// 6. 결과처리, ResultSet 순회
			while (rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);

				result.add(new DeptVo(id, name));
			}

		} catch (ClassNotFoundException e) {
			System.out.println("Driver Class Not Found");
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {// 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
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
