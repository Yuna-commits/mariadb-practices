-- 테이블 조인(JOIN) SQL 문제입니다.

-- 문제 1. 
-- 현재 급여가 많은 직원부터 직원의 사번, 이름, 그리고 연봉을 출력 하시오.
SELECT 
    a.emp_no, a.first_name, b.salary * 12 AS '연봉'
FROM
    employees a
        JOIN
    salaries b ON a.emp_no = b.emp_no
WHERE
    b.to_date = '9999-01-01'
ORDER BY b.salary DESC;

-- 문제2.
-- 전체 사원의 사번, 이름, 현재 직책을 이름 순서로 출력하세요.
SELECT 
    a.emp_no, a.first_name, b.title
FROM
    employees a
        JOIN
    titles b ON a.emp_no = b.emp_no
WHERE
    b.to_date = '9999-01-01'
ORDER BY a.first_name ASC;

-- 문제3.
-- 전체 사원의 사번, 이름, 현재 부서를 이름 순서로 출력하세요.
SELECT 
    a.emp_no, a.first_name, c.dept_name
FROM
    employees a
        JOIN
    dept_emp b ON a.emp_no = b.emp_no
        JOIN
    departments c ON b.dept_no = c.dept_no
WHERE
    b.to_date = '9999-01-01'
ORDER BY a.first_name ASC;

-- 문제4.
-- 현재 근무중인 전체 사원의 사번, 이름, 연봉, 직책, 부서를 모두 이름 순서로 출력합니다.
SELECT 
    a.emp_no,
    a.first_name,
    d.salary * 12 AS '연봉',
    e.title,
    c.dept_name
FROM
    employees a
        JOIN
    dept_emp b ON a.emp_no = b.emp_no
        JOIN
    departments c ON b.dept_no = c.dept_no
        JOIN
    salaries d ON a.emp_no = d.emp_no
        JOIN
    titles e ON a.emp_no = e.emp_no
WHERE
    b.to_date = '9999-01-01'
        AND d.to_date = '9999-01-01'
        AND e.to_date = '9999-01-01'
ORDER BY a.first_name ASC;

-- 문제5.
-- 'Technique Leader'의 직책으로 과거에 근무한 적이 있는 모든 사원의 사번과 이름을 출력하세요.
-- (현재 'Technique Leader'의 직책으로 근무하는 사원은 고려하지 않습니다.)
SELECT 
    a.emp_no, a.first_name
FROM
    employees a
        JOIN
    titles b ON a.emp_no = b.emp_no
WHERE
    b.title = 'Technique Leader'
        AND b.to_date <> '9999-01-01';

-- 문제6.
-- 직원 이름(last_name) 중에서 S로 시작하는 직원들의 이름, 부서명, 직책을 조회하세요.
SELECT 
    a.last_name, c.dept_name, d.title
FROM
    employees a
        JOIN
    dept_emp b ON a.emp_no = b.emp_no
        JOIN
    departments c ON b.dept_no = c.dept_no
        JOIN
    titles d ON a.emp_no = d.emp_no
WHERE
    a.last_name LIKE 'S%';

-- 문제7.
-- 현재, 직책이 Engineer인 사원 중에서 현재 급여가 40,000 이상인 사원들의 사번, 이름, 급여 그리고 타이틀을 급여가 큰 순서대로 출력하세요.
SELECT 
    a.emp_no, a.first_name, b.salary, c.title
FROM
    employees a
        JOIN
    salaries b ON a.emp_no = b.emp_no
        JOIN
    titles c ON a.emp_no = c.emp_no
WHERE
    b.to_date = '9999-01-01'
        AND c.to_date = '9999-01-01'
        AND c.title = 'Engineer'
        AND b.salary >= 40000
ORDER BY b.salary DESC;

-- 문제8.
-- 현재, 평균급여가 50,000이 넘는 직책을 직책과 평균급여을 평균급여가 큰 순서대로 출력하세요.
SELECT 
    b.title, AVG(a.salary)
FROM
    salaries a
        JOIN
    titles b ON a.emp_no = b.emp_no
WHERE
    a.to_date = '9999-01-01'
        AND b.to_date = '9999-01-01'
GROUP BY b.title
HAVING AVG(a.salary) > 50000
ORDER BY AVG(a.salary) DESC;

-- 문제9.
-- 현재, 부서별 평균급여을 평균급여가 큰 순서대로 부서명과 평균연봉을 출력 하세요.
SELECT 
    a.dept_name, AVG(c.salary)
FROM
    departments a
        JOIN
    dept_emp b ON a.dept_no = b.dept_no
        JOIN
    salaries c ON b.emp_no = c.emp_no
WHERE
    b.to_date = '9999-01-01'
        AND c.to_date = '9999-01-01'
GROUP BY a.dept_no
ORDER BY AVG(c.salary) DESC;

-- 문제10.
-- 현재, 직책별 평균급여를 평균급여가 큰 직책 순서대로 직책명과 그 평균연봉을 출력 하세요.
SELECT 
    b.title, AVG(c.salary)
FROM
    employees a
        JOIN
    titles b ON a.emp_no = b.emp_no
        JOIN
    salaries c ON a.emp_no = c.emp_no
WHERE
    b.to_date = '9999-01-01'
        AND c.to_date = '9999-01-01'
GROUP BY b.title
ORDER BY AVG(c.salary) DESC;