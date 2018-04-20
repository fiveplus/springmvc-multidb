-- 运行前请先创建数据库：db_test,db_test2,db_test3,db_test4,db_test5,db_test6,db_test7,db_test8,db_test9,db_test10,db_test11
use db_test;
drop table IF EXISTS tbl_test;
create table tbl_test(
id int(11) not null AUTO_INCREMENT,
name varchar(20),
age int(11),
PRIMARY KEY(id)
);
-- 创建存储过程
drop PROCEDURE IF EXISTS insert_proc;
DELIMITER ;;
create PROCEDURE insert_proc(
in count integer 
)
begin
DECLARE num int;
set num = 1;
while (num <= count) do
insert into tbl_test(name,age) values('user',24);
set num = num + 1;
end while;
commit;
end ;;

call insert_proc(50000000)