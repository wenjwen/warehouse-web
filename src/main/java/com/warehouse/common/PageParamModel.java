package com.warehouse.common;
/**
 * 
 * ClassName: PageParamModel <br/>
 * Function: 分页请求参数对象. <br/>
 *
 * @author Jerry
 * @version 1.0 <T>
 * @since JDK 1.6
 */
public class PageParamModel<T> {
	
	//当前页面数
	private int page=1;
	
	//每页的行数
	private int rows=10;
	
	//排序字段名称
	private String sort;
	
	// 排序次序, 默认asc
	private String order;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public String getOrder()
	{
		return order;
	}

	public void setOrder(String order)
	{
		this.order = order;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("[ page:%s, rows:%d, sort:%s ]", page,rows,sort);
	}
	

}
