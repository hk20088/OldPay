package com.newspace.common.page;

import java.util.Collection;

public class NoStackPage implements Page
{
    /**
     * 总页码
     */
    private int pageCount;       
    /**
     * 当前页码
     */
    private int currentPage = 1;    
    /**
     * 总记录数
     */
    private int recordCount;                
    /**
     * 分页中显示的记录对应的Vo类对象集合
     */
    private Collection<?> vos;              
    /**
     * 每一分页中显示的记录数量
     */
    private int showCount;
    
    public NoStackPage(int showCount,int currentPage)
    {
        this.showCount = showCount;
        this.currentPage = currentPage;
    }
    
    @Override
    public int getCurrentPage()
    {
        return currentPage;
    }
    @Override
    public int getPageCount()
    {
        return pageCount;
    }
    @Override
    public int getRecordCount()
    {
        return recordCount;
    }
    @Override
    public int getShowCount()
    {
        return showCount;
    }
    @Override
    public Collection<?> getVos()
    {
        return vos;
    }
    @Override
    public void setCurrentPage(int currentPage)
    {
        this.currentPage = currentPage;
    }
    @Override
    public void setPageCount(int pageCount)
    {
        this.pageCount = pageCount;
    }
    @Override
    public void setRecordCount(int recordCount)
    {
        this.recordCount = recordCount;
    }
    @Override
    public void setShowCount(int showCount)
    {
        this.showCount = showCount;
    }
    @Override
    public void setVos(Collection<?> vos)
    {
        this.vos = vos;
    } 
    
    
    
}
