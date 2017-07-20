package com.newspace.common.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 分页标签
 * @author huqili
 * @since jdk1.6 strutst2.3.4
 * @version 1.0
 */
public class PagerTag extends ComponentTagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 当前页码
     */
    private int currentPage;
    /**
     * 标签中css样式的class
     */
    private String styleClass;
    /**
     * 跳转url
     */
    private String url;
    /**
     * 跳转时指定的form
     */
    private String form;

    
    public String getForm()
    {
    
        return form;
    }

    
    public void setForm(String form)
    {
    
        this.form = form;
    }

    @Override
    public Component getBean(ValueStack valueStack, HttpServletRequest request,
                             HttpServletResponse response)
    {
        Component c = new PageResult(valueStack);
        return c;
    }
    
    @Override
    protected void populateParams()
    {
        super.populateParams();
        PageResult page = (PageResult)component;
        page.setCurrentPage(currentPage);
        page.setStyleClass(styleClass);
        page.setUrl(url);
        page.setForm(form);
    }
    
    public int getCurrentPage()
    {
    
        return currentPage;
    }

    
    public void setCurrentPage(int currentPage)
    {
    
        this.currentPage = currentPage;
    }

    
    public String getUrl()
    {
    
        return url;
    }

    
    public void setUrl(String url)
    {
    
        this.url = url;
    }

    
    public String getStyleClass()
    {
    
        return styleClass;
    }

    
    public void setStyleClass(String styleClass)
    {
    
        this.styleClass = styleClass;
    }

}
