-- https://leetcode.com/problems/status-of-flight-tickets/
-- windows, hard, cte

with A as (select *, row_number() over (partition by flight_id order by booking_time) as r_no from Passengers),
     B as (select passenger_id, "Confirmed" as Status
           from A a
                    join Flights f on a.flight_id = f.flight_id
           where a.r_no <= f.capacity
           union all
           select passenger_id, "Waitlist" as Status
           from A a
                    join Flights f on a.flight_id = f.flight_id
           where a.r_no > f.capacity)
select *
from B
order by passenger_id;