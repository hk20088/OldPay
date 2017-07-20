package com.newspace.payplatform.token.vo;

import com.newspace.payplatform.base.JsonVo;

/**
 * 封装令牌请求响应参数的VO类
 * @author huqili
 *
 */
public class TokenRespVo implements JsonVo
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 服务器下发的令牌（加密后的）
     */
    private String token;
    /**
     * 返回码
     */
    private Integer code;
    
    public Integer getCode()
    {
        return code;
    }
    
    public void setCode(Integer code)
    {
        this.code = code;
    }
    
    public String getToken()
    {
        return token;
    }
    
    public void setToken(String token)
    {
        this.token = token;
    }
}
