// **********************************************************************
//
// Copyright (c) 2003-2013 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.5.1
//
// <auto-generated>
//
// Generated from file `SmInt.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.newspace.payplatform.unicomsms.ice;

public final class strConentSeqHelper
{
	public static void write(IceInternal.BasicStream __os, byte[] __v)
	{
		__os.writeByteSeq(__v);
	}

	public static byte[] read(IceInternal.BasicStream __is)
	{
		byte[] __v;
		__v = __is.readByteSeq();
		return __v;
	}
}
