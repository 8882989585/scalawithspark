-- https://leetcode.com/problems/total-sales-amount-by-year/
-- recursive, cte, hard

with RECURSIVE A as (select * from Sales
union
select product_id, period_start + INTERVAL 1 DAY as period_start, period_end, average_daily_sales from A where period_start < period_end
)
select p.product_id as product_id, p.product_name as product_name, CAST(YEAR(a.period_start) as CHAR(4)) as report_year,
sum(a.average_daily_sales) as total_amount from A a join Product p on a.product_id = p.product_id group by
p.product_id, YEAR(a.period_start) order by p.product_id, YEAR(a.period_start);