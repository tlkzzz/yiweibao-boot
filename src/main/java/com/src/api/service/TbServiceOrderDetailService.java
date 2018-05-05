package com.src.api.service;

import java.util.HashMap;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbServiceEmployee;
import com.src.api.entity.TbServiceOrderDetail;

public interface TbServiceOrderDetailService extends BaseService<TbServiceOrderDetail, Long> {

	public TbServiceOrderDetail searchOne(TbServiceOrderDetail t);

	public HashMap<String, Object> findForJson(HashMap<String, String> params);

	public void tsodOpt(TbServiceOrderDetail params);

	public TbServiceOrderDetail searchOne(String tsoId);

	public Boolean canDelete(TbServiceEmployee t);
}
