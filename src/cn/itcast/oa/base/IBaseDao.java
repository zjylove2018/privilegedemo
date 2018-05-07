package cn.itcast.oa.base;

import java.util.List;

/**
 * 抽取通用DAO接口
 * @author zhaoqx
 *
 * @param <T>
 */
public interface IBaseDao<T> {
	/**
	 * 保存
	 */
	public void save(T entity);
	
	/**
	 * 根据id删除
	 */
	public void delete(Long id);
	
	/**
	 *  根据id修改实体
	 */
	public void update(T entity);
	
	/**
	 * 根据id查询实体
	 */
	public T findById(Long id);
	
	/**
	 * 查询所有
	 */
	public List<T> findAll();
	
	/**
	 * 根据id数组查询
	 */
	public List<T> findByIds(Long[] ids);
}
