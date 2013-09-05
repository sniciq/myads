package com.ku6ads.dao.base;

import java.sql.Connection;
import java.util.List;

/**
 * DAO基础接口
 * @author yangHanguang
 *
 */
public interface BaseDao {
	
	/**
	 * 插入
	 * @param po
	 */
	public Integer insert(Object po);
	
	/**
	 * 根据ID号修改单个实体
	 * @param obj
	 */
	public Integer updateById(Object po);
	
	/**
	 * 根据ID号删除单个实体
	 * @param id
	 */
	public void deleteById(Integer id);
	
	/**
	 * 根据ID号查询单个实体
	 * @param obj
	 */
	public Object selectById(Integer id);
	
	/**
	 * 根据实体对象查询
	 * @param po
	 * @return
	 */
	public List selectByEntity(Object po);
	
	/**
	 * 由分页信息查询分页记录
	 * @param object
	 * @return
	 */
	public List selectByLimit(Object po);
	
	/**
	 *  获得数据库连接，注意该连接需要自己关闭，慎用!
	 * @return
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception;
	
	/**
	 * 得到ibatis执行的SQL语句<br>
	 * <a style="color: #FF0000; font-weight: bold; font-size: 20">
	 * 注意：如果有ibatis sql文件中有##形式的动态参数，该方法无法将parameterObject中的参数注入,<br>
	 * 将得到一个不完整的SQL
	 * </a>
	 * @param sqlMapId sql XML 文件中的域名+ID名
	 * @param parameterObject
	 * @return
	 */
	public String getSql(String sqlMapId, Object parameterObject);
	
	/**
	 * 为分页查询出记录总数
	 * @param object
	 * @return
	 */
	public Integer selectLimitCount(Object po);
}
