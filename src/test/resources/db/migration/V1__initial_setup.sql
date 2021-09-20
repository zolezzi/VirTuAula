drop table if exists option_task;
drop table if exists task;
drop table if exists lesson;
drop table if exists classroom;
create table classroom(id bigint primary key AUTO_INCREMENT, name varchar(255) not null);
create table lesson(id bigint primary key AUTO_INCREMENT, name varchar(255) not null,classroom_id bigint, constraint fk_lesson_classroom FOREIGN KEY (classroom_id) REFERENCES classroom(id));
create table task(id bigint primary key AUTO_INCREMENT, statement varchar(255) not null, state varchar(15) default 'UNCOMPLETED', answer bigint, correct_answer bigint not null, lesson_id bigint, constraint fk_task_lesson FOREIGN KEY (lesson_id) REFERENCES lesson(id));
create table option_task(id bigint primary key AUTO_INCREMENT, val varchar(255) not null, task_id bigint, constraint fk_option_task FOREIGN KEY (task_id) REFERENCES task(id));