-- https://leetcode.com/problems/find-the-quiet-students-in-all-exams/
-- cte, hard

with A as (select exam_id, min(score) as mm, max(score) as mx from Exam group by exam_id),
     B as (select distinct(e.student_id) as student_id
           from Exam e
                    join A a on e.exam_id = a.exam_id
           where e.score = a.mm || e.score = a.mx)
select s.*
from Student s
         join (select distinct(e.student_id) as student_id
               from Exam e
                        left join B b on e.student_id = b.student_id
               where b.student_id is null) c on s.student_id = c.student_id;