-- https://leetcode.com/problems/students-report-by-geography/
-- cte, windows, hard

with A as (select name, continent, row_number() over (partition by continent order by name) as r_no from Student),
     B as (select name as America, r_no from A where continent = 'America'),
     C as (select name as Asia, r_no from A where continent = 'Asia'),
     D as (select name as Europe, r_no from A where continent = 'Europe'),
     E as (select America, Asia, Europe, b.r_no
           from B b
                    left join C c on b.r_no = c.r_no
                    left join D d on b.r_no = d.r_no
           union
           select America, Asia, Europe, b.r_no
           from C b
                    left join D c on b.r_no = c.r_no
                    left join B d on b.r_no = d.r_no
           union
           select America, Asia, Europe, b.r_no
           from D b
                    left join B c on b.r_no = c.r_no
                    left join C d on b.r_no = d.r_no)
select America, Asia, Europe
from E
order by r_no;