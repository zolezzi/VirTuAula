INSERT INTO account(username, first_name, last_name, email, account_type_id, account_type_role, experience, user_id) VALUES ('charly','Carlos', 'Cardozo', 'carlos@gmail.com', 2, 'TeacherAccount', null, 1);
INSERT INTO account(username, first_name, last_name, email, account_type_id, account_type_role, experience, user_id) VALUES ('german','German', 'Cabrera', 'german@gmail.com', 1, 'StudentAccount', 0, 2);

INSERT INTO teachers_students(teacher_id, student_id) VALUES(1,2);

INSERT INTO accounts_classrooms(account_id, classroom_id) VALUES(1,1);
INSERT INTO accounts_classrooms(account_id, classroom_id) VALUES(1,2);
INSERT INTO accounts_classrooms(account_id, classroom_id) VALUES(2,1);
INSERT INTO accounts_classrooms(account_id, classroom_id) VALUES(2,2);