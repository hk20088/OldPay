package com.newspace.common.utils;

/**
 * TwoTuple.java 
 * 描 述:  工具类，当函数需要返回两个对象时使用
 * 作 者:  liushuai
 * 历 史： 2014-4-25 创建
 */
public final class TwoTuple<A, B>
{

	public final A first;
	public final B second;

	/**
	 * @param a 参数
	 * @param b 参数
	 */
	public TwoTuple(A a, B b)
	{
		first = a;
		second = b;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		if (first != null)
			sb.append(first.toString());
		else
			sb.append("NULL");
		sb.append(" , ");
		if (second != null)
			sb.append(second.toString());
		else
			sb.append("NULL");
		return sb.toString();
	}
}
