-- https://leetcode.com/problems/generate-the-invoice/
-- cte

with A as (select pr.*, ps.price * pr.quantity as price
           from Purchases pr
                    join Products ps on pr.product_id = ps.product_id),
     B as (select invoice_id, sum(price) as sp from A group by invoice_id),
     C as (select min(invoice_id) as invoice_id from B tim1 where sp = (select max(sp) from B))
select product_id, quantity, price
from A a
where invoice_id = (select invoice_id from C c)