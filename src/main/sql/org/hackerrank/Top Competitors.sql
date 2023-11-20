-- https://www.hackerrank.com/challenges/full-score/problem?isFullScreen=true
select hacker_id, name
from (select v.hacker_id, h.name, v.cnt
      from (select hacker_id, count(*) as cnt
            from (select s.hacker_id
                  from Submissions s
                           left join (select c.challenge_id, c.difficulty_level, d.score
                                      from Challenges c
                                               left join Difficulty d on c.difficulty_level = d.difficulty_level) t
                                     on s.challenge_id = t.challenge_id
                  where s.score = t.score) u
            group by hacker_id
            having count(*) > 1) v
               join Hackers h on v.hacker_id = h.hacker_id) w
order by cnt desc, hacker_id;