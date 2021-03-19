create table jsp_member(
id varchar2(50) primary key,
password varchar2(50) not null,
name varchar2(50),
gender varchar2(10),
birth date,
mail varchar2(100),
phone varchar2(50),
address varchar2(200),
reg date default sysdate);