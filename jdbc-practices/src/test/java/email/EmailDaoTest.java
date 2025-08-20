package email;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmailDaoTest {

	private static Long count = 0L;

	@BeforeAll
	public static void setup() {
		count = new EmailDao().count();
	}

	@Test
	@Order(1)
	public void insertTest() {
		EmailVo vo = new EmailVo();
		vo.setFirstName("둘");
		vo.setLastName("리");
		vo.setEmail("dooly@gmail.com");

		int count = new EmailDao().insert(vo);

		// assertEquals(기댓값, 실제값)
		assertEquals(1, count);
	}

	@Test
	@Order(2)
	public void findAllTest() {
		List<EmailVo> list = new EmailDao().findAll();

		assertEquals(count + 1, list.size());
		// assertTrue(list.size() - 1 == count);
	}

	@Test
	@Order(3)
	public void deleteByEmailTest() {
		int count = new EmailDao().deleteByEmail("dooly@gmail.com");

		assertEquals(1, count);
	}

	@AfterAll
	public static void cleanup() {

	}
}
