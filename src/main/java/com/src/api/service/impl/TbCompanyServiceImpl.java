package com.src.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.src.common.entity.Userinfo;
import com.src.common.service.UserinfoService;
import com.src.common.utils.GlobalStatic;
import com.src.common.utils.hjtech.util.LogTool;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbCompanyDao;
import com.src.api.entity.TbCompany;
import com.src.api.service.TbCompanyService;

@Service
public class TbCompanyServiceImpl extends BaseServiceImpl<TbCompany, Long> implements TbCompanyService{
	
	@Autowired
	TbCompanyDao tbCompanyDao;
	@Resource
	UserinfoService userinfoService;
	
	@Override
	public BaseDao<TbCompany, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbCompanyDao;
	}

	@Override
	public TbCompany findOne(TbCompany tbCompany) {
		// TODO Auto-generated method stub
		return tbCompanyDao.searchOne(tbCompany);
	}

	@Override
	public List<TbCompany> getAll() {
		String sql = "SELECT * FROM tb_company WHERE tc_status = 1";
		return tbCompanyDao.search(sql, null);
	}

	@Override
	public TbCompany findByPhone(String phone) {
		TbCompany tbCompany = null;
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT * FROM tb_company WHERE tc_login_user = ?");
		List<Object> values = new ArrayList<>();
		values.add(phone);
		List<TbCompany> list = tbCompanyDao.search(sbSql.toString(), values);
		if(list.size()>0){
			tbCompany = list.get(0);
		}
		return tbCompany;
	}

	@Override
	public void saveCompany(HttpSession session,TbCompany company, Userinfo companyUser)
			throws Exception {
		tbCompanyDao.save(company);
		companyUser.setCreateUserId(company.getTcId());//创建人就是自己的companyid
		userinfoService.save(companyUser);
		LogTool.WriteLog("pwd:"+company.getTcLoginPass());
		session.setAttribute(GlobalStatic.DEFAULT_LOGIN_SESSION_NAME,
				company);//保存登录用户信息 
		UsernamePasswordToken token = new UsernamePasswordToken(companyUser.getAccount(), companyUser.getPassword());
		Subject user = SecurityUtils.getSubject();
		user.login(token);
	}
	@Override
	public List<Map<String, Object>> getById(Long tcId) {
		StringBuffer sbString = new StringBuffer("select ");
		sbString.append("tc_id tcId,");
		sbString.append("tc_name tcName,");
		sbString.append("tc_logo tcLogo,");
		sbString.append("tc_code tcCode,");
		sbString.append("(SELECT pname from china_province WHERE pid = tc_prov_id) pName,");
		sbString.append("(SELECT cname from china_city WHERE cid = tc_city_id) cName,");
		sbString.append("(SELECT oname from china_county WHERE oid = tc_region_id) oName,");
		sbString.append("tc_contact_name tcContactName,");
		sbString.append("tc_contact_phone tcContactPhone ,");
		sbString.append("tc_sms_count,tc_amount ");
		sbString.append("from  tb_company ");
		sbString.append(" where tc_id = "+tcId);
		
		return tbCompanyDao.searchForMap(sbString.toString(), null);
	}

	
	@Override
	public void updatePass(TbCompany tbCompany, Userinfo companyUser)
			throws Exception {
		tbCompanyDao.update(tbCompany);
		userinfoService.update(companyUser);
		
	}
	@Override
	public Map<String, Object> findCompanyInfo(String tcId) {
		StringBuffer sbSql = new StringBuffer();
		Map<String, Object> companyinfoMap = new HashMap<String, Object>();
		sbSql.append("SELECT *,");
		sbSql.append("(SELECT pr.pname FROM china_province pr WHERE pr.pid=c.tc_prov_id) provName,");
		sbSql.append("(SELECT ci.cname FROM china_city ci WHERE ci.cid = c.tc_city_id) cityName,");
		sbSql.append("(SELECT co.oname FROM china_county co WHERE co.oid=c.tc_region_id) countyName ");
		sbSql.append("FROM `tb_company` c WHERE c.tc_id=?");//根据客户id查询客户信息
		List<Object> values = new ArrayList<>();
		values.add(tcId);
		List<Map<String, Object>> list = tbCompanyDao.searchForMap(sbSql.toString(), values);
		if(list.size()>0){
			companyinfoMap = list.get(0);
		}
		return companyinfoMap;
	}
	@Override
	public void updatePassANDInfo(TbCompany tbCompany, Userinfo companyUser)
			throws Exception {
		tbCompanyDao.update(tbCompany);
	//	userinfoService.update(companyUser);
		
	}

	@Override
	public TbCompany findByCode(String tcCode) {
		TbCompany tbCompany = null;
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT * FROM tb_company WHERE tc_code = ?");//根据邀请码查询
		List<Object> values = new ArrayList<>();
		values.add(tcCode);
		List<TbCompany> list = tbCompanyDao.search(sbSql.toString(), values);
		if(list.size()>0){
			tbCompany = list.get(0);
		}
		return tbCompany;
	}

	@Override
	public boolean checkCompanyByCode(String tcCode) {
		if(tcCode.equals("")){
			return false;
		}
		//判断字符串中是否有1与l,如果有重新生成tcCode
		if(tcCode.contains("1")||tcCode.contains("l")){
			return false;
		}
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT * FROM tb_company WHERE tc_code = ?");//根据邀请码查询
		List<Object> values = new ArrayList<>();
		values.add(tcCode);
		List<TbCompany> list = tbCompanyDao.search(sbSql.toString(), values);
		if(list.size()>0){
			return false;
		}
		return true;
	}

	@Override
	public TbCompany findByName(String tcName, String tcId) {
		TbCompany tbCompany = null;
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT * FROM tb_company WHERE tc_name = ? AND tc_status <> 0");
		List<Object> values = new ArrayList<>();
		values.add(tcName);
		if(!StringUtils.isBlank(tcId)){
			sbSql.append(" AND tc_id <> ?");
			values.add(tcId);
		}
		List<TbCompany> list = tbCompanyDao.search(sbSql.toString(), values);
		if(list.size()>0){
			tbCompany = list.get(0);
		}
		return tbCompany;
	}
}
