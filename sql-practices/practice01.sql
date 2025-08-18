-- 기본 SQL 문제

-- 문제1.
-- 사번이 10944인 사원의 이름은(전체 이름)
SELECT 
    emp_no, CONCAT(first_name, ' ', last_name) AS name
FROM
    employees
WHERE
    emp_no = '10944';

-- 문제2. 
-- 전체 직원의 다음 정보를 조회하세요. 가장 선임부터 출력이 되도록 하세요.
-- 출력은 이름, 성별, 입사일 순서이고 "이름", "성별", "입사일"로 컬럼 이름을 대체해 보세요.
SELECT 
    CONCAT(first_name, ' ', last_name) AS '이름',
    gender AS '성벌',
    hire_date AS '입사일'
FROM
    employees
ORDER BY hire_date ASC;

-- 문제3.
-- 여직원과 남직원은 각 각 몇 명이나 있나요?(각각 쿼리 만들어서 각각 출력 또는 집계로 한 번에 해결)
SELECT 
    gender, COUNT(*)
FROM
    employees
GROUP BY gender;

-- 문제4.
-- 현재(to_date='9999-01-01'), 근무하고 있는 직원 수는 몇 명입니까? (salaries 테이블을 사용) 
SELECT 
    COUNT(*) AS '직원 수'
FROM
    salaries
WHERE
    to_date = '9999-01-01';
   
-- 문제5.
-- 부서는 총 몇 개가 있나요?
SELECT 
    COUNT(*) AS '부서 수'
FROM
    departments;
  
-- 문제6.
-- 현재 부서 매니저는 몇 명이나 있나요?(역임 매너저는 제외)
SELECT 
    COUNT(*)
FROM
    dept_manager
WHERE
    to_date = '9999-01-01';
 
-- 문제7.
-- 전체 부서를 출력하려고 합니다. 순서는 부서이름이 긴 순서대로 출력해 보세요.
SELECT 
    dept_name
FROM
    departments
ORDER BY LENGTH(dept_name) DESC;

-- 문제8.
-- 현재 급여가 120,000이상 받는 사원은 몇 명이나 있습니까?
SELECT 
    COUNT(*)
FROM
    salaries
WHERE
    to_date = '9999-01-01'
        AND salary >= 120000;
   
-- 문제9.
-- 어떤 직책들이 있나요? 중복 없이 이름이 긴 순서대로 출력해 보세요.
SELECT DISTINCT
    title
FROM
    titles
ORDER BY LENGTH(title) DESC;

-- 문제10
-- 현재 Engineer 직책의 사원은 총 몇 명입니까?
SELECT 
    COUNT(*)
FROM
    titles
WHERE
    to_date = '9999-01-01'
        AND title = 'Engineer';
   
-- 문제11
-- 사번이 13250(Zeydy)인 직원의 직책 변경 상황을 시간순으로 출력해보세요.
SELECT 
    title, from_date, to_date
FROM
    titles
WHERE
    emp_no = '13250'
ORDER BY from_date ASC;