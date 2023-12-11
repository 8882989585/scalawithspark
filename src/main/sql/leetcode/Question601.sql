-- https://leetcode.com/problems/human-traffic-of-stadium/
-- windows, tricks

with A as (select *, id - row_number() over (partition by null order by visit_date) as r_no
           from Stadium
           where people > 99),
     B as (select r_no from A group by r_no having count(id) > 2)
select t1.id, t1.visit_date, t1.people
from A t1
         join B t2 on t1.r_no = t2.r_no
order by t1.visit_date;