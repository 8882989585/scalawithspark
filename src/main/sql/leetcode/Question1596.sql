-- https://leetcode.com/problems/the-most-frequently-ordered-products-for-each-customer/

with a as (select *, 1 as cnt from Orders t2),
     b as (select *, sum(cnt) over (partition by customer_id, product_id order by order_date) as total from a),
     c as (select *, rank() over (partition by customer_id order by total desc) as r_no from b),
     d as (select * from c where r_no = 1),
     e as (select t2.*, customer_id
           from d t1
                    left join Products t2 on t1.product_id = t2.product_id)
select customer_id, product_id, product_name
from e;