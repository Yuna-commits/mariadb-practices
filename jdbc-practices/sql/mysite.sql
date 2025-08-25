--
-- mysite : user
--
desc user;

-- 회원가입
insert into user(name, email, password, gender, join_date) values ('둘리', 'dooly@gmail.com', password('1234'), 'male', current_date());

-- 회원 리스트
select * from user;

-- 삭제
delete from user where id=1;