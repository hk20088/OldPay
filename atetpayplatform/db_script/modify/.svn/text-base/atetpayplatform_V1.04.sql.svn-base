---订单表，新增atetId列（服务器下发的设备唯一Id）,APK版本号列,更新订单时间 列,是否为扫码支付列
alter table t_payplatform_order add atetId varchar2(20);
alter table t_payplatform_order add versionCode varchar2(10);
alter table t_payplatform_order add updateTime date;
alter table t_payplatform_order add isScanPay number(1);  ---0代表扫码支付，1代表非扫码支付。
alter table t_payplatform_order add isSendGood number(1); ---0代表已发货，1代表未发货

---2014年11月19日
alter table　t_payplatform_order add clientIP varchar2(20); 

---绑定手机号表,添加atetId列。
alter table t_Payplatform_Bindtel add atetId varchar2(20);


---支付点表，添加支付点状态列，同步时间列
alter table t_payplatform_paypoint add state number(1);
alter table t_payplatform_paypoint add syncTime date;

---密钥表，添加更新时间列
alter table t_payplatform_secretKey add updateTime date;