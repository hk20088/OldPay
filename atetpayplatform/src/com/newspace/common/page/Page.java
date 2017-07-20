package com.newspace.common.page;

import java.util.Collection;

public interface Page
{
    void setPageCount(int pageCount);
    int getPageCount();
    
    void setCurrentPage(int currentPage);
    int getCurrentPage();
    
    Collection<?> getVos();
    void setVos(Collection<?>vos);
    
    void setRecordCount(int recordCount);
    int getRecordCount();
    
    int getShowCount();
    void setShowCount(int showCount);
}
