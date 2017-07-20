package com.newspace.payplatform.token.vo;

import java.io.Serializable;
/**
 * 令牌VO类
 * @author huqili
 *
 */
public class TokenVo implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 令牌创建时间
     */
    private long createTime;
    /**
     * 生成的令牌串
     */
    private String token;
    
    public String getToken()
    {
        return token;
    }
    
    public void setToken(String token)
    {
        this.token = token;
    }
    
    public long getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(long createTime)
    {
        this.createTime = createTime;
    }
}
