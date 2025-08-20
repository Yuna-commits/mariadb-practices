package bookshop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import bookshop.dao.AuthorDao;
import bookshop.dao.BookDao;
import bookshop.vo.BookVo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookDaoTest {
	private static Long preCount = 0L;

	@BeforeAll
	public static void setup() {
		preCount = new BookDao().count();
	}

	@Test
	@Order(1)
	public void deleteAllTest() {
		int count = new AuthorDao().deleteAll();

		assertEquals(1, count);
	}

	@Test
	@Order(2)
	public void insertTest() {
		BookVo vo = new BookVo();
		vo.setTitle("트와일라잇");
		vo.setAuthorId(7L);

		int count = new BookDao().insert(vo);

		// assertEquals(기댓값, 실제값)
		assertEquals(1, count);
	}
}
