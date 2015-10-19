package com.warehouse.common;

import java.util.List;
/**
 * 
 * ClassName: PaginQueryResult <br/>
 * Function: 分页查询结果对象. <br/>
 *
 * @since JDK 1.6
 */
public class PaginQueryResult<T> {
	
	//总页数
	private int pageCount;
	
	//总行数
	private int total;
	
	//当前页
	private int page; 
	
	//页行数集
	private List<T> rows;
	
    public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getPageCount()
	{
		return pageCount;
	}

	public void setPageCount(int pageCount)
	{
		this.pageCount = pageCount;
	}

}
