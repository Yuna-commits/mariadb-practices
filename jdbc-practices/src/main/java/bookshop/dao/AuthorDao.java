package bookshop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bookshop.vo.AuthorVo;

public class AuthorDao {

	public int deleteAll() {
		int result = 0;

		try (
			Connection con = getConnection(); 
			PreparedStatement pstmt = con.prepareStatement("delete from author");
		) {
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int insert(AuthorVo vo) {
		int result = 0;

		try (
			Connection con = getConnection();
			PreparedStatement pstmt1 = con.prepareStatement("insert into author(name) values(?)");
			PreparedStatement pstmt2 = con.prepareStatement("select last_insert_id()");
		) {
			// Parameter Binding
			pstmt1.setString(1, vo.getName());
			result = pstmt1.executeUpdate();

			ResultSet rs = pstmt2.executeQuery();
			// 방금 insert된 id의 값을 JAVA는 모름, 직접 설정해야 함
			// author의 id와 BookVo의 author_id를 연결해야 함
			// BookShopApp의 bookVo.setAuthorId(authorVo.getId())에 사용하기 위해 필수!
			vo.setId(rs.next() ? rs.getLong(1) : null);

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return result;
	}

	// Driver 로딩, Connection 연결 처리
	private Connection getConnection() throws SQLException {
		Connection con = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.0.181:3306/webdb";
			con = DriverManager.getConnection(url, "webdb", "webdb");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return con;
	}
}
