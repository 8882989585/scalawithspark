-- https://leetcode.com/problems/accepted-candidates-from-the-interviews/

select candidate_id
from (select candidate_id, max(years_of_exp) as exp, sum(score) as ss
      from Candidates c
               join Rounds r on c.interview_id = r.interview_id
      group by candidate_id) tim1
where exp >= 2
  and ss > 15;