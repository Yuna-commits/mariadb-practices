package bookshop.app;

import java.util.List;
import java.util.Scanner;

import bookshop.dao.AuthorDao;
import bookshop.dao.BookDao;
import bookshop.vo.AuthorVo;
import bookshop.vo.BookVo;

public class BookShopApp {

	public static void main(String[] args) {
		installDB();
		displayBookInfo();

		// 도서 대여
		Scanner scanner = new Scanner(System.in);
		System.out.print("대여하고 싶은 책의 번호를 입력하세요:");
		Long id = scanner.nextLong();
		scanner.close();

		new BookDao().update(id, "대여중");
		displayBookInfo();
	}

	// Book 정보 출력
	private static void displayBookInfo() {
		System.out.println("*****도서 정보 출력*****");
		List<BookVo> list = new BookDao().findAll();
		for (BookVo vo : list) {
			String info = String.format("[%d] 제목: %s, 작가: %s, 대여유무: %s", vo.getId(), vo.getTitle(), vo.getAuthorName(),
					vo.getStatus());
			System.out.println(info);
		}
	}

	// DB에 Book, Author 데이터 저장
	private static void installDB() {
		AuthorDao authorDao = new AuthorDao();
		BookDao bookDao = new BookDao();

		bookDao.deleteAll();
		authorDao.deleteAll();

		AuthorVo authorVo = null;
		BookVo bookVo = null;

		// author - "스테파니메이어"
		authorVo = new AuthorVo();
		authorVo.setName("스테파니메이어");
		authorDao.insert(authorVo);

		bookVo = new BookVo();
		bookVo.setTitle("트와일라잇");
		bookVo.setAuthorId(authorVo.getId());
		bookDao.insert(bookVo);

		bookVo = new BookVo();
		bookVo.setTitle("뉴문");
		bookVo.setAuthorId(authorVo.getId());
		bookDao.insert(bookVo);

		bookVo = new BookVo();
		bookVo.setTitle("이클립스");
		bookVo.setAuthorId(authorVo.getId());
		bookDao.insert(bookVo);

		bookVo = new BookVo();
		bookVo.setTitle("브레이킹던");
		bookVo.setAuthorId(authorVo.getId());
		bookDao.insert(bookVo);

		// author - "조정래"
		authorVo = new AuthorVo();
		authorVo.setName("조정래");
		authorDao.insert(authorVo);

		bookVo = new BookVo();
		bookVo.setTitle("아리랑");
		bookVo.setAuthorId(authorVo.getId());
		bookDao.insert(bookVo);

		// author - "김동인"
		authorVo = new AuthorVo();
		authorVo.setName("김동인");
		authorDao.insert(authorVo);

		bookVo = new BookVo();
		bookVo.setTitle("젊은그들");
		bookVo.setAuthorId(authorVo.getId());
		bookDao.insert(bookVo);

		// author - "김난도"
		authorVo = new AuthorVo();
		authorVo.setName("김난도");
		authorDao.insert(authorVo);

		bookVo = new BookVo();
		bookVo.setTitle("아프니깐 청춘이다");
		bookVo.setAuthorId(authorVo.getId());
		bookDao.insert(bookVo);

		// author - "천상병"
		authorVo = new AuthorVo();
		authorVo.setName("천상병");
		authorDao.insert(authorVo);

		bookVo = new BookVo();
		bookVo.setTitle("귀천");
		bookVo.setAuthorId(authorVo.getId());
		bookDao.insert(bookVo);

		// author - "조정래"
		authorVo = new AuthorVo();
		authorVo.setName("조정래");
		authorDao.insert(authorVo);

		bookVo = new BookVo();
		bookVo.setTitle("태백산맥");
		bookVo.setAuthorId(authorVo.getId());
		bookDao.insert(bookVo);

		// author - "원수연"
		authorVo = new AuthorVo();
		authorVo.setName("원수연");
		authorDao.insert(authorVo);

		bookVo = new BookVo();
		bookVo.setTitle("풀하우스");
		bookVo.setAuthorId(authorVo.getId());
		bookDao.insert(bookVo);
	}

}
