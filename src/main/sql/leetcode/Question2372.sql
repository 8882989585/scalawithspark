-- https://leetcode.com/problems/calculate-the-influence-of-each-salesperson/

select a.salesperson_id, a.name, coalesce(sum(c.price), 0) as total
from Salesperson a
         left join Customer b on a.salesperson_id = b.salesperson_id
         left join Sales c on b.customer_id = c.customer_id
group by a.salesperson_id, a.name;