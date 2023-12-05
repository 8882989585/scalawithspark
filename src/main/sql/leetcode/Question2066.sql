-- https://leetcode.com/problems/account-balance/

with t1 as (select account_id,
                   day,
                   case
                       when type = 'Deposit' then amount
                       else -1 * amount
                       end as amount
            from Transactions)
select account_id,
       day,
       (amount +
        (select coalesce(sum(a.amount), 0) from t1 a where a.day < b.day and a.account_id = b.account_id)) as balance
from t1 b
order by account_id, day;