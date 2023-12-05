-- https://leetcode.com/problems/tasks-count-in-the-weekend/

select sum(case when DAYOFWEEK(submit_date) between 2 and 6 then 1 else 0 end) as working_cnt,
       sum(case when DAYOFWEEK(submit_date) between 2 and 6 then 0 else 1 end) as weekend_cnt
from Tasks;