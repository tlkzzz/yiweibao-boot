package com.src.api.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbItem;
import com.src.api.entity.TbMessage;
import com.src.api.entity.TbServiceOrder;
import com.src.api.entity.TbServiceOrderDetail;

public interface TbServiceOrderService extends BaseService<TbServiceOrder, Long> {

	
	public TbServiceOrder searchOne(TbServiceOrder t);

	public HashMap<String, Object> findForJson(HashMap<String, String> params);
	
	public HashMap<String, Object> findForJsonEx(HashMap<String, String> params);
	
	List<TbServiceOrder> findServiceOrderByDate(String date) ;
	
	public Boolean canDelete(TbItem t);
	
	public HashMap<String, Object> GetWorkerJx(HashMap<String, String> params);
	
	public HashMap<String, Object> GetWorkerHistory(HashMap<String, String> params);

	public List<Map<String, Object>> findTbServiceOrderDetail(long tsoId, String type);

	void updateOrderDispatch(TbServiceOrder tbServiceOrder,
                             TbServiceOrderDetail tbServiceOrderDetail, TbMessage message)throws Exception;

	public HashMap<String, Object> findOrderEmpForJson(
            HashMap<String, String> params);

	public List<Map<String, Object>> getfitting(String tsoId, String type);

	public List<Map<String, Object>> getOrderSpec(String tsoId,
                                                  String tpId, String type, String todId);
}
