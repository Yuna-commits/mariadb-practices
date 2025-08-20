--
-- bookshop application
--

alter table author modify id int not null auto_increment;

-- insert : author
insert into author(name) values('스테파니메이어');
select last_insert_id();

-- insert : book
insert into book(title, author_id) values('트와일라잇', 7);

-- update
update book set status = '대여중' where id = 6;

-- findAll
select id, name from author;
select id, title, status, author_id from book;
-- join
select b.id, b.title, b.status, a.name from author a, book b where a.id=b.author_id order by b.id;

-- deleteAll
delete from book;
delete from author;

drop table book;
drop table author;