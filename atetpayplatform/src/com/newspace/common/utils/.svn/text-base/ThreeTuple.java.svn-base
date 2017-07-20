package com.newspace.common.utils;

/**
 * ThreeTuple.java 
 * 描 述:  工具类，当函数需要返回三个对象时使用。
 * 作 者:  liushuai
 * 历 史： 2014-4-26 创建
 */
public final class ThreeTuple<A, B, C>
{
	public final A first;
	public final B second;
	public final C third;

	/**
	 * @param first 参数
	 * @param second 参数
	 * @param third 参数
	 */
	public ThreeTuple(A first, B second, C third)
	{
		this.first = first;
		this.second = second;
		this.third = third;
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
		sb.append(" , ");
		if (third != null)
			sb.append(third.toString());
		else
			sb.append("NULL");
		return sb.toString();
	}
}
