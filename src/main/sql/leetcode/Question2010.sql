-- https://leetcode.com/problems/the-number-of-seniors-and-juniors-to-join-the-company-ii/

-- cte, hard
with A as (select *
           from (select employee_id, sum(salary) over (partition by null order by salary) as wth
                 from Candidates
                 where experience = 'Senior') tim1
           where wth <= 70000),
     B as (select *
           from (select employee_id, sum(salary) over (partition by null order by salary) as wth
                 from Candidates
                 where experience = 'Junior') tim1
           where wth <= (select 70000 - coalesce(max(wth), 0) from A))
select employee_id
from A
union all
select employee_id
from B;
