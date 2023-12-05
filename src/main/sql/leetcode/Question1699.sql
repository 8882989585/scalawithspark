-- https://leetcode.com/problems/number-of-calls-between-two-persons/

with t as (select LEAST(from_id, to_id) as from_id, GREATEST(from_id, to_id) as to_id, duration from Calls)
select from_id as person1, to_id as person2, count(*) as call_count, sum(duration) as total_duration
from t
group by from_id, to_id;