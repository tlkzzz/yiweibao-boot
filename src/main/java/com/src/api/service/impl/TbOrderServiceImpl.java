package com.src.api.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.src.common.base.entity.PageBean;
import com.src.common.utils.StringUtil;
import com.src.common.utils.hjtech.util.LogTool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.ProductSkuDao;
import com.src.api.dao.TbCompanyDao;
import com.src.api.dao.TbLogisticsDao;
import com.src.api.dao.TbMemberAddressDao;
import com.src.api.dao.TbOrderDao;
import com.src.api.dao.TbOrderDetailDao;
import com.src.api.dao.TbOrderInsuranceDao;
import com.src.api.dao.TbPayRecordDao;
import com.src.api.entity.TbCompany;
import com.src.api.entity.TbCustomers;
import com.src.api.entity.TbLogistics;
import com.src.api.entity.TbOrderDetail;
import com.src.api.entity.TbOrderHead;
import com.src.api.entity.TbOrderInsurance;
import com.src.api.entity.TbPayRecord;
import com.src.api.entity.TbProductSku;
import com.src.api.service.TbCustomersService;
import com.src.api.service.TbOrderService;


@Service
public class TbOrderServiceImpl extends BaseServiceImpl<TbOrderHead, Long>
implements TbOrderService{

	@Autowired
	TbOrderDao tbOrderDao;
	@Autowired
	ProductSkuDao productSkuDao;
	@Resource
	TbMemberAddressDao tbMemberAddressDao;
	@Resource
	TbCompanyDao tbCompanyDao;
	@Resource
	TbLogisticsDao tbLogisticsDao;
	@Resource
	TbCustomersService tbCustomersService;
	@Resource
	TbPayRecordDao tbPayRecordDao;
	@Autowired
	TbOrderDetailDao tbOrderDetailDao;
	@Autowired
	TbOrderInsuranceDao tbInsuranceDao;
	
	@Override
	public BaseDao<TbOrderHead, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbOrderDao;
	}

	@Override
	public HashMap<String, Object> findForJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		String order = params.get("order");
		String tohNumber = params.get("tohNumber");
		String keywords = params.get("keywords");
		String tmName = params.get("tmName");
		String tohStatus = params.get("tohStatus");
		String AddDate = params.get("AddDate");
		String AddDateEnd = params.get("AddDateEnd");
		String companyId = params.get("companyId");
		LogTool.WriteLog("tohStatus:"+tohStatus);
		LogTool.WriteLog("keywords:"+keywords);
		LogTool.WriteLog("companyId:"+companyId);
		LogTool.WriteLog("AddDate:"+AddDate);
		LogTool.WriteLog("AddDateEnd:"+AddDateEnd);
		String sql = "SELECT toh_id tohId,toh_number tohNumber,"
				+ "c.tc_person tmName,c.tc_mobile tcMobile,"
				+ "DATE_FORMAT(toh_add_date,'%Y-%m-%d %H:%i:%s') tohAddDate,toh_amount tohAmount,"
//				+ "toh_status tohStatus,IF(od.tod_end_time>now(),0,1) ifOutTime,"
				+ "toh_status tohStatus,"
//				+ "p.tp_name,p.tp_logo,p.tp_pics,"
				+ "toh_count tohCount,"
//				+ "(SELECT GROUP_CONCAT(ps.tps_spec_value SEPARATOR ' ') "/*多个商品的情况就会查询到重复订单*/
//				+ "FROM tb_product_spec ps"
//				+ " WHERE ps.tps_sku_id=od.tod_sku_id) as tgModel,"
				+ "(SELECT pname FROM china_province WHERE pid = c.tc_prov_id) pname,"
				+ "(SELECT cname FROM china_city WHERE cid = c.tc_city_id) cname,"
				+ "(SELECT oname FROM china_county WHERE oid = c.tc_region_id) oname, "
				+ "c.tc_address tohAddress,(SELECT tw_name FROM tb_words WHERE tw_id = c.tc_type) tcType "
