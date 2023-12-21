덧글 게시판 project 순서

1)model 확정
- create table board  
- getter/setter 
drop table board
--Board
create table board(
num int primary key,
name varchar(30),
pass varchar(20),
subject varchar(100),
content varchar(4000),
file1 varchar(100),
regdate date,
readcnt number(10),
boardid varchar(1));   //게시판 분류

create sequence boardseq;
--Comment
create table boardcomment (
ser int primary key,
num int,      //board num
content varchar(2000),
regdate date);

create sequence boardcomseq;
