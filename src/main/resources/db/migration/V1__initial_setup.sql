drop table if exists lesson;
drop table if exists classroom;
create table classroom(id bigint primary key AUTO_INCREMENT, name varchar(255) not null);
create table lesson(id bigint primary key AUTO_INCREMENT, name varchar(255) not null,classroom_id bigint not null, constraint fk_lesson_classroom FOREIGN KEY (classroom_id) REFERENCES classroom(id));