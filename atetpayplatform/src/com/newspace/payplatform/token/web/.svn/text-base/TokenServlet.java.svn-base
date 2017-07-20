package com.newspace.payplatform.token.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.newspace.common.utils.NativeEncryptUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.token.vo.TokenReqVo;
import com.newspace.payplatform.token.vo.TokenRespVo;
/**
 * 请求令牌接口Servlet
 * @author huqili
 *
 */
public class TokenServlet extends BaseServlet
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        logger.debug("client request token...");
        TokenReqVo reqVo = new TokenReqVo();
        TokenRespVo respVo = new TokenRespVo();
        respVo.setCode(padRequestVo(req, reqVo));
        
        if (respVo.getCode()==ReturnCode.SUCCESS.getCode())
        {
            String token = createToken(req);
            if(token==null)
            {
                respVo.setCode(ReturnCode.TOKEN_CREATEERR.getCode());
            }
            else
            {
                try
                {
                	String token2 = validateToken(req);
                    respVo.setToken(NativeEncryptUtils.encryptAES(token,reqVo.getTime()+""));
                    respVo.setCode(ReturnCode.SUCCESS.getCode());
                }
                catch (Exception e)
                {
                	logger.error("请求令牌接口_令牌加密错误！",e);
                    respVo.setCode(ReturnCode.ERROR.getCode());
                }
            }
        }
        outputResult(resp, respVo);
    }
}
