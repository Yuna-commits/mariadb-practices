-- 서브쿼리(SUBQUERY) SQL 문제입니다.

-- 단 조회결과는 급여의 내림차순으로 정렬되어 나타나야 합니다. 

-- 문제1.
-- 현재 전체 사원의 평균 급여보다 많은 급여를 받는 사원은 몇 명이나 있습니까?
SELECT 
    COUNT(*)
FROM
    salaries
WHERE
    to_date = '9999-01-01'
        AND salary > (SELECT -- 전체 사원 평균급여
            AVG(salary)
        FROM
            salaries
        WHERE
            to_date = '9999-01-01')
ORDER BY salary ASC;

-- 문제2. 
-- 현재, 각 부서별로 최고의 급여를 받는 사원의 사번, 이름, 부서 급여을 조회하세요. 단 조회결과는 급여의 내림차순으로 정렬합니다.
SELECT 
    b.emp_no, b.first_name, c.salary
FROM
    dept_emp a
        JOIN
    employees b ON a.emp_no = b.emp_no
        JOIN
    salaries c ON b.emp_no = c.emp_no
WHERE
    a.to_date = '9999-01-01'
        AND c.to_date = '9999-01-01'
        AND c.salary = (SELECT -- 부서별 최고 급여
            MAX(sb.salary)
        FROM
            dept_emp sa
                JOIN
            salaries sb ON sa.emp_no = sb.emp_no
        WHERE
            sa.to_date = '9999-01-01'
                AND sb.to_date = '9999-01-01'
                AND a.dept_no=sa.dept_no
        GROUP BY sa.dept_no)
ORDER BY c.salary DESC;

-- 문제3.
-- 현재, 사원 자신들의 부서의 평균급여보다 급여가 많은 사원들의 사번, 이름 그리고 급여를 조회하세요.
SELECT 
    employees.emp_no AS '사번', employees.first_name AS '이름', salary AS '급여'
FROM
    employees 
        JOIN
    salaries ON a.emp_no = c.emp_no,
    (SELECT -- 부서별 평균급여
        a.dept_no, AVG(b.salary) AS avg_salary
    FROM
        dept_emp a 
			JOIN
		salaries b ON a.emp_no = b.emp_no
    WHERE
        a.to_date = '9999-01-01'
            AND b.to_date = '9999-01-01'
    GROUP BY a.dept_no) AS sub
WHERE
    a.to_date = '9999-01-01'
        AND c.to_date = '9999-01-01'
        AND c.salary > sub.avg_salary;

-- 문제4.
-- 현재, 사원들의 사번, 이름, 그리고 매니저 이름과 부서 이름을 출력해 보세요.
SELECT 
    a.emp_no,
    a.first_name AS emp_name,
    e.first_name AS mgr_name,
    c.dept_name
FROM
    employees a
        JOIN
    dept_emp b ON a.emp_no = b.emp_no
        JOIN
    departments c ON b.dept_no = c.dept_no
        JOIN
    dept_manager d ON b.dept_no = d.dept_no
        JOIN
    employees e ON d.emp_no = e.emp_no
WHERE
    b.to_date = '9999-01-01'
        AND d.to_date = '9999-01-01';
        
-- 문제5.
-- 현재, 평균급여가 가장 높은 부서의 사원들의 사번, 이름, 직책 그리고 급여를 조회하고 급여 순으로 출력하세요.
SELECT 
    b.emp_no, b.first_name, c.title, d.salary
FROM
    dept_emp a
        JOIN
    employees b ON a.emp_no = b.emp_no
        JOIN
    titles c ON b.emp_no = c.emp_no
        JOIN
    salaries d ON b.emp_no = d.emp_no
