package bookshop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import bookshop.dao.AuthorDao;
import bookshop.vo.AuthorVo;

public class AuthorDaoTest {
	@Test
	@Order(1)
	public void deleteAllTest() {
		int count = new AuthorDao().deleteAll();

		assertEquals(1, count);
	}

	@Test
	@Order(2)
	public void insertTest() {
		AuthorVo vo = new AuthorVo();
		vo.setName("스테파니메이어");

		int count = new AuthorDao().insert(vo);

		// assertEquals(기댓값, 실제값)
		assertEquals(1, count);
	}
}
