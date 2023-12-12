-- https://leetcode.com/problems/all-people-report-to-the-given-manager/
-- recursive

WITH RECURSIVE rc as (select manager_id, employee_id
                      from Employees
                      where employee_id = 1
                      union
                      select t.manager_id, t.employee_id
                      from rc r
                               join Employees t on r.employee_id = t.manager_id)
select employee_id
from rc
where employee_id != 1;
