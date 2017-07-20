create table t_payplatform_msg
(
    id number(9) primary key not null,
    telephone varchar2(13) not null,
    content varchar2(200) not null,
    state number(1) not null,
    projectCode number(1) not null,
    createTime date default sysdate not null
);

create sequence seq_payplatform_msg_id start with 1 increment by 1 nomaxvalue nocycle nocache;

commit;