-- https://leetcode.com/problems/sales-by-day-of-the-week/

with a as (select item_category, DAYOFWEEK(order_date) as dow, sum(quantity) as sl
           from Orders o
                    right join Items i on o.item_id = i.item_id
           group by item_category, DAYOFWEEK(order_date)),
     b as (select item_category                        as Category,
                  case when dow = 2 then sl else 0 end as Monday,
                  case when dow = 3 then sl else 0 end as Tuesday,
                  case when dow = 4 then sl else 0 end as Wednesday,
                  case when dow = 5 then sl else 0 end as Thrusday,
                  case when dow = 6 then sl else 0 end as Friday,
                  case when dow = 7 then sl else 0 end as Saturday,
                  case when dow = 1 then sl else 0 end as Sunday
           from a)
select Category       as CATEGORY,
       sum(Monday)    as MONDAY,
       sum(Tuesday)   as TUESDAY,
       sum(Wednesday) as WEDNESDAY,
       sum(Thrusday)  as THURSDAY,
       sum(Friday)    as FRIDAY,
       sum(Saturday)  as SATURDAY,
       sum(Sunday)    as SUNDAY
from b
group by Category
order by Category;