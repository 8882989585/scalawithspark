-- https://leetcode.com/problems/grand-slam-titles/

with w as (select Wimbledon as player_id, count(*) as grand_slams_count from Championships group by Wimbledon),
     f as (select Fr_open as player_id, count(*) as grand_slams_count from Championships group by Fr_open),
     u as (select US_open as player_id, count(*) as grand_slams_count from Championships group by US_open),
     a as (select Au_open as player_id, count(*) as grand_slams_count from Championships group by Au_open),
     g as (select * from a union all select * from w union all select * from u union all select * from f),
     h as (select player_id, sum(grand_slams_count) as grand_slams_count from g group by player_id)
select p.player_id, p.player_name, grand_slams_count
from h m
         join Players p on m.player_id = p.player_id;