package com.newspace.payplatform.unicomhttp.valve;

import org.apache.commons.lang.math.RandomUtils;

import com.newspace.common.redis.RedisApi;
import com.newspace.common.utils.StringUtils;

/**
 * @description 渠道限流工具类,可以根据百分比来控制通知渠道订单
 * @author huqili
 * @date 2016年7月18日
 *
 */
public class ValveUtils {
	
	
	
	/**
	 * 是否限制此条订单通知到渠道
	 * 
	 * Redis里设置的值说明：
	 * percent:3000  :限流比例，80表示80%；88表示88%
	 * flag:3000  :标识符，值为true时表示向上取值，值为false时表示向下取值。初始值为 true
	 * @return true 限制，即不通知；false 不限制，即通知
	 */
	public static boolean control(String channel)
	{
		boolean bl = true;
		
		//查询设置的限流比例
		String percent = RedisApi.INSTANCE.getValue("percent:"+channel);
		
		//获取一个1-100内的随机数
		int c= RandomUtils.nextInt(100);
		
		if(Integer.valueOf(percent) >= c)
		{
			bl = false;
		}
		
		return bl;
	}

	/**
	 * 是否限制此条订单通知到渠道
	 * 
	 * Redis里设置的值说明：
	 * percent:3000  :限流比例，80表示80%；88表示88%
	 * flag:3000  :标识符，值为true时表示向上取值，值为false时表示向下取值。初始值为 true
	 * outlet:3000  :表示已经流出的订单数，不断递增，达到限流额时赋值为0。初始值为 0
	 * 
	 * @return true 限制，即不通知；false 不限制，即通知
	 */
	public static boolean valve(String channel)
	{
		boolean bl = true;
		double valve;  //限流的粒度
		String flag = RedisApi.INSTANCE.getValue("flag:"+channel);
		System.out.println("本次标识符是："+flag);
		
		//查询设置的限流比例
		String percent = RedisApi.INSTANCE.getValue("percent:"+channel);
		System.out.println("设置的比例是："+percent+"%");
		
		//查询已经流出的订单数量，和限流粒度作对比
		String outletNum = RedisApi.INSTANCE.getValue("outlet:"+channel);
		System.out.println("已流出的订单量为："+outletNum);
		
		if(StringUtils.isExistNullOrEmpty(flag,percent,outletNum))
		{
			System.out.println("存在空值...");
			return bl;
		}
		
		//根据限流比例计算出每隔多少条订单开始限流（每次限流一条）
		double _valve = Integer.parseInt(percent) / 10.0;
		
		//valve可能不被整除，为了最小颗粒的限流，这里轮循向上取值 ，向上取值
		if(flag.equals("true"))
		{
			//向上取值
			valve = Math.ceil(_valve);
			System.out.println("本次取值："+valve);
		}
		else
		{
			//向下取值
			valve = Math.floor(_valve);
			System.out.println("本次取值："+valve);
		}
		
		
		
		if(Integer.parseInt(outletNum) < (int)valve) 
		{
			//说明没有达到限流粒度，不限制
			bl = false;
			
			//将outlet:num 加1，表示流出一条订单
			RedisApi.INSTANCE.incr("outlet:"+channel);
			System.out.println("没有达到限流额，此条订单流出...");
		}
		else
		{
			System.out.println("达到限流额，此条订单限制...");
			//说明达到限流粒度，将outlet:num复原为 0，从头开始计算
			RedisApi.INSTANCE.setValue("outlet:"+channel, "0");
			
			//valve可能不被整除，为了最小颗粒的限流，这里设置标识符，用于轮循向上取值 ，向上取值
			if(flag.equals("true"))
			{
				RedisApi.INSTANCE.setValue("flag:"+channel, "false");
			}
			else
			{
				RedisApi.INSTANCE.setValue("flag:"+channel, "true");
			}
		}
	
		System.out.println("响应值："+bl);
		
		return bl;
	}
	
	public static void main(String[] args) {
		for(int i = 0; i <= 100; i++){
			System.out.println(control("3000"));
		}
		
	
		
	}
}
