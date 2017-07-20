
--新增:订单交易轨迹表：
create table t_payplatform_wotrace
(
	id number(9) primary key not null,
	transactionId varchar2(120) not null,
	phone varchar2(11) not null,
	trace varchar2(2014) not null,
	createTime date default sysdate not null  --数据记录时间
);
create sequence seq_payplatform_wotrace_id start with 1 increment by 1 nomaxvalue nocycle nocache;
commit;