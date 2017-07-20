package com.newspace.payplatform.syncgameinfo.bo.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.newspace.common.bo.impl.GenericBoImpl;
import com.newspace.common.exception.BoException;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.syncgameinfo.bo.SyncGameBo;
import com.newspace.payplatform.syncgameinfo.dao.SyncGameDao;
import com.newspace.payplatform.syncgameinfo.vo.SyncGameReq;
import com.newspace.payplatform.syncgameinfo.vo.SyncGameVo;

/**
 * SyncGameBoImpl.java 
 * 描 述:  同步游戏信息Bo类  
 * 作 者:  liushuai
 * 历 史： 2014-9-16 创建
 */
public class SyncGameBoImpl extends GenericBoImpl<SyncGameVo, SyncGameDao> implements SyncGameBo
{
	/**
	 * 同步游戏信息，如果不存在则插入，如果已经存在则修改。
	 * @return int : ReturnCode返回码
	 */
	public int saveGameInfo(SyncGameReq req)
	{
		try
		{
			/**
			 * 因为一款游戏可有不同版本（例：电视版，手机版）
			 * 所以一款游戏有一个包名(packageName)，可有不同游戏ID(gameId)
			 * 即：一个packageName可对应多个gameId
			 * 
			 * 因为一个AppId对应一个packageName,且packageName有可能会被修改。
			 * 所以同步游戏的时候用appId来判断是否已将此游戏上传。
			 */
			DetachedCriteria criteria = DetachedCriteria.forClass(SyncGameVo.class);
			criteria.add(Restrictions.eq("appId", req.getAppId()));
			List<SyncGameVo> list = query(criteria, 2);
			SyncGameVo game = null;
			if (list != null && list.size() > 0)
			{
				// 如果存在则更新数据
				game = list.get(0);
				ganerateSyncGameVo(req, game);
			}
			else
			{
				// 如果不存在则执行插入操作
				game = ganerateSyncGameVo(req, null);
			}
			saveOrUpdate(game);
			return ReturnCode.SUCCESS.getCode();
		}
		catch (BoException e)
		{
			logger.error("同步游戏信息出错", e);
			return ReturnCode.ERROR.getCode();
		}
	}

	/**
	 * 生成最新的SyncGameVo对象
	 */
	private SyncGameVo ganerateSyncGameVo(SyncGameReq req, SyncGameVo gameVo)
	{
		if (gameVo == null)
		{
			gameVo = new SyncGameVo();
			gameVo.setCreateTime(new Date());
		}
		gameVo.setAppId(req.getAppId());
		gameVo.setCpId(req.getCpId());
		gameVo.setGameCreateTime(new Date(req.getGameCreateTime()));
		gameVo.setGameId(req.getGameId());
		gameVo.setGameName(req.getGameName());
		gameVo.setPackageName(req.getPackageName());

		return gameVo;
	}
}
