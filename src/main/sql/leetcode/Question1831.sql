-- https://leetcode.com/problems/maximum-transaction-each-day/

with a as (select date(day), amount, transaction_id, rank() over (partition by date(day) order by amount desc) as rnk
           from Transactions)
select transaction_id
from a
where rnk = 1
order by transaction_id;