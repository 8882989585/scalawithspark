-- https://leetcode.com/problems/median-employee-salary/description/
-- windows, cte, hard

with A as (select *, row_number() over (partition by company order by salary, id) as r_no from Employee),
     B as (select CEIL(max(r_no) / 2) as x, FLOOR(max(r_no) / 2) + 1 as y, company from A group by company),
     D as (select x, company from B union select y, company from B)
select A.id, A.company, A.salary
from D
         join A on D.company = A.company and D.x = A.r_no;