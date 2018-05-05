package com.src.api.service;

import java.util.List;
import java.util.Map;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbAsset;

public interface TbAssetService extends BaseService<TbAsset, Long>{
	/**
	 * 
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public TbAsset findById(Long id);
	/**
	 * 新增
	 * @param t
	 * @return
	 */
	public TbAsset add(TbAsset t);
	/**
	 * 修改
	 * @param t
	 * @return
	 */
	public TbAsset edit(TbAsset t);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int del(Long id);
	/**
	 * 查询列表
	 * @param t
	 * @return
	 */
	public List<TbAsset>  search(TbAsset t);
	/**
	 * 根据id查询设备分类信息
	 * @param goodsId
	 * @return
	 */
	public List<Map<String, Object>> asseSearch(String goodsId);



	public Map<String, Object> getByParam(Map<String, String> param);
	

}
