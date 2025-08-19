package email;

import java.util.Scanner;

public class EmailApp {

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		while (true) {
			System.out.print("(l)ist (d)elete (i)nsert (q)uit > ");
			String command = scanner.nextLine();

			if ("l".equals(command)) {
				doList();
			} else if ("d".equals(command)) {
				doDelete();
			} else if ("i".equals(command)) {
				doInsert();
			} else if ("q".equals(command)) {
				break;
			}
		}

		if (scanner != null) {
			scanner.close();
		}
	}

	private static void doInsert() {
		System.out.print("성: ");
		String firstName = scanner.nextLine();

		System.out.print("이름: ");
		String lastName = scanner.nextLine();

		System.out.print("이메일: ");
		String email = scanner.nextLine();

		EmailVo vo = new EmailVo();
		vo.setFirstName(firstName);
		vo.setLastName(lastName);
		vo.setEmail(email);
		
		new EmailDao().insert(vo);

		doList();
	}

	private static void doDelete() {
		System.out.println("doDelete");
	}

	private static void doList() {
		System.out.println("doList");
	}

}
