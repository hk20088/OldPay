package com.newspace.payplatform.syncgameinfo.bo;

import com.newspace.common.bo.GenericBo;
import com.newspace.payplatform.syncgameinfo.dao.SyncGameDao;
import com.newspace.payplatform.syncgameinfo.vo.SyncGameReq;
import com.newspace.payplatform.syncgameinfo.vo.SyncGameVo;

/**
 * SyncGameBo.java 
 * 描 述:  同步游戏信息Bo接口  
 * 作 者:  liushuai
 * 历 史： 2014-9-16 创建
 */
public interface SyncGameBo extends GenericBo<SyncGameVo, SyncGameDao>
{
	/**
	 * 同步游戏信息，如果不存在则插入，如果已经存在则修改。
	 */
	public int saveGameInfo(SyncGameReq vo);
}
