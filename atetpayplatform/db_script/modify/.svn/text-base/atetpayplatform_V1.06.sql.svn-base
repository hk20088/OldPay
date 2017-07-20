
---同步设备信息表
---注：1、后台审批完设备后调用接口将此设备信息同步过来。
---2、弱联网方式支付时通过deviceId查询渠道等信息，记录到订单表中。
create table t_payplatform_syncDevice
(
	id number(9) primary key not null,
	deviceId varchar2(20) not null,    ---设备Id(后台数据库生成的设备表唯一标识，非设备硬件Id)
	deviceCode varchar2(64),           ---设备Code
	deviceName varchar2(64),           ---设备类型：例：乐视TV
	deviceType number(1),              ---设备类型：1：电视      2：手机   3：平板
	channelId varchar2(32),            ---渠道Id
	channelName varchar2(40),          ---渠道名称
	createTime date default sysdate not null  
);

---支付方式表
create table t_payplatform_payType
(
	id number(9) primary key not null,
	payTypeId varchar2(20) not null,    ---支付方式ID
	payTypeCode varchar2(20),　　　　　　---支付方式代码，添加支付方式时填写
	payTypeName varchar2(20),　　　　　　---支付方式名称，如：联通，支付宝等
	remark varchar2(256),　　　　　　　　---备注0
	state number(1)　　　　　　　　　　　　---状态：0代表正常　　1代表冻结（冻结后此支付方式在所有设备不可用）
);

---设备与支付方式中间表（保存某款设备不支持某种支付方式的关联关系）
create table t_payplatform_device_payType
(
	deviceId varchar2(20) not null,    ---设备Id
	payTypeId varchar2(20) not null    ---支付方式ID
);

---序列
create sequence seq_payplatform_syncDevice_id start with 1 increment by 1 nomaxvalue nocycle nocache;
create sequence seq_payplatform_payType_id start with 1 increment by 1 nomaxvalue nocycle nocache;
