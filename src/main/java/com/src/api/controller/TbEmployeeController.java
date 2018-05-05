package com.src.api.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.src.api.entity.TbCompany;
import com.src.api.entity.TbServiceEmployee;
import com.src.api.service.TbCompanyService;
import com.src.api.service.TbServiceEmployeeService;
import com.src.common.shiro.config.JWTUtil;
import com.src.common.shiro.config.ResponseRestful;
import com.src.common.utils.StringUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value="/employee")
public class TbEmployeeController {
	private static final Logger LOGGER = LogManager.getLogger(TbEmployeeController.class);
	@Resource
	TbServiceEmployeeService tbServiceEmployeeService;
	@Resource
	TbCompanyService tbCompanyService;
	
	/**
	 * 员工列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/workList")
	public ResponseRestful workList(HttpServletRequest request) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("page", request.getParameter("pageNum"));
		params.put("pageSize", request.getParameter("pageSize"));
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String TOken = httpServletRequest.getHeader("Authorization");
		String name= JWTUtil.getLoginName(TOken);
		TbCompany tbCompany=  tbCompanyService.findByPhone(name);
		if(tbCompany==null){
			params.put("tcId", "0");
		}else{
			params.put("tcId", tbCompany.getTcId().toString());
		}
		params.put("tseName", request.getParameter("tseName"));
		params.put("tseMobile", request.getParameter("tseMobile"));
		params.put("ifLogin", request.getParameter("ifLogin"));
		HashMap<String, Object> json = new HashMap<String, Object>();
		json = tbServiceEmployeeService.findEmployee(params);


		return new ResponseRestful(200,"查询成功",json);
		
	}


	@RequestMapping(value = "/getEmployee",method = RequestMethod.GET)
	public ResponseRestful getEmployee(HttpServletRequest request,HttpSession session) {
		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			String TOken = httpServletRequest.getHeader("Authorization");
			String name= JWTUtil.getLoginName(TOken);
			TbCompany tbCompany=  tbCompanyService.findByPhone(name);

			Map<String, String>  param = new HashMap<String, String>();
			param.put("companyId", tbCompany.getTcId().toString());
			param.put("tseNumber", request.getParameter("tseNumber"));
			param.put("tseStatus", "1");
			param.put("tseUnitid", request.getParameter("tseUnitid"));
			param.put("page", request.getParameter("pageNum")); //页数
			param.put("pageSize", request.getParameter("pageSize"));//每页条数
			Map<String, Object> json = tbServiceEmployeeService.getByparam(param);
			//Map<String, Object> tbServiceEmployee = tbServiceEmployeeService.getByCompanyId(tbCompany.getTcId());
			return new ResponseRestful(200,"查询成功",json);
		} catch (Exception e) {
			LOGGER.error("[serviceEmployee/employee]出错，错误原因："+e.getMessage());
			e.printStackTrace();
			return new ResponseRestful(100,"查询失败",null);
		}
	}


	@RequestMapping(value="/delEmployee")
	@ResponseBody
	public  ResponseRestful delEmployee(HttpServletRequest request){
	String id=	request.getParameter("ids");
		try {
			TbServiceEmployee tbServiceEmployee = tbServiceEmployeeService.findById(Long.valueOf(id));
			if(tbServiceEmployee==null){
				return new ResponseRestful(100,"员工不存在",null);//
			}
			//逻辑删除
			tbServiceEmployee.setTseStatus(0);
			tbServiceEmployeeService.update(tbServiceEmployee);
			return new ResponseRestful(200,"删除成功",null);//
		} catch (Exception e) {
			LOGGER.error("[servicePoints/del]出错，错误原因："+e.getMessage());
			e.printStackTrace();
			return new ResponseRestful(100,"删除失败",null);
		}
	}
	@RequestMapping(value="/addEmployee",method = RequestMethod.POST)
	@ResponseBody
	public ResponseRestful addEmployee(HttpServletRequest request) {
		try {
			String tseUnitid = request.getParameter("tseUnitid");
			String tseName = request.getParameter("tseName");
			String tseNumber = request.getParameter("tseNumber");
			String tseMobile = request.getParameter("tseMobile");
			String sex = request.getParameter("sex");
			String tseLoginUser = request.getParameter("tseLoginUser");
			String password = request.getParameter("password");
			String tcLogo = request.getParameter("tcLogo");

			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			String TOken = httpServletRequest.getHeader("Authorization");
			String name= JWTUtil.getLoginName(TOken);
			TbCompany tbCompany=  tbCompanyService.findByPhone(name);
			//判断网点员工编码是否存在
			List<TbServiceEmployee> tbServiceEmployeeList = tbServiceEmployeeService.findByNumberAndCompany(tseNumber,tbCompany.getTcId());
			if(tbServiceEmployeeList!=null&&tbServiceEmployeeList.size()>0){
				return new ResponseRestful(100,"员工编码已存在",null);//
			}



			List<TbServiceEmployee> tbServiceEmployeeListByMobile = tbServiceEmployeeService.findByMobileAndCompany(tseMobile,tbCompany.getTcId());
			if(tbServiceEmployeeListByMobile!=null&&tbServiceEmployeeListByMobile.size()>0){
				return new ResponseRestful(100,"联系电话已存在",null);//联系电话已存在
			}
			TbServiceEmployee tbServiceEmployee=new TbServiceEmployee();

//			2018-01-24 前端界面所属部门去掉后，需要给个默认值 在数据字典中 tw_id=182
//			if(tbServiceEmployee.getTseDepartment() == null)
				tbServiceEmployee.setTseDepartment(182L);
//			2018-01-24 前端界面所属职位去掉后，需要给个默认值 在数据字典中 tw_id=188
			if (tbServiceEmployee.getTsePosition() == null)
				tbServiceEmployee.setTsePosition(188L);
			tbServiceEmployee.setTseAddDate(new Timestamp(new Date().getTime()));
			tbServiceEmployee.setTseAddPerson(tbCompany.getTcId());
			tbServiceEmployee.setTseStatus(1);
			tbServiceEmployee.setTseWorkType(0);
			tbServiceEmployee.setTseSex(Integer.valueOf(sex));
			tbServiceEmployee.setTsePhoto(tcLogo);
			tbServiceEmployee.setTseLoginPass(StringUtil.getMD5Str(password));
			tbServiceEmployee.setTseLoginUser(tseLoginUser);
			tbServiceEmployee.setTseUnitid(Long.valueOf(tseUnitid));
			tbServiceEmployee.setTseName(tseName);
			tbServiceEmployee.setTseNumber(tseNumber);
			tbServiceEmployee.setTseMobile(tseMobile);
			tbServiceEmployeeService.save(tbServiceEmployee);
			return new ResponseRestful(200,"新增成功",null);
		} catch (Exception e) {
			LOGGER.error("[servicePoints/add]出错，错误原因："+e.getMessage());
			e.printStackTrace();
			return new ResponseRestful(100,"新增失败",null);
		}
	}

}
