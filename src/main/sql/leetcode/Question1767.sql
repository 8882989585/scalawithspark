-- https://leetcode.com/problems/find-the-subtasks-that-did-not-execute/

WITH RECURSIVE A AS (select task_id, 1 as subtask_id
                     from Tasks
                     union
                     select a.task_id, a.subtask_id + 1 as subtask_id
                     from A a
                              join Tasks t on a.task_id = t.task_id
                     where a.subtask_id + 1 <= t.subtasks_count)
select a.task_id, a.subtask_id
from A a
         left join Executed e on a.task_id = e.task_id and a.subtask_id = e.subtask_id
where e.task_id is null;