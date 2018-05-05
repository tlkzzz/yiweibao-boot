package com.src.common.base.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.entity.PageBean;
import com.src.common.base.service.BaseService;

public abstract class BaseServiceImpl<T, ID extends Serializable>  implements BaseService<T,ID> {


	public abstract BaseDao<T,ID> getGenericDao();
	
	public void save(T entity)  throws Exception{
		getGenericDao().save(entity);
	}

	public void delete(ID id)  throws Exception{
		getGenericDao().del(id);
	}

	public void update(T entity)  throws Exception{
		getGenericDao().update(entity);
	}

	public T findById(ID id) {
		return getGenericDao().get(id);
	}

	public List<T> findAll() {
		return getGenericDao().getAll();
	}
	
	public PageBean<T> getPage(T t, PageBean<T> pageBean){
		return getGenericDao().search(t, pageBean);
	}
	
	public PageBean<T> getPageByParams(Map<String, Object> map, PageBean<T> pageBean){
		return getGenericDao().search(map, pageBean);
	}

}
