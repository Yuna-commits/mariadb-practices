package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.CategoryVo;

public class CategoryDao {

	public int insert(CategoryVo vo) {
		int count = 0;
		
		try(
			Connection conn=getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("insert into category(type) value(?)");
			PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id()");
		) {
			// INSERT
			pstmt1.setString(1, vo.getType());
				
			// SQL 쿼리를 DB에 실행
			count = pstmt1.executeUpdate();

			// SELECT LAST_INSERT_ID(자신의 pk_no)
			ResultSet rs = pstmt2.executeQuery();
			vo.setNo(rs.next() ? rs.getInt(1) : null);
			rs.close();
		} catch(SQLException e) {
			System.err.println("DB 연결에 실패했습니다.");
			System.err.println("오류: "+e.getMessage());
		}

		return count;
	}
	
	public List<CategoryVo> findAll() {
		List<CategoryVo> result = new ArrayList<CategoryVo>();
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select no, type from category");
			ResultSet rs = pstmt.executeQuery();
		){
			while(rs.next()) {
				int no=rs.getInt(1);
				String type=rs.getString(2);
				
				CategoryVo vo = new CategoryVo(no, type);
				
				result.add(vo);
			}
		} catch (SQLException e) {
			System.err.println("DB 연결에 실패했습니다.");
			System.err.println("오류: "+e.getMessage());
		}
		
		return result;
	}
	
	// 인자로 받은 category의 no로 카테고리 삭제
	public int deleteByNo(int no) {
		int result = 0;

		try (
			Connection con = getConnection(); 
			PreparedStatement pstmt = con.prepareStatement("delete from category where no = ?");
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
