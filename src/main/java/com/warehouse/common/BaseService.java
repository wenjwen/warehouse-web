package com.warehouse.common;



public abstract class BaseService<T>
{
	public abstract BaseMapper<T> getMapper();

	public int insert(T t) throws Exception
	{
		return getMapper().insert(t);
	}

	public T findById(Object id) throws Exception
	{
		return getMapper().selectByPrimaryKey(id);
	}

	public int deleteById(Object id)
	{
		return getMapper().deleteByPrimaryKey(id);
	}


	public int batchDelete(String[] ids)
	{
		for (String id : ids)
		{
			getMapper().deleteByPrimaryKey(id);
		}
		return 0;
	}

	public int update(T t)
	{
		return getMapper().updateByPrimaryKey(t);
	}

	public int queryByCount(T t)
	{
		return getMapper().queryByCount(t);
	}


	/**
	 * 分页查询
	 * 
	 * @param t 参数对象
	 * @param pageNum 页数
	 * @param size  页大小
	 * @return
	 */
	public PaginQueryResult<T> paginQuery(T t, int pageNum, int size)
	{
		PaginQueryResult<T> pqr = new PaginQueryResult<T>();
		int count = getMapper().count(t);
		
		// 当前页数为0与表数据行数为0都不会执行查询数据操作
		if (count > 0 && pageNum > 0)
		{
			int pageCount = (count - 1) / size + 1;  // 总页数
	        int start = size * (pageNum - 1);// rownum范围起始值
	        // int end = size * index + 1;// rownum范围终结值
			pqr.setRows(getMapper().queryForPage(t, start, size));
			pqr.setTotal(count);
			pqr.setPage(pageNum);
			pqr.setPageCount(pageCount);
		}
		
		return pqr;
	}
	
}
