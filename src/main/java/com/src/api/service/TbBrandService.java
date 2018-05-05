package com.src.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbBrand;
import com.src.api.entity.TbCategory;

public interface TbBrandService extends BaseService<TbBrand, Long>{

	/**
	 * 查询所有品牌
	 * @return
	 */
	List<Map<String, Object>> getAll();

	HashMap<String, Object> findForJson(HashMap<String, String> params);

	List<Map<String, Object>> getAll1(String categoryId);

	Map<String, Object> getList();
	
	/**
	 * 根据类型id查询信息
	 * */
	List<TbBrand> findList(Long tcId);

	Map<String, Object> getByParam(Map<String, String> param);

	TbBrand findOne(TbBrand tbBrand);

	/**
	 * 查询是否有相同品牌名称在相同类型下除当前编辑tbId以外
	 * @param tcId 类型id
	 * @param tbName 品牌名称
	 * @param tbId 品牌id
	 * @param addUserId 添加人id 一般值tb_company单位id
	 * @return
	 */
	TbBrand findBrandByTcIdAndTbName(String tcId, String tbName, String tbId, String addUserId);

}
