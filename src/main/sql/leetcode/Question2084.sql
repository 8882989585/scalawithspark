-- https://leetcode.com/problems/drop-type-1-orders-for-customers-with-type-0-orders/

with a as (select distinct customer_id as cust_id from Orders where order_type = 0)
select o.order_id, o.customer_id, o.order_type
from Orders o
         left join a k on o.customer_id = k.cust_id
where (k.cust_id is not null and o.order_type != 1)
   or k.cust_id is null;