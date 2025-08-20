package bookshop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookshop.vo.BookVo;

public class BookDao {

	public int deleteAll() {
		int result = 0;

		try (
			Connection con = getConnection(); 
			PreparedStatement pstmt = con.prepareStatement("delete from book");
		) {
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int insert(BookVo vo) {
		int result = 0;

		try (
			Connection con = getConnection();
			PreparedStatement pstmt1 = con.prepareStatement("insert into book(title, author_id) values(?, ?)");
			PreparedStatement pstmt2 = con.prepareStatement("select last_insert_id();");
		) {
			// Parameter Binding
			pstmt1.setString(1, vo.getTitle());
			pstmt1.setLong(2, vo.getAuthorId());
			result = pstmt1.executeUpdate();

			ResultSet rs = pstmt2.executeQuery();
			vo.setId(rs.next() ? rs.getLong(1) : null);

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	// 도서id와 일치하는 책 대여 처리
	public int update(Long id, String status) {
		int result = 0;

		try (
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement("update book set status = ? where id = ?");
		) {
			// Parameter Binding
			pstmt.setString(1, status);
			pstmt.setLong(2, id);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return result;
	}

	public List<BookVo> findAll() {
		List<BookVo> result = new ArrayList<BookVo>();

		try (
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(
						"select b.id, b.title, a.name, b.status from author a, book b where a.id=b.author_id order by b.id");
		) {
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				String status = rs.getString(4);

				BookVo vo = new BookVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setAuthorName(name);
				vo.setStatus(status);

				result.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
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

	// Book 테이블에 저장된 전체 레코드 수
	public Long count() {
		Long result = 0L;

		try (		
			Connection con = getConnection();
			PreparedStatement pstmt =con.prepareStatement("select count(*) from book"); 
			ResultSet rs = pstmt.executeQuery();
		){
			if (rs.next()) {
				// sql 결과 테이블의 첫번째 컬럼 반환 -> count(*) 반환
				result = rs.getLong(1);
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		} 

		return result;
	}
}
