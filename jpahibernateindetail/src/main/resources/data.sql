insert into course(id, name, created_date, last_updated_date, is_deleted) values (10001, 'JPA in 50 Steps', sysdate(), sysdate(), false);
insert into course(id, name, created_date, last_updated_date, is_deleted) values (10002, 'Spring in 50 Steps', sysdate(), sysdate(), false);
insert into course(id, name, created_date, last_updated_date, is_deleted) values (10003, 'Spring Boot in 100 Steps', sysdate(), sysdate(), false);

insert into passport(id, number) values (40001, 'E123456');
insert into passport(id, number) values (40002, 'F123456');
insert into passport(id, number) values (40003, 'G123456');

insert into student(id, name, passport_id) values (20001, 'Ranga', 40001);
insert into student(id, name, passport_id) values (20002, 'Adams', 40002);
insert into student(id, name, passport_id) values (20003, 'James', 40003);


insert into review(id, rating, description, course_id) values (50001, 'FIVE', 'Great Course', 10001);
insert into review(id, rating, description, course_id) values (50002, 'FOUR', 'Good Course', 10001);
insert into review(id, rating, description, course_id) values (50003, 'THREE', 'Nice Course', 10003);


insert into student_course(student_id, course_id) values('20001','10001');
insert into student_course(student_id, course_id) values('20002','10001');
insert into student_course(student_id, course_id) values('20003','10001');
insert into student_course(student_id, course_id) values('20001','10003');