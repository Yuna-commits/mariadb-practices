--
-- Test
--

-- deleteAll
delete from orders_book;
delete from orders;
delete from cart;
delete from user;
delete from book;
delete from category;

-- auto_increment 초기화
alter table user auto_increment = 1;
alter table category auto_increment = 1;
alter table cart auto_increment = 1;
alter table book auto_increment = 1;
alter table orders auto_increment = 1;
alter table orders_book auto_increment = 1;
