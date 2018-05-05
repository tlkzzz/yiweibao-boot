package com.src.common.base.service;

import com.src.common.base.entity.PageBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface BaseService<T, ID extends Serializable> {

	void save(T entity) throws Exception;

	void delete(ID id) throws Exception;

	void update(T entity) throws Exception;

	T findById(ID id);

	List<T> findAll();
	
	PageBean<T> getPage(T t, PageBean<T> pageBean);
	
	PageBean<T> getPageByParams(Map<String, Object> map, PageBean<T> pageBean);

}
