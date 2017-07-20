---------------- create tables ------------

---订单表
create table t_payplatform_order
(
       id number(9)  primary key not null,
       partnerId varchar2(16),    ---合作商ID
       orderNo varchar2(32) unique not null,
       userId number(10),
       atetId varchar2(20),
       deviceId varchar2(40), ---后台数据库生成的唯一标识，代表设备型号
	   productId varchar2(40),---设备的唯一标识（硬件信息）
       deviceCode varchar2(40),
       deviceType number(1),  ---设备类型：1：电视      2：手机   3：平板
       channelId varchar2(20),   ---渠道Id
       cpId varchar2(40),
       cpOrderNo varchar2(120),               ---外部订单号，即CP端的订单号
       appId varchar2(40),
       packageName varchar2(120),         ---应用、游戏 包名
       payPoint varchar2(32),           
	   payPointName varchar(20),     ---支付名称 例：金币支付，道具支付
	   payPointMoney number(9),    ---支付点的单价，单位：分
       counts number(9),
       amount number(9) not null,           ---支付金额 留 2位小数，单位：分
       amountType number(1) default 0 not null,  ---交易币种：默认为：0 人民币
       payType number(2) default 0 not null,   ---支付方式 ：0代表联通支付，1代表支付宝支付，2代表快钱
       createTime date default sysdate not null,
       state number(1) not null,
       gameName varchar2(40),
       versionCode varchar2(10),
       isScanPay number(1),　　　---0代表扫码支付，1代表非扫码支付。
       isSendGood number(1),　　---0代表已发货，1代表未发货
       updateTime date,
       clientIP varchar2(20),
       telephone varchar2(20),
       commandid varchar2(25),
       channelShortCode varchar2(8),
       paymentCreatedOrderNo varchar2(120)  --支付操作所生成的订单号；比如支付宝、快钱等生成的交易号
);

---绑定手机号
create table t_payplatform_bindTel
(
	id number(9) primary key not null,
	userId number(10),
	atetId varchar2(20),
	productId varchar2(40) not null,
	deviceCode varchar2(40),
	telephone varchar2(20),
	deviceType number(1),	--设备类型：1：电视      2：手机   3：平板
	state number(1) not null, --绑定状态，0：绑定成功；1：还未验证通过(针对盒子)
	lastSendTime date,
	sendCount number(4),
	ext varchar(100)	--扩展字段
);

---密钥表
create table t_payplatform_secretKey
(
       id number(9)  primary key not null,
       cpId varchar2(40),
       cpName varchar2(40),
       appId varchar2(40) not null,
       privateKey varchar2(1024) not null,
       publicKey varchar2(256) not null,
       cpNotifyUrl varchar2(256),      ---CP服务器接收通知的地址
       createTime date default sysdate not null,  ---同步时间
       updateTime date      ---更新时间。
);

---应用支付点表
create table t_payplatform_paypoint
(
       id number(9) primary key not null,
       appId varchar2(40) not null,
       payPoint varchar2(32),      ---支付点
       payName varchar2(20),       ---支付点名称
       money number(9,2),          ---支付点单价，单位分。
	   state number(1),            ---支付点状态 （CP系统传过来的） 0 代表正常  1 代表此支付点已被删除。
       syncTime date,              ---同步时间。 
);

--同步游戏信息表
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

--失败日志表
create table t_payplatform_failLog
(
	id number(9) primary key not null,
	operaType number(3) not null,			--操作类型；0 支付，1 订单查询  
	failCode number(5) not null,  			--失败码
	failDesc varchar2(100),       			--失败描述信息
	receivedDataMsg varchar2(1000),  		--接收到的原数据
	receivedSignMsg varchar2(1000),   		--接收到的数字签名
	logTime date default sysdate not null,  --日志记录时间
	payOrderId number(9)			 		--若请求生成了订单，订单id
);


---创建序列
create sequence seq_payplatform_order_id start with 1 increment by 1 nomaxvalue nocycle nocache;
create sequence seq_payplatform_secretKey_id start with 1 increment by 1 nomaxvalue nocycle nocache;
create sequence seq_payplatform_paypoint_id start with 1 increment by 1 nomaxvalue nocycle nocache;
create sequence seq_payplatform_failLog_id start with 1 increment by 1 nomaxvalue nocycle nocache;
create sequence seq_payplatform_bindTel_id start with 1 increment by 1 nomaxvalue nocycle nocache;
create sequence seq_payplatform_syncGame_id start with 1 increment by 1 nomaxvalue nocycle nocache;
