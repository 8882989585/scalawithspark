-- https://leetcode.com/problems/calculate-salaries/

with a as (select max(salary) as sal, company_id from Salaries group by company_id),
     b as (select case when sal < 1000 then 1 when sal > 10000 then 0.51 else 0.76 end as tslab, company_id from a)
select t2.company_id, t2.employee_id, t2.employee_name, ROUND(salary * tslab) as salary
from b t1
         join Salaries t2 on t1.company_id = t2.company_id;