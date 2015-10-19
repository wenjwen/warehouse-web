package com.warehouse.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;


public interface BaseMapper<T> {

	/**
	 * <p>Title: insert</p>
	 * <p>Description: 根据传入一个对象插入</p>
	 * @param t：插入对象
	 * @return
	 * @author lisb@createw.com  2015年7月28日 上午10:08:00
	 */
	public int insert(T t);

	public T selectByPrimaryKey(Object id);

	/**
	 * <p>Title: delete</p>
	 * <p>Description: 根据传入id删除</p>
	 * @param id
	 * @return
	 * @author lisb@createw.com  2015年7月28日 上午10:06:15
	 */
	public int deleteByPrimaryKey(Object id);

	/**
	 * <p>Title: updateByPrimaryKeySelective</p>
	 * <p>Description: 根据传入一个对象更新</p>
	 * @param t  传入一个更新的对象
	 * @return
	 * @author lisb@createw.com  2015年7月28日 上午10:04:21
	 */
	public int updateByPrimaryKeySelective(T t);
	
	/**
	 * <p>Title: updateByPrimaryKey</p>
	 * <p>Description: 根据传入一个对象更新主键</p>
	 * @param t  传入一个对象
	 * @return
	 * @author lisb@createw.com  2015年7月28日 上午10:05:19
	 */
	public int updateByPrimaryKey(T t);
	

	/**
	 * <p>Title: queryByCount</p>
	 * <p>Description: 查询表的总行数</p>
	 * @return  返回总行数
	 * @author lisb@createw.com  2015年7月28日 上午10:02:37
	 */
	public int queryByCount(@Param(value="t")T t);

	/**
	 * <p>Title: queryById</p>
	 * <p>Description: 根据ID查询对象</p>
	 * @param id
	 * @return
	 * @author lisb@createw.com  2015年7月28日 上午10:03:46
	 */
	public T queryById(Object id);
 
	  /**
     * <p>Title: findAll</p>
     * <p>分面查询: TODO</p>
     * @param rownum：总共表记录数 
     * @param rn :  起始行号
     * @param t ： 传入一个对象，实现带参数的查询
     * @return
     * @author lisb@createw.com  2015年7月28日 上午9:56:13
     */
    // List<T> findByPage(@Param(value="rownum")Integer rownum,@Param(value="rn")Integer rn,@Param(value="t")T t);
    
    /**
     * 根据条件查询
     * @param t
     * @return
     * @author liaodg
     */
    List<T> findByParam(@Param(value="t")T t);
    
    // 分布
    int count(@Param("t")T t);
    List<T> queryForPage(@Param("t")T t, @Param("start")int start, @Param("size")int size);
}
