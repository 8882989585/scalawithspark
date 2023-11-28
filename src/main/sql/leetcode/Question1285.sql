-- https://leetcode.com/problems/find-the-start-and-end-number-of-continuous-ranges/

with a as (select log_id, (log_id - row_number() over(partition by null order by log_id)) as r_no from Logs)
select min(log_id) as start_id, max(log_id) as end_id
from a
group by r_no;