//				+ "FROM tb_order_head,tb_order_detail od,tb_product p,tb_customers c,tb_address dr "
				+ "FROM tb_order_head,tb_customers c "
				+ "where toh_status <> 0 "
//				+ "AND od.tod_head_id = toh_id "
//				+ "AND od.tod_product_id = p.tp_id "
				+ "AND c.tc_id = toh_customer_id ";
//				+ "AND od.tod_type = 1 "
		if (!StringUtils.isBlank(companyId)) {
			sql += " and toh_company_id = "+companyId;//单位id
		}
		
		if (!StringUtils.isBlank(keywords)) {
			sql += " and toh_number like '%"+keywords+"%'";//工单号
		}
//		if(!StringUtils.isBlank(keywords)){
//			//根据工单号与联系人模糊查询
//			sql += " and (toh_number like '%"+keywords+"%' OR c.tc_person like '%"+keywords+"%')";
//		}
		if (!StringUtils.isBlank(tohStatus)) {
			//0:删除 1:订单生成 2:已支付 3:已发货 4:已收货 5:已评价 6:申请退款中 7:已退款 8:交易取消 9:交易关闭 10:确认出库
			if(!StringUtil.isNumber(tohStatus)){
				sql += " and toh_status in ("+tohStatus+")";
			}else{
				sql += " and toh_status = "+tohStatus;
			}
		}
		
		if (!StringUtils.isBlank(tmName)) {//联系人
			sql += " and toh_customer_id in (SELECT tc_id FROM tb_customers  WHERE tc_person like '%"+tmName+"%')";
		}
		if (AddDate != null && !AddDate.equalsIgnoreCase("")) {
			sql += " AND DATE_FORMAT(toh_add_date,'%Y-%m-%d') >= DATE_FORMAT('"+AddDate+"','%Y-%m-%d')";
		} 
		
		if (AddDateEnd!= null && !AddDateEnd.equalsIgnoreCase("")) {
			sql += " AND DATE_FORMAT(toh_add_date,'%Y-%m-%d') <= DATE_FORMAT('"+AddDateEnd+"','%Y-%m-%d')";
		}
		//按工单添加时间排序
		if (!StringUtils.isBlank(order)) {
			sql += " order by toh_add_date " + order;
		} else {
			sql += " order by toh_add_date desc ";
		}
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbOrderDao.searchForMap(sql, null);
			for (Map<String, Object> map : list) {
				List<Object> values = new ArrayList<>();
				//获取订单下商品信息
				String detailSql = "SELECT tp_id AS tpId,tp_name AS tpName ,tp_pics as tpPic,tp_logo AS tpLogo,"
						+ "tod_price AS todPrice,tp_number tpNumber,"
						+ "tod_count AS todCount,tod_spec_json AS todSpecJson,"
						+ "(SELECT GROUP_CONCAT(ps.tps_spec_value SEPARATOR ' ') "
						+ "FROM tb_product_spec ps WHERE ps.tps_sku_id=tod_sku_id) as value,"
						+ "tod_spec_json "
						+ " FROM tb_product,tb_order_detail WHERE tp_id = tod_product_id "
						+ " and tod_head_id = ? AND tod_type = 1";
				values.add(map.get("tohId"));
				List<Map<String, Object>> products = tbOrderDao.searchForMap(detailSql, values);
				map.put("product", products);
				map.put("productSize", products.size());
				map.put("tohAmount", String.format("%.2f", map.get("tohAmount")));
				for (Map<String, Object> m : products) {
					String specJsonValue = "";
					//tod_spec_json中截取value的值空格拼接([{"tsName":"硬盘","value":"500G"},{"tsName":"cpu","value":"双核"}])
					String todSpecJson = m.get("tod_spec_json").toString();
					LogTool.WriteLog("todSpecJson:"+todSpecJson);
					//todSpecJson不为空的时候进行以下操作
					if(!StringUtils.isBlank(todSpecJson)){
						//示例：    []
						todSpecJson = todSpecJson.replaceAll(" ", "");//去空格
						todSpecJson = todSpecJson.replaceAll("\\[", "");
						todSpecJson = todSpecJson.replaceAll("\\]", "");//{tsName=内存, value=8GB}, {tsName=内存, value=8GB}
						todSpecJson = todSpecJson.replaceAll("\\{", "");
						todSpecJson = todSpecJson.replaceAll("\\}", "");
						todSpecJson = todSpecJson.replaceAll("\"", "");//去掉双引号
						//tsName=内存, value=8GB, tsName=内存, value=8GB
						String[] arrTodSpecJson = todSpecJson.split(",");
						for (int i = 0; i < arrTodSpecJson.length; i++) {
							//如果是数组第奇数个元素则获取值
							if(i%2==1){
								String[] arrSpec = arrTodSpecJson[i].split("=");//value=8GB
								//如果字符串用逗号分割后数组长度没有变为2则用:分割
								if(arrSpec.length!=2){
									arrSpec = arrTodSpecJson[i].split(":");
								}
								if(specJsonValue.equals("")){
									specJsonValue = arrSpec[1];//获取第二个元素
								}else{
									specJsonValue = specJsonValue + " " +arrSpec[1];//获取第二个元素用空格隔开
								}
							}
						}
					}
					m.put("specJsonValue", specJsonValue);//返回商品规格值
				}
			}
			json.put("pageCount", StringUtil.getPageCount(list.size(),pageSize));
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbOrderDao.searchForMap(sql, null, pageBean);
			for (Map<String, Object> map : pageBean.getList()) {
				List<Object> values = new ArrayList<>();
				//获取订单下商品信息
				String detailSql = "SELECT tp_id AS tpId,tp_name AS tpName ,tp_pics as tpPic,tp_logo AS tpLogo,"
						+ "tod_price AS todPrice,tp_number tpNumber,"
						+ "tod_count AS todCount,tod_spec_json AS todSpecJson,"
						+ "(SELECT GROUP_CONCAT(ps.tps_spec_value SEPARATOR ' ') "
						+ "FROM tb_product_spec ps WHERE ps.tps_sku_id=tod_sku_id) as value,"
						+ "tod_spec_json "
						+ " FROM tb_product,tb_order_detail WHERE tp_id = tod_product_id "
						+ " and tod_head_id = ? AND tod_type = 1";
				values.add(map.get("tohId"));
				List<Map<String, Object>> products = tbOrderDao.searchForMap(detailSql, values);
				map.put("product", products);
				map.put("productSize", products.size());
				map.put("tohAmount", String.format("%.2f", map.get("tohAmount")));
				for (Map<String, Object> m : products) {
					String specJsonValue = "";
					//tod_spec_json中截取value的值空格拼接([{"tsName":"硬盘","value":"500G"},{"tsName":"cpu","value":"双核"}])
					String todSpecJson = m.get("tod_spec_json").toString();
					LogTool.WriteLog("todSpecJson:"+todSpecJson);
					//todSpecJson不为空的时候进行以下操作
					if(!StringUtils.isBlank(todSpecJson)){
						//示例：    []
						todSpecJson = todSpecJson.replaceAll(" ", "");//去空格
						todSpecJson = todSpecJson.replaceAll("\\[", "");
						todSpecJson = todSpecJson.replaceAll("\\]", "");//{tsName=内存, value=8GB}, {tsName=内存, value=8GB}
						todSpecJson = todSpecJson.replaceAll("\\{", "");
						todSpecJson = todSpecJson.replaceAll("\\}", "");
						todSpecJson = todSpecJson.replaceAll("\"", "");//去掉双引号
						//tsName=内存, value=8GB, tsName=内存, value=8GB
						String[] arrTodSpecJson = todSpecJson.split(",");
						for (int i = 0; i < arrTodSpecJson.length; i++) {
							//如果是数组第奇数个元素则获取值
							if(i%2==1){
								String[] arrSpec = arrTodSpecJson[i].split("=");//value=8GB
								//如果字符串用逗号分割后数组长度没有变为2则用:分割
								if(arrSpec.length!=2){
									arrSpec = arrTodSpecJson[i].split(":");
								}
								if(specJsonValue.equals("")){
									specJsonValue = arrSpec[1];//获取第二个元素
								}else{
									specJsonValue = specJsonValue + " " +arrSpec[1];//获取第二个元素用空格隔开
								}
							}
						}
					}
					m.put("specJsonValue", specJsonValue);//返回商品规格值
				}
			}
			json.put("pageCount", StringUtil.getPageCount(pageBean.getRowCount(),pageSize));
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}
	
	
	
	@Override
	public HashMap<String, Object> list_order_product(HashMap<String, String> params){
		HashMap<String, Object> json = new HashMap<String, Object>();
		
		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		
		
		
		String strOrderNumber = params.get("ordernumber")==null?"":params.get("ordernumber"); //订单编号
		String strDeviceNum=params.get("device_num")==null?"":params.get("device_num");
		String strCustomerNum = params.get("memo_num")==null?"":params.get("memo_num");
		String strCustomerId =  params.get("customer_id")==null?"":params.get("customer_id");
		String strProductName = params.get("productname")==null?"":params.get("productname");
		
		String strOrderId = params.get("orderid")==null?"":params.get("orderid"); //订单id
	
		
		StringBuffer sbSql = new StringBuffer();
		
		sbSql.append(" select tod_id, b.tc_name,tp_name,a.tc_name customer_name,toh_number,tod_product_id,tod_product_code,tod_customer_code,tod_price,DATE_FORMAT(tod_add_date,'%Y-%m-%d %H:%i:%s') tod_add_date  ");
		
		sbSql.append(" from tb_order_detail,tb_order_head,tb_customers a,tb_product,tb_category b ");
		sbSql.append(" where tod_head_id = toh_id  and toh_customer_id = a.tc_id and tp_id = tod_product_id  and  tp_category_id= b.tc_id ");
	//	sbSql.append(" and toh_status > 4"); //至少是已收货
		sbSql.append(" and tod_type = 1 "); //至少是已收货
		
		
		if(!strCustomerId.equals(""))
			sbSql.append(" and  toh_customer_id =" +strCustomerId ); //至少是已收货
		
		if(!strCustomerNum.equals(""))
			sbSql.append(" and  tod_customer_code LIKE '%" +strCustomerNum +"%' " ); //至少是已收货
		
		
		if(!strDeviceNum.equals(""))
			sbSql.append(" and  tod_product_code LIKE '%" +strDeviceNum +"%' " ); //至少是已收货
		
		if(!strOrderNumber.equals(""))
			sbSql.append(" and  toh_number ='" +strOrderNumber +"' " ); //至少是已收货
		
		if(!strOrderId.equals(""))
			sbSql.append(" and  toh_id ='" +strOrderId +"' " ); //至少是已收货
			
		
		if(!strProductName.equals(""))
			sbSql.append(" and  tp_name like '%" +strProductName +"%' " ); //至少是已收货
			
		
		
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbOrderDao.searchForMap(sbSql.toString(), null);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbOrderDao.searchForMap(sbSql.toString(), null, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public HashMap<String, Object> findForJson1(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		String  tohId = params.get("tohId");
		String sql = "SELECT tp_id tpId, tp_logo tpLogo,tp_number tpNumber,tp_name tpName,tp_price tpPrice,"
				+ "tod_count todCount,tod_sku_id todSkuId FROM tb_order_detail,tb_product WHERE "
				+ "tod_product_id = tp_id AND tod_head_id = "+tohId;
		List<Map<String, Object>> list = tbOrderDao.searchForMap(sql, null);
		
		for (Map<String, Object> map2 : list) {
			String todSkuId = String.valueOf(map2.get("todSkuId"));
			String sql1 ="SELECT tps_spec_value specValue FROM tb_product_spec WHERE tps_sku_id = "+todSkuId;
			List<Map<String, Object>> tbOrderSpecList = tbOrderDao.searchForMap(sql1, null);
			String specValue = "";
			int in = 1;
			for (Map<String, Object> map3 : tbOrderSpecList) {
				specValue += map3.get("specValue");
				if(in<tbOrderSpecList.size()){
					specValue += ";";
				}
			}
			map2.put("spec", specValue);
			TbProductSku tbProductSku = productSkuDao.get(Long.valueOf(todSkuId));
			map2.put("tpPrice", tbProductSku.getTpsPrice());
		}
		
		json.put("rows", list);
		return json;
	}
//	@Override
//	public List<Map<String, Object>> findForJson1(HashMap<String, String> params) {
//		String  tohId = params.get("tohId");
//		String sql = "SELECT tp_id tpId, tp_logo tpLogo,tp_number tpNumber,tp_name tpName,tp_price tpPrice,"
//				+ "tod_count todCount FROM tb_order_detail,tb_product WHERE "
//				+ "tod_product_id = tp_id AND tod_head_id = "+tohId;
//		return tbOrderDao.searchForMap(sql, null);
//	}

	@Override
	public List<Map<String, Object>> getById(Long id) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT toh_id tohId, toh_number tohNumber,");
		sbSql.append("DATE_FORMAT(toh_add_date,'%Y/%m/%d %H:%i:%s') tohAddDate,");
		sbSql.append("toh_status tohStatus,toh_amount tohAmount,(toh_amount-toh_freight) price,");
		sbSql.append("tho_memo tohMemo,tma_name,tma_phone,(SELECT pname FROM china_province WHERE pid = tma_provice_id) prov,");
		sbSql.append("(SELECT cname FROM china_city WHERE cid = tma_city_id) city,");
		sbSql.append("(SELECT oname FROM china_county WHERE oid = tma_county_id) county,tma_address,");
		sbSql.append("(SELECT tc_person FROM tb_customers WHERE tc_id = toh_customer_id) tcName ");
		sbSql.append("FROM tb_order_head LEFT JOIN tb_address ON toh_address_id = tma_id ");
		sbSql.append("WHERE toh_id ="+id);//根据订单id
		return tbOrderDao.searchForMap(sbSql.toString(), null);
	}

	@Override
	public Map<String, Object> findDetail(String toId) {
		Map<String, Object> result = new HashMap<>();
		TbOrderHead orderHead = tbOrderDao.get(Long.parseLong(toId));
		if (orderHead == null || orderHead.getTohStatus() == 0) {
			result.put("code", 102);
			result.put("message", "订单不存在");
			return result;
		}
		if (orderHead.getTohCompanyId() == 0) {
			result.put("tcName", "易维保");
		}else{
			TbCompany tbCompany = tbCompanyDao.get(orderHead.getTohCompanyId());
			if (tbCompany!=null) {
				result.put("tcName", tbCompany.getTcName());
			}
		}
		//获取会员信息
		TbCustomers customer = tbCustomersService.findById(orderHead.getTohCustomerId());
		if(customer==null){
			result.put("code", "104");
			result.put("message", "会员不存在");
			return result;
		}
		result.put("customer", customer);
		//返回订单状态
		//1:订单生成 2:已支付 3:已发货 4:已收货 5:已评价 6:申请退款中 7:已退款 8:交易取消 9:交易关闭 10:确认出库
		String status = "";
		if(orderHead.getTohStatus()==1){
			status = "待付款";
		}else if(orderHead.getTohStatus() == 2){
			status = "待发货";
		}else if(orderHead.getTohStatus() == 3){
			status = "待收货";
		}else if(orderHead.getTohStatus() == 4){
			status = "已完成";
		}else if(orderHead.getTohStatus() == 5){
			status = "已完成";
		}else if(orderHead.getTohStatus() == 6){
			status = "申请退款";
		}else if(orderHead.getTohStatus() == 7){
			status = "已退款";
		}else if(orderHead.getTohStatus() == 8){
			status = "已取消";
		}else if(orderHead.getTohStatus() == 9){
			status = "已关闭";
		}else if(orderHead.getTohStatus() == 10){
			status = "确认出库";
		}else {
			status = "";
		}
		result.put("status", status);
//		if (orderHead.getTohStatus() == 3 || orderHead.getTohStatus() == 4
//				|| orderHead.getTohStatus() == 5) {
//		}
		TbLogistics logistics = new TbLogistics();
		logistics.setTlOrderId(orderHead.getTohId());
		List<TbLogistics> list = tbLogisticsDao.search(logistics);
		if (list!=null && list.size()>0) {
			result.put("logistics", list.get(0));
		}
		
		List<Object> values = new ArrayList<>();
//		String addressSql = "SELECT * FROM `tb_address` a,`china_province` p,`china_city` c,`china_county` o WHERE p.`pid` = a.`tma_provice_id` AND c.`cid`= a.`tma_city_id` AND o.`oid` = a.`tma_county_id` AND a.`tma_id` = ?;";
//		values.add(orderHead.getTohAddressId());
		//2018-01-12 地址从tb_customers中获取
		String addressSql = "SELECT *,a.tc_address tma_address FROM `tb_customers` a,`china_province` p,`china_city` c,`china_county` o " +
				"WHERE p.`pid` = a.`tc_prov_id` AND c.`cid`= a.`tc_city_id` " +
				"AND o.`oid` = a.`tc_region_id` AND a.`tc_id` = ?;";
		values.add(orderHead.getTohCustomerId());
		List<Map<String, Object>> address = tbMemberAddressDao.searchForMap(addressSql, values);
		if (address!=null && address.size()>0) {
			result.put("address", address.get(0));
		}
		
		String detailSql = "SELECT tp_id AS tpId,tp_name AS tpName ,tp_pics as tpPic,tp_logo AS tpLogo,"
				+ "tod_price AS todPrice,tp_number tpNumber,"
				+ "tod_count AS todCount,tod_spec_json AS todSpecJson,"
				+ "(SELECT GROUP_CONCAT(ps.tps_spec_value SEPARATOR ' ') "
				+ "FROM tb_product_spec ps WHERE ps.tps_sku_id=tod_sku_id) as value,"
				+ "tod_spec_json "
				+ " FROM tb_product,tb_order_detail WHERE tp_id = tod_product_id "
				+ " and tod_head_id = ? AND tod_type = 1";//tod_type  1:商品订单 2:报修配件 3:普通报修订单
		values.clear();
		values.add(toId);
		List<Map<String, Object>> products = tbOrderDao.searchForMap(detailSql, values);
		for (Map<String, Object> m : products) {
			String specJsonValue = "";
			//tod_spec_json中截取value的值空格拼接([{"tsName":"硬盘","value":"500G"},{"tsName":"cpu","value":"双核"}])
			String todSpecJson = m.get("tod_spec_json").toString();
			LogTool.WriteLog("todSpecJson:"+todSpecJson);
			//todSpecJson不为空的时候进行以下操作
			if(!StringUtils.isBlank(todSpecJson)){
				//示例：    []
				todSpecJson = todSpecJson.replaceAll(" ", "");//去空格
				todSpecJson = todSpecJson.replaceAll("\\[", "");
				todSpecJson = todSpecJson.replaceAll("\\]", "");//{tsName=内存, value=8GB}, {tsName=内存, value=8GB}
				todSpecJson = todSpecJson.replaceAll("\\{", "");
				todSpecJson = todSpecJson.replaceAll("\\}", "");
				todSpecJson = todSpecJson.replaceAll("\"", "");//去掉双引号
				//tsName=内存, value=8GB, tsName=内存, value=8GB
				String[] arrTodSpecJson = todSpecJson.split(",");
				for (int i = 0; i < arrTodSpecJson.length; i++) {
					//如果是数组第奇数个元素则获取值
					if(i%2==1){
						String[] arrSpec = arrTodSpecJson[i].split("=");//value=8GB
						//如果字符串用逗号分割后数组长度没有变为2则用:分割
						if(arrSpec.length!=2){
							arrSpec = arrTodSpecJson[i].split(":");
						}
						if(specJsonValue.equals("")){
							specJsonValue = arrSpec[1];//获取第二个元素
						}else{
							specJsonValue = specJsonValue + " " +arrSpec[1];//获取第二个元素用空格隔开
						}
					}
				}
			}
			m.put("specJsonValue", specJsonValue);//返回商品规格值
		}
		//订单支付类型与支付金额
//		String payRecordSql = "SELECT * FROM tb_pay_record WHERE tp_id = tod_product_id "
//				+ " and tpr_target = ? AND tpr_type = 1 AND tpr_status = 2";
//		values.clear();
//		values.add(toId);
//		List<TbPayRecord> payRecordList = tbPayRecordDao.search(payRecordSql, values);
//		result.put("payWay", "");
//		if(payRecordList.size()>0){
//			TbPayRecord payRecord = payRecordList.get(0);
//			if(payRecord.getTprWay()==1){//1:支付宝 2:微信 3:银联
//				result.put("payWay", "支付宝");
//			}
//			result.put("payRecord", );
//		}else{
//			result.put("payRecord", new HashMap<String, Object>());
//		}
		result.put("products", products);
		result.put("productsSize", products.size());
		result.put("detail", orderHead);
		result.put("code", 100);
		result.put("message", "查询成功");
		return result;
	}
	
	@Transactional
	@Override
	public void addOrder(TbOrderHead tbOrderHead,List<TbOrderDetail> tbOrderDetailList) throws Exception{
		
		tbOrderHead=tbOrderDao.save(tbOrderHead);
		for (TbOrderDetail tbOrderDetail : tbOrderDetailList) {
			tbOrderDetail.setTodHeadId(tbOrderHead.getTohId());
			tbOrderDetailDao.save(tbOrderDetail);
			
			//根据产品id添加一条延保记录
			TbOrderInsurance tbOrderInsurance = new TbOrderInsurance();
			tbOrderInsurance.setToiOrderId(tbOrderHead.getTohId());
			tbOrderInsurance.setToiDetailId(tbOrderDetail.getTodId());
			tbOrderInsurance.setToiCuetomerId(tbOrderHead.getTohCustomerId());
			tbOrderInsurance.setToiCompanyId(Long.parseLong("0"));  //默认第一条记录
			tbOrderInsurance.setToiProductId(tbOrderDetail.getTodProductId());
			tbOrderInsurance.setToiFeesId(Long.parseLong("0"));  //0代表首次购买时的 质保
			tbOrderInsurance.setToiAddtime(new Timestamp(new Date().getTime()));
			tbOrderInsurance.setToiOperDate(new Timestamp(new Date().getTime()));
			tbOrderInsurance.setToiMonths(0); //产品的需要月数
			tbOrderInsurance.setToiStartDate(tbOrderDetail.getTodAddDate());
			tbOrderInsurance.setToiEndDate(tbOrderDetail.getTodEndTime());
			tbOrderInsurance.setToiStatus(2);  //已完成支付
			tbOrderInsurance.setToiInsurancePeice(Double.parseDouble("0.00"));
			tbOrderInsurance.setToiNumber(tbOrderHead.getTohNumber()); //保留订单的编号
			tbInsuranceDao.save(tbOrderInsurance);
			
		}
			
	}
	
	
}
