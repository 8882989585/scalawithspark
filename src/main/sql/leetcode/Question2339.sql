-- https://leetcode.com/problems/all-the-matches-of-the-league/

select a.team_name as home_team, b.team_name as away_team
from Teams a
         cross join Teams b
where a.team_name != b.team_name;