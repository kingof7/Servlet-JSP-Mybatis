create table member(
    num number(10),
    id varchar2(50) not null,
    password varchar2(100) not null,
    name varchar2(50) not null,
    jumin1 varchar2(20) not null,
    
    jumin2 varchar2(20) not null,
    email varchar2(50) not null,
    zipcode varchar2(50) not null,
    address varchar2(100) not null,
    job varchar2(200) not null,
    
    mailing varchar2(5) not null,
    interest varchar2(50) not null,
    member_level varchar2(6) not null,
    register_date date not null,
    
    primary key(num)
);

create sequence member_num_seq;

create table zipcode(
    zipcode varchar2(50),
    sido varchar2(70),
    gugun varchar2(80),
    dong varchar2(100),
    ri varchar2(100),
    bunji varchar2(100)
);

alter table board add file_name varchar2(200);
alter table board add path varchar2(100);
alter table board add file_size number(10);


<hr계정>
select
    e.employee_id,
    e.first_name,
    e.hire_date,
    e.job_id,
    e.department_id,
    d.department_name
    
from employees e, departments d
where e.department_id = d.department_id
and d.department_name='IT';

<실시간 댓글기능 게시판>
create table linereply(
    bunho number(8),
    line_reply varchar2(500),
    primary key (bunho)
);

create sequence linereply_bunho_seq;