--
-- bookmall
-- 

-- auto_increment 값 추출
select last_insert_id();

-- insert
-- user
insert into user(name, email, password, phone) values('데스트유저03', 'test01@test.com', '1234', '010-0000-0000');
-- category
insert into category(type) value('인문');
-- book
insert into book(category_no, title, price) values(1, '과학혁명의 구조', 20000);
-- cart
insert into cart(user_no, book_no, quantity) values(1, 1, 1);
-- orders
insert into orders(user_no, number, payment, shipping, status) values(3, '202401213-000012', 82400, '서울시 은평구 진관3로 77 구파발 래미안 926-801', '배송완료');
-- orders_book
insert into orders_book(orders_no, book_no, quantity, price) values(1, 1, 1, 20000);

--
-- findAll
--
-- user
select no, name, email, phone from user;
-- category
select no, type from category;

--
-- findByUserNo
-- cart
-- 회원은 자신만의 카트가 존재하며, (도서제목, 수량, 가격)의 내용을 확인할 수 있다.
select book.no, book.title, cart.quantity, book.price from book join cart on book.no = cart.book_no join user on cart.user_no = user.no where cart.user_no = 1;
-- orders
-- 운영자는 회원의 주문내용(주문번호, 주문자(이름/이메일), 주문상태, 결제금액, 배송지)를 확인할 수 있다.
select orders.number, user.name, user.email, orders.status, orders.payment, orders.shipping from orders join user on orders.user_no = user.no where orders.no = 1 and user.no = 1;
-- orders_book
-- 주문도서는 (도서제목, 수량, 가격)의 내용을 확인할 수 있다.
select orders.no, book.no, book.title, orders_book.quantity, orders_book.price from orders_book join book on orders_book.book_no = book.no join orders on orders_book.orders_no = orders.no where orders.no =1 and orders.user_no = 1;

--
-- deleteByNo
--
-- orders_book
select * from orders_book;
delete from orders_book where orders_no = 1;

-- orders
select * from orders;
delete from orders where no = 1;

-- cart
select * from cart;
delete from cart where user_no = 1 and book_no = 2;

-- book
select * from book;
delete from book where no = 1;

-- category
select * from category;
delete from category where no = 1;

-- user
select * from user;
delete from user where no = 1;