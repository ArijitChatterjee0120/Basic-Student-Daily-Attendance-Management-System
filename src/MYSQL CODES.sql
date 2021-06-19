create table student_details
(
Roll_number int auto_increment,
Firstname varchar(30),
Lastname varchar(30), 
Sex varchar(20),
Mailid varchar(30),
State varchar(20), 
District varchar(20), 
Course varchar(20), 
Department varchar(20),
Date_Of_Birth varchar(40), 
Address varchar(50),
primary key(Roll_number)
);
select *from student_details; 
create table student_attendance(
	Roll_number int primary key auto_increment,
    STUDENT_ID int not null,
    PRESENT boolean default false,
    created_at timestamp default now(),
    foreign key(STUDENT_ID) references student_details(Roll_number) on delete cascade);
  select *from student_details;
  select *from student_details where District='North24Parganas';
  select *from student_details where Firstname like 'A%'; 
  select *from student_details where Roll_number between 1 and 5;
   select *from student_details order by Firstname desc;
   select * from student_details inner join student_attendance on student_details.Roll_number=student_attendance.STUDENT_ID where PRESENT='0';
   select * from student_details inner join student_attendance on student_details.Roll_number=student_attendance.STUDENT_ID where PRESENT='1';
   select *from student_details where Date_of_Birth between '01-Jan-2000' and '20-May-2002';
   delete from student_details where Roll_number='10';