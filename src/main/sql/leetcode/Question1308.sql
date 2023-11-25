-- https://leetcode.com/problems/running-total-for-different-genders/

-- solution 1
select gender, day, (select sum (score_points) from Scores t where t.day <= s.day and t.gender = s.gender) as total
from Scores s
group by gender, day
order by gender, day;

-- solution 2
SELECT gender, day, SUM (score_points) OVER(PARTITION BY gender ORDER BY day) AS total
FROM Scores;