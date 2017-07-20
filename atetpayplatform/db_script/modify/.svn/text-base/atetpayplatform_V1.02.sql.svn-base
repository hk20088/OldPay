--订单表：将原先deviceId列修改为productId；并新增deviceId列; 新增gameName列
alter table t_payplatform_order rename column deviceId to productId;
alter table t_payplatform_order add deviceId varchar2(40);
alter table t_payplatform_order add gameName varchar2(40);

--绑定手机号表：将列deviceId修改为productId
alter table t_payplatform_bindTel rename column deviceId to productId;

--绑定手机号表：新增sendCount列
alter table t_Payplatform_Bindtel add sendCount number(4);
commit;

--新增：同步游戏信息表
create table t_payplatform_syncGame
(
	id number(9) primary key not null,
	packageName varchar2(120) not null,
	appId varchar2(40) not null,
	cpId varchar2(40) not null,
	gameId varchar2(40) not null,
	gameName varchar2(40) not null,
	gameCreateTime date,              --游戏创建时间
	createTime date default sysdate not null  --数据记录时间
);
create sequence seq_payplatform_syncGame_id start with 1 increment by 1 nomaxvalue nocycle nocache;
commit;