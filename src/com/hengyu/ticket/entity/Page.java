package com.hengyu.ticket.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hengyu.ticket.common.Const;

/**
 * 分页对象. 包含当前页数据及分页信息如总记录数.
 * @author wangwu
 *
 */
@SuppressWarnings("all")
public class Page implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static int DEFAULT_PAGE_NO = 1;
	private static int DEFAULT_PAGE_SIZE = Const.PAGE_SIZE;
	
	private int pageNo = DEFAULT_PAGE_NO;//页码,从1开始
	private int pageSize = DEFAULT_PAGE_SIZE; // 每页的记录数
    private long totalCount; // 总记录数
    private long totalPage; //总页数
	private List data; // 当前页中存放的记录
	private Long startOfPage;
	private Object param;//查询条件对象
	private Object admin;//登录用户
	//时间戳
	private long timestamp;

    //没有数据显示类型
    private int notDataType = 0;
    
    public Page() {
    	this(DEFAULT_PAGE_NO, DEFAULT_PAGE_SIZE, 0, new ArrayList());
    }
    
    public Page(int pageNo, int pageSize) {
    	this(pageNo, pageSize, 0, new ArrayList<Object>());
    }
    
    public Page(int pageNo, int pageSize, int totalCount, List data) {
    	this.pageNo = pageNo;
    	this.pageSize = pageSize;
    	this.totalCount = totalCount;
    	this.data = data;
    	this.totalPage = this.totalCount%this.pageSize==0?this.totalCount/this.pageSize:this.totalCount/this.pageSize+1;
    }
    
    /**
     * 该页是否有下一页.
     */
    public boolean hasNextPage() {
        return pageNo < this.totalPage;
    }
    
    /**
     * 该页是否有上一页.
     */
    public boolean hasPreviousPage() {
        return pageNo > 1;
    }
    
    /**
     * 获取当前页第一条数据的位置
     * @return
     */
    public Long getStartOfPage() {
    	if(startOfPage!=null){
    		return startOfPage;
    	}
        return (pageNo - 1L) * pageSize;
    }
    
    public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		startOfPage = (pageNo - 1L) * pageSize;
		if(pageNo==0){
			pageNo = 1;
		}
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		startOfPage = (pageNo - 1L) * pageSize;
		this.pageSize = pageSize;
		this.totalPage = this.totalCount%this.pageSize==0?this.totalCount/this.pageSize:this.totalCount/this.pageSize+1;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
		this.totalPage = this.totalCount%this.pageSize==0?this.totalCount/this.pageSize:this.totalCount/this.pageSize+1;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;		
	}

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}

	public Object getAdmin() {
		return admin;
	}

	public void setAdmin(Object admin) {
		this.admin = admin;
	}

	public void setStartOfPage(Long startOfPage) {
		this.startOfPage = startOfPage;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

    public int getNotDataType() {
        return notDataType;
    }

    public void setNotDataType(int notDataType) {
        this.notDataType = notDataType;
    }
}
