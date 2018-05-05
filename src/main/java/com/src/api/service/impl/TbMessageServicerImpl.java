package com.src.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.src.common.base.entity.PageBean;
import com.src.common.utils.GlobalStatic;
import com.src.common.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbMessageDao;
import com.src.api.entity.TbCompany;
import com.src.api.entity.TbMessage;
import com.src.api.service.TbMessageServicer;

@Service("tbMessageServicer")
public class TbMessageServicerImpl extends BaseServiceImpl<TbMessage, Long>
implements TbMessageServicer{

	@Autowired
	TbMessageDao tbMessageDao;
	@Resource
	TbMessageServicer tbMessageServicer;
	
	@Override
	public BaseDao<TbMessage, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbMessageDao;
	}
	
	@Override
	public TbMessage findTbMessage(TbMessage message) {
		return tbMessageDao.searchOne(message);
	}

	@Override
	public HashMap<String, Object> findForJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
//		String sort = params.get("sort");
		String order = params.get("order");
		String tmName = params.get("tmName");
		String AddDate = params.get("AddDate");
		String AddDateEnd = params.get("AddDateEnd");
		
		StringBuffer strSql = new StringBuffer();
		strSql.append("SELECT ");
		strSql.append("tm_id tmId ,tm_title tmTitle ,tm_content tmContent, ");
		strSql.append("DATE_FORMAT(tm_add_date,'%Y-%m-%d %H:%i:%s') tmAddDate, tm_sign tmSign ");
		strSql.append("FROM tb_message ");
		strSql.append("WHERE tm_status <>0 ");
		if (!StringUtils.isBlank(tmName)) {
			strSql.append("AND tm_title LIKE '%"+tmName+"%' ");
		}
		if (AddDate != null && !AddDate.equalsIgnoreCase("")) {
			strSql.append("AND DATE_FORMAT(tm_add_date,'%Y-%m-%d') >= DATE_FORMAT('"+AddDate+"','%Y-%m-%d') ");
		} 
		
		if (AddDateEnd!= null && !AddDateEnd.equalsIgnoreCase("")) {
			strSql.append("AND DATE_FORMAT(tm_add_date,'%Y-%m-%d') <= DATE_FORMAT('"+AddDateEnd+"','%Y-%m-%d') ");
		}
		
		strSql.append("GROUP BY tm_sign ");
		
		if (!StringUtils.isBlank(order)) {
			strSql.append("ORDER BY tm_add_date "+order+" ");
		} else {
			strSql.append("ORDER BY tm_add_date DESC ");
		}
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbMessageDao.searchForMap(strSql.toString(), null);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbMessageDao.searchForMap(strSql.toString(), null, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public List<Map<String, Object>> findCustomers(String account){
		String sql = "SELECT tc_id Id,tc_person uName, tc_login_user LoginUser,tc_mobile mobile "
				+ "FROM tb_customers WHERE tc_status = 1";
		if(!StringUtils.isBlank(account)){
			sql += " AND tc_login_user LIKE '%"+account+"%'";
		}
		return tbMessageDao.searchForMap(sql, null);
	}

	@Override
	public List<Map<String, Object>> findEmployee(String account) {
		String sql = "SELECT tse_id Id,tse_name uName,tse_login_user LoginUser ,tse_mobile mobile "
				+ "FROM tb_service_employee WHERE tse_status = 1";
		if(!StringUtils.isBlank(account)){
			sql += " AND tse_login_user LIKE '%"+account+"%'";
		}
		return tbMessageDao.searchForMap(sql, null);
	}

	@Override
	public HashMap<String, Object> findForJson1(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		String account = params.get("account");
		String type = params.get("type");
//		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
//		int pageSize = 0;
//		if(!StringUtils.isBlank(type)){
//			pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
//		}else{
//			pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"))/2;
//		}
		String sql = "SELECT tc_id Id,tc_person uName, tc_login_user LoginUser,tc_mobile mobile "
				+ "FROM tb_customers WHERE tc_status = 1 ";
		if (!StringUtils.isBlank(account)) {
			sql += " AND tc_login_user LIKE '%"+account+"%'";
		}
		int listSize = tbMessageDao.getCount(sql, null);
//		if(pageSize!=0){
//			sql += " limit "+(page-1)*pageSize+","+pageSize+"";
//		}
//		
		String sql1 = "SELECT tse_id Id,tse_name uName,tse_login_user LoginUser ,tse_mobile mobile "
				+ "FROM tb_service_employee WHERE tse_status = 1 ";
		if(!StringUtils.isBlank(account)){
			sql1 += " AND tse_login_user LIKE '%"+account+"%'";
		}
		int listSize1 = tbMessageDao.getCount(sql1, null);
//		if(pageSize!=0){
//			sql1 += " limit "+(page-1)*pageSize+","+pageSize+"";
//		}
		int rowCount = listSize+listSize1;
		List<Map<String, Object>> list = tbMessageDao.searchForMap(sql, null);
		for (Map<String, Object> map : list) {
			map.put("type", 1);
		}
		List<Map<String, Object>> list1 = tbMessageDao.searchForMap(sql1, null);
		for (Map<String, Object> map : list1) {
			map.put("type", 2);
		}
		if (!StringUtils.isBlank(type)) {
			if(Integer.valueOf(type)==1){
				rowCount = listSize;
				json.put("total", rowCount);
				json.put("rows", list);
				return json;
			}
			if(Integer.valueOf(type)==2){
				rowCount = listSize1;
				json.put("total", rowCount);
				json.put("rows", list1);
				return json;
			}
		}
		
		for (Map<String, Object> map : list1) {
			list.add(map);
		}
		
		json.put("total", rowCount);
		json.put("rows", list);
		return json;
	}

	@Override
	public List<TbMessage> findMessage(String tmSign) {
		StringBuffer strSql = new StringBuffer();
		strSql.append("SELECT * FROM tb_message ");
		strSql.append("WHERE tm_status =1 AND tm_sign= '"+tmSign+"' ");
		return tbMessageDao.search(strSql.toString(), null);
	}

	@Override
	public HashMap<String, Object> findForJson2(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		String tmSign = params.get("tmSign");
		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"))/2;
		StringBuffer strSql = new StringBuffer();
		strSql.append("SELECT ");
		strSql.append("tc_id Id, tc_person uName, tc_login_user LoginUser, tc_mobile mobile ");
		strSql.append("FROM tb_customers ");
		strSql.append("WHERE tc_status = 1 ");
		strSql.append("AND tc_id IN (SELECT tm_receive_id FROM tb_message WHERE tm_status =1 AND tm_type =1 AND tm_sign='"+tmSign+"') ");
		
		int listSize = tbMessageDao.getCount(strSql.toString(), null);
		if(pageSize!=0){
			strSql.append("LIMIT "+(page-1)*pageSize+","+pageSize+" ");
		}
		
		StringBuffer strSqlOne = new StringBuffer();
		strSqlOne.append("SELECT ");
		strSqlOne.append("tse_id Id, tse_name uName, tse_login_user LoginUser, tse_mobile mobile ");
		strSqlOne.append("FROM tb_service_employee ");
		strSqlOne.append("WHERE tse_status = 1 ");
		strSqlOne.append("AND tse_id IN (SELECT tm_receive_id FROM tb_message WHERE tm_status =1 AND tm_type =2 AND tm_sign='"+tmSign+"') ");
		int listSize1 = tbMessageDao.getCount(strSqlOne.toString(), null);
		if(pageSize!=0){
			strSqlOne.append("LIMIT "+(page-1)*pageSize+","+pageSize+" ");
		}
		int rowCount = listSize+listSize1;
		List<Map<String, Object>> list = tbMessageDao.searchForMap(strSql.toString(), null);
		for (Map<String, Object> map : list) {
			map.put("type", 1);
		}
		List<Map<String, Object>> list1 = tbMessageDao.searchForMap(strSqlOne.toString(), null);
		for (Map<String, Object> map : list1) {
			map.put("type", 2);
		}
		
		for (Map<String, Object> map : list1) {
			list.add(map);
		}
		
		json.put("total", rowCount);
		json.put("rows", list);
		return json;
	}

	@Override
	public void delBySign(String tmSign) {
		StringBuffer strSql = new StringBuffer();
		strSql.append("UPDATE tb_message SET tm_status = 0 WHERE tm_sign IN("+tmSign+") ");
		tbMessageDao.update(strSql.toString(), null);
	}

	@Override
	public TbMessage findOne(TbMessage message) {
		return tbMessageDao.searchOne(message);
	}
	

	@Override
	public HashMap<String, Object> findForJsonOfMessage(HashMap<String, String> params,HttpSession session) {
		HashMap<String, Object> json = new HashMap<String, Object>();

		int page = params.get("page") == null ? 0 : Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0 : Integer.parseInt(params.get("pageSize"));
		System.out.println("pageSize:"+pageSize);
		String title = params.get("title");//消息标题
		TbCompany tbCompany= (TbCompany)session.getAttribute(GlobalStatic.DEFAULT_LOGIN_SESSION_NAME);//当前登录人
		
		StringBuffer strSql = new StringBuffer();
		strSql.append("SELECT ");
		strSql.append("*, ");//查所有
		strSql.append("DATE_FORMAT(tm_add_date,'%Y-%m-%d %H:%i:%s') toAddtime, ");//添加时间
		strSql.append("(SELECT tc_name FROM tb_company WHERE tm_add_user=tc_id) tmAddUser ");//创建人
		strSql.append("FROM tb_message ");
		strSql.append("WHERE tm_add_user = "+tbCompany.getTcId()+" AND tm_status<>0 AND tm_sign !='' ");
		
		if (title != null && !title.equals("")) {
			strSql.append("AND tm_title like '%" + title + "%' ");
		}
		strSql.append("GROUP BY tm_sign ORDER BY tm_add_date DESC ");
		
		List<Object> values = new ArrayList<Object>();

		if (pageSize == 0) {
			List<TbMessage> list = tbMessageDao.search(strSql.toString(), values);
			json.put("pageCount", StringUtil.getPageCount(list.size(),pageSize));
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		} else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbMessageDao.searchForMap(strSql.toString(), null, pageBean);
			json.put("pageCount", StringUtil.getPageCount(pageBean.getRowCount(),pageSize));
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public HashMap<String, Object> findForJsonOfMessageDetail(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();

		int page = params.get("page") == null ? 0 : Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0 : Integer.parseInt(params.get("pageSize"));
		String tmSign = params.get("tmSign");//消息标识码
		//根据标识码查找
		TbMessage message = new TbMessage();
		message.setTmSign(tmSign);
		message = tbMessageServicer.findOne(message);
		int tmType = message.getTmType();//接收人类型
		
		StringBuffer strSql = new StringBuffer();
		if(tmType == 1){//客户
			strSql.append("SELECT ");
			strSql.append("(SELECT c.tc_name FROM tb_customers c WHERE c.tc_id = m.tm_receive_id) AS tName,");//姓名
			strSql.append("(SELECT c.tc_login_user FROM tb_customers c WHERE c.tc_id = m.tm_receive_id) AS tAccount,");//登录账号
			strSql.append("'客户' AS tType,");//会员类型
			strSql.append("(SELECT c.tc_mobile FROM tb_customers c WHERE c.tc_id = m.tm_receive_id) AS tPhone ");//手机号码
			strSql.append("FROM tb_message m ");
			strSql.append("WHERE m.tm_sign = '"+tmSign+"' ");
		}else if(tmType == 2){//员工
			strSql.append("SELECT ");
			strSql.append("(SELECT c.tse_name FROM tb_service_employee c WHERE c.tse_id = m.tm_receive_id) AS tName,");//姓名
			strSql.append("(SELECT c.tse_login_user FROM tb_service_employee c WHERE c.tse_id = m.tm_receive_id) AS tAccount,");//登录账号
			strSql.append("'员工' AS tType,");//会员类型
			strSql.append("(SELECT c.tse_mobile FROM tb_service_employee c WHERE c.tse_id = m.tm_receive_id) AS tPhone ");//手机号码
			strSql.append("FROM tb_message m ");
			strSql.append("WHERE m.tm_sign = '"+tmSign+"' ");
		}else if(tmType == 3){//网点	
			strSql.append("SELECT ");
			strSql.append("(SELECT c.tsp_name FROM tb_service_points c WHERE c.tsp_id = m.tm_receive_id) AS tName,");//姓名
			strSql.append("(SELECT c.tsp_login_user FROM tb_service_points c WHERE c.tsp_id = m.tm_receive_id) AS tAccount,");//登录账号
			strSql.append("'网点' AS tType,");//会员类型
			strSql.append("(SELECT c.tsp_charge_phone FROM tb_service_points c WHERE c.tsp_id = m.tm_receive_id) AS tPhone ");//手机号码
			strSql.append("FROM tb_message m ");
			strSql.append("WHERE m.tm_sign = '"+tmSign+"' ");
		}
		
		List<Object> values = new ArrayList<Object>();

		if (pageSize == 0) {
			List<TbMessage> list = tbMessageDao.search(strSql.toString(), values);
			json.put("pageCount", StringUtil.getPageCount(list.size(),pageSize));
			json.put("total", list.size());
			json.put("rows", list);
		} else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbMessageDao.searchForMap(strSql.toString(), null, pageBean);
			json.put("pageCount", StringUtil.getPageCount(pageBean.getRowCount(),pageSize));
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
		}
		return json;
		

	}

	@Override
	public List<Map<String,Object>> findPersonNameByType(String personType,String tcId) {
		
		StringBuffer strSql = new StringBuffer();
		if(personType.equals("1")){//如果接收人类型为1（客户）
			strSql.append("SELECT *,left(tc_name,7) tcName FROM tb_customers WHERE tc_status = 1 AND tc_company_id = "+tcId+"");
		}else if(personType.equals("2")){//如果接收人类型为2（员工）
			strSql.append("SELECT *,left(tse_name,7) tseName FROM tb_service_employee WHERE tse_unitid in (select tsp_id from tb_service_points where tsp_company_id = "+tcId+") AND tse_status = 1");	
		}else if(personType.equals("3")){//如果接收人类型为3（网点）
			strSql.append("SELECT *,left(tsp_name,7) tspName FROM tb_service_points WHERE tsp_status = 1 AND tsp_company_id = "+tcId+"");
		}
		
		return tbMessageDao.searchForMap(strSql.toString(), null);
	}

}
