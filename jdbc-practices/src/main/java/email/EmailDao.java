package email;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmailDao {
	public int deleteByEmail(String email) {
		int result = 0;

		try (
				Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement("delete from email where email = ?");
		) {
			// Parameter Binding
			pstmt.setString(1, email);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return result;
	}

	public int insert(EmailVo vo) {
		int result = 0;

		try (
				Connection con = getConnection();
				PreparedStatement pstmt = con
						.prepareStatement("insert into email(first_name, last_name, email) values (?, ?, ?)");
		) {
			// Parameter Binding
			pstmt.setString(1, vo.getFirstName());
			pstmt.setString(2, vo.getLastName());
			pstmt.setString(3, vo.getEmail());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return result;
	}

	public List<EmailVo> findAll() {
		List<EmailVo> result = new ArrayList<EmailVo>();

		try (Connection con = getConnection();
				PreparedStatement pstmt = con
						.prepareStatement("select id, first_name, last_name, email from email order by id desc");
				ResultSet rs = pstmt.executeQuery();) {

			while (rs.next()) {
				Long id = rs.getLong(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String email = rs.getString(4);

				EmailVo vo = new EmailVo();
				vo.setId(id);
				vo.setFirstName(firstName);
				vo.setLastName(lastName);
				vo.setEmail(email);

				result.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return result;
	}

	public Long count() {
		Long result = 0L;

		try (		
				Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement("select count(*) from email");
				ResultSet rs = pstmt.executeQuery();
		){
			if (rs.next()) {
				result = rs.getLong(1);
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		} 
		
		return result;
	}

	private Connection getConnection() throws SQLException {
		Connection con = null;

		try {
			// 1. JDBC Driver 로드 -> 실패시 ClassNotFoundException
			Class.forName("org.mariadb.jdbc.Driver");

			// 2. Connection 연결 -> 호출 위치로 반환, Exception도 호출 위치로
			String url = "jdbc:mariadb://192.168.0.181:3306/webdb";
			con = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver Class Not Found");
		}

		return con;
	}

}
