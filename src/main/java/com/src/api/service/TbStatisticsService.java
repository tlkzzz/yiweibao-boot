/**    
* @{#} TbInformationCataService.java Create on 2015��8��17�� ����3:44:44    
* Copyright (c) 2015.    
*/
package com.src.api.service;

import java.util.HashMap;
import java.util.List;

import com.src.common.base.service.BaseService;
import com.src.api.entity.TbVersion;

/**
 * @author <a href="mailto:liwei.flyf@gmail.com">author</a>
 * @version 1.0
 * @description
 */
public interface TbStatisticsService  {
	
	int getAllOrders(String companyid); // 获取所有的报修工单数
	int getTodayOrder(String companyid);// 获取今日报修工单数
	int getTodayWaitAssign(String companyid);//获取今日待派工单数
	int getTodayComplete(String companyid); //获取今日已完成工单
	int getTodayNoComplete(String companyid);//获取今日未完成
	public HashMap<String, Object> list_order_baoxiu_detail(HashMap<String, String> params);
	public int getDayOrderCount(String day, String companyid); //获取某一天的报修工单数
	
	//获取报修产品排行榜
	public HashMap<String, Object> getRepairProductTop5(String companyid);
	
	//获取员工排行榜
	public HashMap<String, Object> statistics_employee_order(HashMap<String, String> params);
	
	
	//根据员工id获取相关条件下的评价平均分
	public String statistics_employee_evaluate(HashMap<String, String> params);
	
	
	//获取完成工单量的前10名
	public HashMap<String, Object> getRepairCompleteTop(HashMap<String, String> params);
	
	//获取评价的前10名
	public HashMap<String, Object> getRepairEvaluateTop(HashMap<String, String> params);

	
	//获取拒绝工单排行的前10名
	public HashMap<String, Object> getRepairRejectTop(HashMap<String, String> params);
}
