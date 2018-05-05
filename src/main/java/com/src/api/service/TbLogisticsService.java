package com.src.api.service;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbLogistics;
import com.src.api.entity.TbOrderHead;

public interface TbLogisticsService extends BaseService<TbLogistics, Long>{

	/**
	 *添加订单快递记录    更新订单状态为已发货
	 * @param tbLogistics
	 * @param tbOrder
	 */
	void upLogisticsAndOrder(TbLogistics tbLogistics, TbOrderHead tbOrder)throws Exception;

}
