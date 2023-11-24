-- https://leetcode.com/problems/find-the-team-size/

with a as (select team_id, count(*) as t_count from Employee group by team_id)
select e.employee_id, k.t_count as team_size
from Employee e
         join a k on e.team_id = k.team_id;