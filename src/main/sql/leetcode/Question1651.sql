-- https://leetcode.com/problems/hopper-company-queries-iii/
-- cte, recursive, advance select

with RECURSIVE B as (select 1 as month
from Rides r
union
select month + 1 as month
from B
where month
    < 12)
    , A as (
Select
    month (r.requested_at) as month, sum (ar.ride_distance) as average_ride_distance, sum (ar.ride_duration) as average_ride_duration
from AcceptedRides ar
    join Rides r
on ar.ride_id = r.ride_id
where r.requested_at > date ('2019-12-31') and r.requested_at < date ('2021-01-01')
group by month (r.requested_at)),
    C as (
select b.month, Coalesce (average_ride_distance, 0.00) as average_ride_distance, coalesce (average_ride_duration, 0.00) as average_ride_duration
from B b left join A a
on b.month = a.month)
select month, (select round(sum (average_ride_distance) / 3, 2) from C a where a.month < b.month + 3 and a.month >= b.month) as average_ride_distance, (select round(sum (average_ride_duration) / 3, 2) from C a where a.month < b.month + 3 and a.month >= b.month) as average_ride_duration
from C b
where b.month < 11;