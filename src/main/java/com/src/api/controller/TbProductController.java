package com.src.api.controller;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import com.src.api.dao.TbSpecValueDao;
import com.src.api.entity.*;
import com.src.api.service.*;
import com.src.common.entity.ShiroUser;
import com.src.common.shiro.config.JWTUtil;
import com.src.common.shiro.config.ResponseRestful;
import com.src.common.utils.GlobalStatic;
import com.src.common.utils.StringUtil;
import com.src.common.utils.hjtech.util.LogTool;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value="/product")
public class TbProductController{
	private static final Logger logger = LogManager.getLogger(TbProductController.class);
	
	@Resource
	TbProductService tbProductService;
	

	@Resource
	TbCompanyService tbCompanyService;
	@Resource
	TbQrcodeService tbQrcodeServiceImpl;
	
	/**
	 * 商品列表
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getProduct",method = RequestMethod.GET)
	public ResponseRestful getProduct(HttpServletRequest request, HttpSession session) {
		logger.error("进入商品列表");
		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			String TOken = httpServletRequest.getHeader("Authorization");
			String name= JWTUtil.getLoginName(TOken);
			TbCompany tbCompany=  tbCompanyService.findByPhone(name);
			Map<String, String> param = new HashMap<String,String>();
			param.put("page", request.getParameter("pageNum")); //页数
			param.put("pageSize", request.getParameter("pageSize"));//每页条数
			param.put("tpName", request.getParameter("name"));
			param.put("tpStatus", request.getParameter("tpStatus"));

			param.put("companyId", tbCompany.getTcId().toString());
			param.put("tpcId", request.getParameter("tpcId"));
			Map<String, Object> json = tbProductService.getByParam(param);
 			//request.setAttribute("tbProduct", tbProductService.findByCompanyId(tbCompany.getTcId()));
//			request.setAttribute("tbProduct", tbProduct);

			return new ResponseRestful(200,"查询成功",json);
		} catch (Exception e) {
			logger.error("[product/product]出错，错误原因："+e.getMessage());
			e.printStackTrace();
			return new ResponseRestful(100,"查询失败",null);
		}
	}

	@RequestMapping(value="/delProduct")
	public  ResponseRestful delProduct(HttpServletRequest request,HttpSession session){
		logger.error("删除商品");
		String id=	request.getParameter("ids");
		try {
			TbProduct tbProduct = tbProductService.findById(Long.valueOf(id));
			if(tbProduct==null){
				return new ResponseRestful(100,"商品不存在",null);
			}

			tbProduct.setTpStatus(0);//逻辑删除


			//删除商品二维码
			TbQrcode qrcode = new TbQrcode();
			qrcode.setSourcetype("product");
			qrcode.setSourceid(Long.valueOf(id));
			qrcode.setStatus(1);
			List<TbQrcode> list =tbQrcodeServiceImpl.search(qrcode);
			if(list.size()==1){
				Long ids= list.get(0).getId();
				TbQrcode qr = tbQrcodeServiceImpl.findById(ids);
				qr.setStatus(0);
				tbQrcodeServiceImpl.update(qr);
			}
			tbProductService.update(tbProduct);
			return new ResponseRestful(200,"删除成功",null);
		} catch (Exception e) {
			logger.error("[product/del]出错，错误原因："+e.getMessage());
			e.printStackTrace();
			return new ResponseRestful(100,"删除失败",null);
		}
	}
	@RequestMapping(value="/downProduct")
	public  ResponseRestful downProduct(HttpServletRequest request,HttpSession session){
		logger.error("商品的状态改变");
		String id=	request.getParameter("ids");
		try {
			TbProduct tbProduct = tbProductService.findById(Long.valueOf(id));
			if(tbProduct==null){
				return new ResponseRestful(100,"商品不存在",null);
			}
			if(tbProduct.getTpStatus()==1){//如果是上架状态
				tbProduct.setTpStatus(2);//改为下架状态
			}else{//否则反之
				tbProduct.setTpStatus(1);
			}
			tbProductService.update(tbProduct);
			return new ResponseRestful(200,"操作成功",null);
		} catch (Exception e) {
			logger.error("[product/updateStatus]出错，错误原因："+e.getMessage());
			e.printStackTrace();
			return new ResponseRestful(100,"操作失败",null);
		}
	}
}
