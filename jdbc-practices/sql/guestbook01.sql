--
-- guestbok
--

desc guestbook;

-- insert
insert into guestbook values(null, '둘리', '1234', '하이', now());

-- findAll
select id, name, message, date_format(reg_date, '%Y-%m-%d %h:%i:%s') from guestbook order by id desc;

-- deleteByIdAndPassword
delete from guestbook where id=1 and password='1234';