WHERE
    a.to_date = '9999-01-01'
        AND c.to_date = '9999-01-01'
        AND d.to_date = '9999-01-01'
        AND a.dept_no = (SELECT -- 평균 급여가 가장 높은 부서번호
            sa.dept_no
        FROM 
            (SELECT -- 부서 평균급여
                ssa.dept_no, AVG(ssb.salary) AS avg_salary
            FROM
                dept_emp ssa
            JOIN salaries ssb ON ssa.emp_no = ssb.emp_no
            WHERE
                ssa.to_date = '9999-01-01'
                    AND ssb.to_date = '9999-01-01'
            GROUP BY ssa.dept_no) AS sa
        WHERE -- 가장 많은 부서 평균급여
            sa.avg_salary = (SELECT 
                    MAX(sa.avg_salary)
                FROM
                    (SELECT 
                        ssa.dept_no, AVG(ssb.salary) AS avg_salary
                    FROM
                        dept_emp ssa
                    JOIN salaries ssb ON ssa.emp_no = ssb.emp_no
                    WHERE
                        ssa.to_date = '9999-01-01'
                            AND ssb.to_date = '9999-01-01'
                    GROUP BY ssa.dept_no) AS sa))
ORDER BY d.salary DESC;

-- LIMIT
SELECT 
    b.emp_no, b.first_name, c.title, d.salary
FROM
    dept_emp a
        JOIN
    employees b ON a.emp_no = b.emp_no
        JOIN
    titles c ON b.emp_no = c.emp_no
        JOIN
    salaries d ON b.emp_no = d.emp_no
WHERE
    a.to_date = '9999-01-01'
        AND c.to_date = '9999-01-01'
        AND d.to_date = '9999-01-01'
        AND a.dept_no = (SELECT 
            sa.dept_no
        FROM 
			(SELECT -- 평균 급여가 가장 높은 부서번호
				a.dept_no, AVG(c.salary) AS avg_salary
			FROM
				departments a
					JOIN
				dept_emp b ON a.dept_no = b.dept_no
					JOIN
				salaries c ON b.emp_no = c.emp_no
			WHERE
				b.to_date = '9999-01-01'
			AND c.to_date = '9999-01-01'
			GROUP BY b.dept_no
			ORDER BY avg_salary DESC
			LIMIT 1) sa)
ORDER BY d.salary DESC;

    
-- 문제6.
-- 현재, 평균 급여가 가장 높은 부서의 이름 그리고 평균급여를 출력하세요.
SELECT 
    a.dept_name, AVG(c.salary) AS avg_salary
FROM
    departments a
        JOIN
    dept_emp b ON a.dept_no = b.dept_no
        JOIN
    salaries c ON b.emp_no = c.emp_no
WHERE
    b.to_date = '9999-01-01'
        AND c.to_date = '9999-01-01'
GROUP BY b.dept_no
ORDER BY avg_salary DESC
LIMIT 1;

-- 문제7.
-- 현재, 평균 급여가 가장 높은 직책의 타이틀 그리고 평균급여를 출력하세요.
SELECT 
    title, avg_salary
FROM
    (SELECT -- 직책별 평균급여
        a.title, AVG(b.salary) AS avg_salary
    FROM
        titles a
    JOIN salaries b ON a.emp_no = b.emp_no
    WHERE
        a.to_date = '9999-01-01'
            AND b.to_date = '9999-01-01'
    GROUP BY a.title) sa
WHERE
    avg_salary = (SELECT -- 가장 높은 직책별 평균급여
            MAX(sub.avg_salary)
        FROM
            (SELECT 
                AVG(sb.salary) AS avg_salary
            FROM
                titles sa
            JOIN salaries sb ON sa.emp_no = sb.emp_no
            WHERE
                sa.to_date = '9999-01-01'
                    AND sb.to_date = '9999-01-01'
            GROUP BY sa.title) sub);

-- LIMIT
SELECT 
    a.title, AVG(b.salary) AS avg_salary
FROM
    titles a
        JOIN
    salaries b ON a.emp_no = b.emp_no
WHERE
    a.to_date = '9999-01-01'
        AND b.to_date = '9999-01-01'
GROUP BY a.title
ORDER BY AVG(b.salary) DESC
LIMIT 1;