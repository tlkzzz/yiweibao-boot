package com.src.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.src.api.dao.TbCustomersDao;
import com.src.api.entity.TbCustomers;
import com.src.common.base.dao.BaseDao;
import com.src.common.base.entity.PageBean;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.common.dao.UserinfoDao;
import com.src.common.entity.Userinfo;
import com.src.common.service.UserinfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("userinfoService")
@Transactional
public class UserinfoServiceImpl extends BaseServiceImpl<Userinfo, Long> implements
		UserinfoService {
	private Log log = LogFactory.getLog(UserinfoServiceImpl.class);
	@Resource
	UserinfoDao userinfoDao;
	@Resource
	TbCustomersDao tbCustomersDao;
	
	@Override
	public BaseDao<Userinfo, Long> getGenericDao() {
		return userinfoDao;
	}

	@Override
	public PageBean<Userinfo> findByPage(Userinfo params, PageBean<Userinfo> pageBean) {
		return userinfoDao.search(params, pageBean);
	}
	
	@Override
	public Userinfo findUserinfo(Userinfo searchParams) {
		return userinfoDao.searchOne(searchParams);
	}

	@Override
	public List<Userinfo> findAll(Userinfo searchParams) {
		return userinfoDao.search(searchParams);
	}
	
	@Override
	public HashMap<String, Object> findForJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();

		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		String sort = params.get("sort");
		String order = params.get("order");
		String group = params.get("group");
		String groupName = params.get("groupName");
		String account = params.get("account");
		
		String sql = "select u.id as id,u.name,u.groupId,u.account,u.sex,u.title,u.theme,u.email,u.remark,u.create_user_name as createUserName,u.create_time as createTime,g.name as groupName from userinfo u,usergroup g where u.groupId = g.id ";
		List<Object> values = new ArrayList<Object>();
		
		if(!StringUtils.isBlank(group)) {
			sql += " and u.groupId = ?";
			values.add(group);
		}
		
		if(!StringUtils.isBlank(groupName)) {
			sql += " and g.name like ?";
			values.add("%"+groupName+"%");
		}
		
		if(!StringUtils.isBlank(account)) {
			sql += " and u.account like ?";
			values.add("%"+account+"%");
		}
		if (pageSize == 0) {
			List<Userinfo> list = userinfoDao.search(sql, values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Userinfo> pageBean = new PageBean<Userinfo>(page, pageSize);
			pageBean = userinfoDao.search(sql, values, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}
	@Override
	public HashMap<String, Object> findForJson1(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		
		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		String sort = params.get("sort");
		String order = params.get("order");
		String group = params.get("group");
		
		String sql = "select u.id as id,u.name,u.groupId,u.account,u.sex,u.title,u.theme,u.email,u.remark,u.create_user_name as createUserName,u.create_time as createTime,g.name as groupName from userinfo u,usergroup g where u.groupId = g.id ";
		List<Object> values = new ArrayList<Object>();
		if(!StringUtils.isBlank(group)) {
			sql += " and u.groupId = ?";
			values.add(group);
		}
		if (pageSize == 0) {
			List<Userinfo> list = userinfoDao.search(sql, values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Userinfo> pageBean = new PageBean<Userinfo>(page, pageSize);
			pageBean = userinfoDao.search(sql, values, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public List<Userinfo> findUserGroup() {
		List<Userinfo> list = new ArrayList<Userinfo>();
		try {
			String sql = "select id as groupid , name as groupName from usergroup  ";
			list = userinfoDao.search(sql, null);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[UserinfoServiceImpl - findUserGroup()]：错误原因:"+ e.getMessage());
		}
		return list;
	}
	
	@Override
	public Userinfo findUserinfoById(Long id) {
		Userinfo userinfo = null;
		try {
			String sql = "select u.id as id,u.name,u.password,u.groupId,u.account,u.sex,u.title,u.theme,u.email,u.remark,u.create_user_id as createUserId,u.create_user_name as createUserName,u.create_time as createTime,u.groupId,g.name as groupName,g.id as groupid from userinfo u,usergroup g where u.groupId = g.id  and u.id = ? ";
			List<Userinfo> list = userinfoDao.searchp(sql, id);

			for (int i = 0; i < list.size(); i++) {
				userinfo = list.get(i);// 将list转换成Userinfo对象
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[UserinfoServiceImpl - findUserinfoById()]：错误原因:"
					+ e.getMessage());
		}
		return userinfo;
	}
	/**
	 * 
	* Title: findUserByName 
	* Description:  根据登录名称查询用户
	* @param account
	* @return 
	* @see com.spring.common.service.UserinfoService#findUserByName(String)
	 */
	@Override
	public Userinfo findUserByName(String account) {
		Userinfo userinfo = null;
		try {
			String sql = "SELECT * FROM userinfo u WHERE account = ? ";
			List<Userinfo> list = userinfoDao.searchp(sql, account);
			
			for (int i = 0; i < list.size(); i++) {
				userinfo = list.get(i);// 将list转换成Userinfo对象
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[UserinfoServiceImpl - findUserByName()]：错误原因:"
					+ e.getMessage());
		}
		return userinfo;
	}

	@Override
	public Userinfo findUserByNameANDGroupId(String userName,
			String company_groupId) {
		Userinfo user = null;
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT u.* FROM userinfo u WHERE u.account = ? AND u.groupId = ? ");
		List<Object> values = new ArrayList<>();
		values.add(userName);
		values.add(company_groupId);
		List<Userinfo> list = userinfoDao.search(sbSql.toString(), values);
		if(list.size()>0){
			user = list.get(0);
		}
		return user;
	}
	@Override
	public void updateMemberStatus(Userinfo search, TbCustomers tbcustomers)
			throws Exception {
		userinfoDao.update(search);
		tbCustomersDao.update(tbcustomers);
	}
}
