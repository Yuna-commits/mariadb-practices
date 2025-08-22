--
-- bookmall
-- 

-- auto_increment 값 추출
select last_insert_id();

-- insert
-- user
insert into user(name, email, password, phone) values('데스트유저01', 'test01@test.com', '1234', '010-0000-0000');
-- category
insert into category(type) value('인문');
-- book
insert into book(category_no, title, price) values(1, '과학혁명의 구조', 20000);
-- cart
insert into cart(user_no, book_no, quantity) values(1, 1, 1);
-- orders
insert into orders(user_no, number, payment, shipping, status) values(1, '202401213-000012', 82400, '서울시 은평구 진관3로 77 구파발 래미안 926-801', '배송준비');
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
select cart.no, user.name, book.title, cart.quantity from book join cart on book.no = cart.book_no join user on cart.user_no = user.no where cart.user_no = 1;
-- orders
select a.no, a.user_no, a.number, a.payment, a.shipping, a.status from orders a join user b on a.user_no = b.no;
-- orders_book

-- book
select a.no, a.title, a.price, b.type from book a join category b on a.category_no=b.no;
