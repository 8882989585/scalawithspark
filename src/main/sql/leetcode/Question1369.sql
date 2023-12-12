-- https://leetcode.com/problems/get-the-second-most-recent-activity/
-- cte

with A as (select *, row_number() over(partition by username order by startDate desc) as r_no from UserActivity),
     B as (select username, max(r_no) as max_rn from A group by username having max(r_no) < 2),
     C as (select a.*
           from A a
                    join B b on A.username = B.username and a.r_no = b.max_rn
           union all
           select a.*
           from A a
           where r_no = 2)
select username, activity, startDate, endDate
from C;