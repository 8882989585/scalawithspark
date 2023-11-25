-- https://leetcode.com/problems/apples-oranges/

-- solution 1
with apple as (select * from Sales where fruit = 'apples'),
     orange as (select * from Sales where fruit = 'oranges')
select a.sale_date, (a.sold_num - o.sold_num) as diff
from apple a
         join orange o on a.sale_date = o.sale_date
order by a.sale_date;

-- solution 2
SELECT sale_date,
       SUM(CASE WHEN fruit = "apples" THEN sold_num END) - SUM(CASE WHEN fruit = "oranges" THEN sold_num END) as diff
FROM Sales
GROUP BY sale_date
ORDER BY sale_date;