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

	@Resource
	TbProductCategoryService tbProductCategoryService;

	@Resource
	TbSpecService tbSpecService;

	@Resource
	TbServiceFeesService tbServiceFeesService;

	@Resource
	TbSpecValueDao tbSpecValueDao;

	@Resource
	ProductSpecService tbProductSpecService;

	@Resource
	ProductSkuService tbProductSkuService;

	@Resource
	TbCategoryService tbCategoryService;

	SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
	
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

	/**
	 * 删除商品
	 * @param request
	 * @param session
	 * @return
	 */
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

	/**
	 * 商品状态更改
	 * @param request
	 * @param session
	 * @return
	 */
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

	/**
	 * 新增商品
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/addProduct")
	@ResponseBody
	@Transactional
	public ResponseRestful addProduct(HttpServletRequest request,HttpSession session) {
		logger.error("进入商品新增");
		try {
			//参数
			String tpCategoryId =request.getParameter("tpCategoryId");//商品类型
			String tpBrandId = request.getParameter("tpBrandId");//商品品牌
			String tpPcId =  request.getParameter("tpPcId");//商品分类
			String tpName = request.getParameter("tpName");//商品名称
			String tpNumber = request.getParameter("tpNumber");//商品编号
			String tpPrice = request.getParameter("tpPrice");//商品价格
			String tpDesp = request.getParameter("tpDesp");//商品描述
			String tpFreightType = request.getParameter("tpFreightType");//运费类型
			String tpFreight = request.getParameter("tpFreight");//自拟运费
			String tpStatus = request.getParameter("tpStatus");//上下架
			String tpType = request.getParameter("tpType");//商品/配件
			String pics = request.getParameter("pics");//商品图片    多个  逗号  隔开
			String spec = request.getParameter("spec");//商品规格   拼接方式： 规格值id,价格,库存 |规格值id,价格,库存...
			String tpWarranty = request.getParameter("tpWarranty");//报修期
			String tpFeesIds = request.getParameter("tpFeesIds");//延保套餐  多个 逗号隔开
			String tpLogo = request.getParameter("tpLogo");//logo
			String baoxiu = request.getParameter("baoxiu");//是否保修
			if(!StringUtils.isBlank(pics))
				pics = pics.substring(0,pics.lastIndexOf(","));//去掉最后一个逗号
			if(!StringUtil.isEmptyNull(tpFeesIds)){//套餐是可以不选的
				tpFeesIds = tpFeesIds.substring(0, tpFeesIds.lastIndexOf(","));//去掉最后一个逗号
			}
			/**
			 * 商品基本信息
			 */
			TbProduct tbProduct = new TbProduct();
			tbProduct.setTpAddtime(new Timestamp(new Date().getTime()));
//			tbProduct.setTpBrandId(Long.valueOf(tpBrandId));
			//2018-01-08 品牌不是必传
			if(!StringUtils.isEmpty(tpBrandId)){
				tbProduct.setTpBrandId(Long.valueOf(tpBrandId));
			}else{
				tbProduct.setTpBrandId(0L);
			}
			tbProduct.setTpCategoryId(Long.valueOf(tpCategoryId));
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			String TOken = httpServletRequest.getHeader("Authorization");
			String name= JWTUtil.getLoginName(TOken);
			TbCompany tbCompany=  tbCompanyService.findByPhone(name);
			//登录单位信息
			tbProduct.setTpCompanyId(tbCompany.getTcId());
			tbProduct.setTpDesp(tpDesp);

			tbProduct.setTpFreightType(Integer.valueOf(tpFreightType));

			if(Integer.valueOf(tpFreightType)==1){//1自拟运费 2运费模板
				tbProduct.setTpFreight(Double.valueOf(tpFreight));
			}else{
				tbProduct.setTpFreight(0.00);
			}
			tbProduct.setTpLogo(tpLogo);
			tbProduct.setTpName(tpName);
//			if(StringUtil.isEmptyNull(tpNumber)){//如果没有输入编号  则生成
//				tbProduct.setTpNumber("HJ"+sdf.format(new Date())+(int)(Math.random()*100));
//			}else{
//				tbProduct.setTpNumber(tpNumber);
//			}
			if(StringUtil.isEmptyNull(tpNumber)){
				tpNumber = "HJ"+sdf.format(new Date())+(int)(Math.random()*100);
				//验证商品货号是否已存在（自动生成）
				//根据商品货号查询商品（布尔版）
				while(!tbProductService.findProductByNumberBoolean(tpNumber,null)){
					LogTool.WriteLog("新增商品自动生成编号tbNumber:"+tpNumber);
					tpNumber = "HJ"+sdf.format(new Date())+(int)(Math.random()*100);
				}
				tbProduct.setTpNumber(tpNumber);
			}else{
				//验证商品货号是否已存在（手动输入）
				//根据商品货号查询商品
				TbProduct productNumber = tbProductService.findProductByNumber(tpNumber,null);
				if(productNumber != null){
					return new ResponseRestful(100,"商品货号已存在",null);//商品货号已存在
				}
				tbProduct.setTpNumber(tpNumber);
			}
//			tbProduct.setTpPcId(Long.valueOf(tpPcId));
			//2018-01-08 商品分类不传默认存0
			if(!StringUtils.isEmpty(tpPcId)){
				tbProduct.setTpPcId(Long.valueOf(tpPcId));
			}else{
				tbProduct.setTpPcId(Long.valueOf(0));
			}
			tbProduct.setTpPics(pics);
//			tbProduct.setTpPrice(Double.valueOf(tpPrice));
			//2018-01-08 价格为空默认存0
			if(!StringUtils.isEmpty(tpPrice)){
				tbProduct.setTpPrice(Double.valueOf(tpPrice));
			}else{
				tbProduct.setTpPrice(Double.valueOf(0.00));
			}
			tbProduct.setTpSalesCount(0);
			tbProduct.setTpStatus(Integer.valueOf(tpStatus));
			tbProduct.setTpSimpleName(tpName);
			tbProduct.setTpType(Integer.valueOf(tpType));
			LogTool.WriteLog("是否报修："+baoxiu);
			if(Integer.valueOf(baoxiu)==1){//选择保修
				tbProduct.setTpWarranty(Integer.valueOf(tpWarranty));
				tbProduct.setTpFeesIds(tpFeesIds);
			}
			tbProduct.setTpWeight(0);

			Integer tpStore = 0;//商品所有规格总库存

			String[] _spec = spec.split("\\|");
			String[] __spec = null;
			TbProductSku tbProductSku =null;
			TbProductSpec tbProductSpec = null;
			TbSpecValue tbSpecValue = null;
			List<TbProductSku> tbProductSkuList = new ArrayList<TbProductSku>();
			List<List<TbProductSpec>> tbProductSpecListList =new ArrayList<List<TbProductSpec>>();
			List<TbProductSpec> tbProductSpecList = null;
			for (int i = _spec.length - 1; i >= 0; i--) {
				__spec = _spec[i].split(",");

				//商品一组规格价格与库存
				tbProductSku = new TbProductSku();
				tbProductSku.setTpsPrice(Double.valueOf(__spec[__spec.length-2]));
				tbProductSku.setTpsStore(Integer.valueOf(__spec[__spec.length-1]));
				tbProductSkuList.add(tbProductSku);

				tpStore+=Integer.valueOf(__spec[__spec.length-1]);//计算总库存
				//商品一组规格详情
				tbProductSpecList = new ArrayList<TbProductSpec>();
				if (__spec.length-2 == 0){
					tbProductSpec = new TbProductSpec();
					tbProductSpec.setTpsPic("");
					tbProductSpec.setTpsProductId(tbProduct.getTpId());
					tbProductSpec.setTpsSkuId(tbProductSku.getTpsId());
					tbProductSpec.setTpsType(1);
					tbProductSpec.setTpsSpecId(0L);
					tbProductSpec.setTpsSpecValue("无");
					tbProductSpecList.add(tbProductSpec);
					tbProductSpecListList.add(tbProductSpecList);
				}else{
					for (int j = 0; j < __spec.length-2; j++) {
						tbProductSpec = new TbProductSpec();
						tbProductSpec.setTpsPic("");
						tbProductSpec.setTpsType(1);
						tbSpecValue = tbSpecValueDao.get(Long.valueOf(__spec[j]));
						tbProductSpec.setTpsSpecId(tbSpecValue.getTsvSpecId());
						tbProductSpec.setTpsSpecValue(tbSpecValue.getTsvValue());
						tbProductSpecList.add(tbProductSpec);
					}
					tbProductSpecListList.add(tbProductSpecList);
				}
			}

			tbProduct.setTpStore(tpStore);

			tbProductService.addProductAndSpec(tbProduct,tbProductSkuList,tbProductSpecListList);


			return new ResponseRestful(200,"新增成功",null);
		} catch (Exception e) {
			logger.error("[product/add]出错，错误原因："+e.getMessage());
			e.printStackTrace();
			return new ResponseRestful(100,"新增失败",null);
		}
	}

	/**
	 * 编辑商品
	 * @param request
	 * @param session
	 * @param tbBrand
	 * @return
	 */
	@RequestMapping(value="/editProduct")
	@ResponseBody
	public ResponseRestful editProduct(HttpServletRequest request,HttpSession session,TbBrand tbBrand) {

		logger.error("进入商品编辑");
		try {
			//参数
			String tpId = request.getParameter("tpId");
			String tpCategoryId =request.getParameter("tpCategoryId");//商品类型
			String tpBrandId = request.getParameter("tpBrandId");//商品品牌
			String tpPcId =  request.getParameter("tpPcId");//商品分类
			String tpName = request.getParameter("tpName");//商品名称
			String tpNumber = request.getParameter("tpNumber");//商品编号
			String tpPrice = request.getParameter("tpPrice");//商品价格
			String tpDesp = request.getParameter("tpDesp");//商品描述
			String tpFreightType = request.getParameter("tpFreightType");//运费类型
			String tpFreight = request.getParameter("tpFreight");//自拟运费
			String tpStatus = request.getParameter("tpStatus");//上下架
			String tpType = request.getParameter("tpType");
			String pics = request.getParameter("pics");//商品图片    多个  逗号  隔开
			String spec = request.getParameter("spec");//商品规格   拼接方式： skuId,规格值id,价格,库存 |skuId,规格值id,价格,库存...
			String tpWarranty = request.getParameter("tpWarranty");//报修期
			String tpFeesIds = request.getParameter("tpFeesIds");//延保套餐  多个 逗号隔开
			String tpLogo = request.getParameter("tpLogo");//logo
			String baoxiu = request.getParameter("baoxiu");//是否保修
			if(!StringUtils.isBlank(pics))
				pics = pics.substring(0,pics.lastIndexOf(","));//去掉最后一个逗号
			if(!StringUtil.isEmptyNull(tpFeesIds)){//售后套餐可以不选
				tpFeesIds = tpFeesIds.substring(0, tpFeesIds.lastIndexOf(","));//去掉最后一个逗号
			}
			/**
			 * 商品基本信息
			 */
			TbProduct tbProduct = tbProductService.findById(Long.valueOf(tpId));
			//tbProduct.setTpAddtime(new Timestamp(new Date().getTime()));
//			tbProduct.setTpBrandId(Long.valueOf(tpBrandId));
			//2018-01-08 品牌不是必传
			if(!StringUtils.isEmpty(tpBrandId)){
				tbProduct.setTpBrandId(Long.valueOf(tpBrandId));
			}else{
				tbProduct.setTpBrandId(0L);
			}
			tbProduct.setTpCategoryId(Long.valueOf(tpCategoryId));
//			TbCompany tbCompany= (TbCompany)session.getAttribute(
//					GlobalStatic.DEFAULT_LOGIN_SESSION_NAME);//登录单位信息
//			tbProduct.setTpCompanyId(tbCompany.getTcId());
			tbProduct.setTpDesp(tpDesp);

			tbProduct.setTpFreightType(Integer.valueOf(tpFreightType));

			if(Integer.valueOf(tpFreightType)==1){//1自拟运费 2运费模板
				tbProduct.setTpFreight(Double.valueOf(tpFreight));
			}else{
				tbProduct.setTpFreight(0.00);
			}
			tbProduct.setTpLogo(tpLogo);
			tbProduct.setTpName(tpName);
			if(StringUtil.isEmptyNull(tpNumber)){//如果没有输入编号  则生成
				tpNumber = "HJ"+sdf.format(new Date())+(int)(Math.random()*100);
				//验证商品货号是否已存在（自动生成）
				//根据商品货号查询商品（布尔版）
				while(!tbProductService.findProductByNumberBoolean(tpNumber,tpId)){
					LogTool.WriteLog("商品编号tbNumber:"+tpNumber);
					tpNumber = "HJ"+sdf.format(new Date())+(int)(Math.random()*100);
				}
				tbProduct.setTpNumber(tpNumber);
			}else{
				//验证商品货号是否已存在（手动输入）
				//根据商品货号查询商品
				TbProduct productNumber = tbProductService.findProductByNumber(tpNumber,tpId);
				if(productNumber != null){
					return new ResponseRestful(100,"商品货号已存在",null);//商品货号已存在
				}
				tbProduct.setTpNumber(tpNumber);
			}
//			tbProduct.setTpPcId(Long.valueOf(tpPcId));
			//2018-01-08 商品分类不传默认存0
			if(!StringUtils.isEmpty(tpPcId)){
				tbProduct.setTpPcId(Long.valueOf(tpPcId));
			}else{
				tbProduct.setTpPcId(0L);
			}
			tbProduct.setTpPics(pics);
//			tbProduct.setTpPrice(Double.valueOf(tpPrice));
			//2018-01-08 价格为空默认存0
			if(!StringUtils.isEmpty(tpPrice)){
				tbProduct.setTpPrice(Double.valueOf(tpPrice));
			}else{
				tbProduct.setTpPrice(Double.valueOf(0.00));
			}
			//tbProduct.setTpSalesCount(0);
			tbProduct.setTpStatus(Integer.valueOf(tpStatus));
			tbProduct.setTpType(Integer.valueOf(tpType));
			tbProduct.setTpSimpleName(tpName);
			//tbProduct.setTpType(1);
			LogTool.WriteLog("是否保修：baoxiu："+baoxiu);
			if(Integer.valueOf(baoxiu)==1){//选择保修
				tbProduct.setTpWarranty(Integer.valueOf(tpWarranty));
				tbProduct.setTpFeesIds(tpFeesIds);
			}
			if(Integer.valueOf(baoxiu)==0){//选择不保修
				tbProduct.setTpWarranty(null);
				tbProduct.setTpFeesIds("");
			}
			//tbProduct.setTpWeight(0);

			Integer tpStore = 0;//商品所有规格总库存

			LogTool.WriteLog("spec:"+spec);
			String[] _spec = spec.split("\\|");

			//根据商品id获取规格组
			List<TbProductSku> tbProductList = tbProductSkuService.findCountByProduct(tbProduct.getTpId());

			//以下操作   把删除的规格组取出来
			List<Long> lList = new ArrayList<Long>();
			for (TbProductSku tbProductSku : tbProductList) {
				boolean falg = true;
				for(int i = 0; i < _spec.length; i++){
					String [] specss = _spec[i].split(",");
					if(!Integer.valueOf(specss[0]).equals(0)){
						if(tbProductSku.getTpsId().equals(Long.valueOf(specss[0]))){
							falg = false;
						}
					}
				}
				if(falg){
					lList.add(tbProductSku.getTpsId());
				}
			}

			String[] __spec = null;
			TbProductSku tbProductSku =null;
			TbProductSpec tbProductSpec = null;
			TbSpecValue tbSpecValue = null;
			List<TbProductSku> tbProductSkuList = new ArrayList<TbProductSku>();
			List<List<TbProductSpec>> tbProductSpecListList =new ArrayList<List<TbProductSpec>>();
			List<TbProductSpec> tbProductSpecList = null;
			for (int i = 0; i < _spec.length; i++) {
				__spec = _spec[i].split(",");

				//商品一组规格价格与库存
				tbProductSku = new TbProductSku();
				if(!__spec[0].equals("0")){
					tbProductSku.setTpsId(Long.valueOf(__spec[0]));
				}
				tbProductSku.setTpsPrice(Double.valueOf(__spec[__spec.length-2]));
				tbProductSku.setTpsStore(Integer.valueOf(__spec[__spec.length-1]));
				tbProductSku.setTpsProductId(Long.valueOf(tpId));
				tbProductSkuList.add(tbProductSku);

				tpStore+=Integer.valueOf(__spec[__spec.length-1]);//计算总库存
				//商品一组规格详情
				tbProductSpecList = new ArrayList<TbProductSpec>();
				if (__spec.length-2 == 0){
					tbProductSpec = new TbProductSpec();
					tbProductSpec.setTpsPic("");
					tbProductSpec.setTpsProductId(tbProduct.getTpId());
					tbProductSpec.setTpsSkuId(tbProductSku.getTpsId());
					tbProductSpec.setTpsType(1);
					tbProductSpec.setTpsSpecId(0L);
					tbProductSpec.setTpsSpecValue("无");//默认
					tbProductSpecList.add(tbProductSpec);
					tbProductSpecListList.add(tbProductSpecList);
				}else{
					for (int j = 1; j < __spec.length-2; j++) {
						tbProductSpec = new TbProductSpec();
						if(!__spec[0].equals("0")){
							tbProductSpec.setTpsSkuId(Long.valueOf(__spec[0]));
						}
						tbProductSpec.setTpsProductId(Long.valueOf(tpId));
						tbProductSpec.setTpsPic("");
						tbProductSpec.setTpsType(1);
						tbSpecValue = tbSpecValueDao.get(Long.valueOf(__spec[j]));
						tbProductSpec.setTpsSpecId(tbSpecValue.getTsvSpecId());
						tbProductSpec.setTpsSpecValue(tbSpecValue.getTsvValue());
						tbProductSpecList.add(tbProductSpec);
					}
				}
				tbProductSpecListList.add(tbProductSpecList);
			}
			tbProduct.setTpStore(tpStore);
			tbProductService.updateProductAndSpec(tbProduct,lList,tbProductSkuList,tbProductSpecListList);
			return new ResponseRestful(200,"修改成功",null);
		} catch (Exception e) {
			logger.error("[product/edit]出错，错误原因："+e.getMessage());
			e.printStackTrace();
			return new ResponseRestful(100,"修改失败",null);
		}
	}

	/**
	 *根据规格id获取某一规格的商品价格
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryProductBySkuId")
	public ResponseRestful queryProductBySkuId(HttpServletRequest request) {
		String skuId = request.getParameter("skuId");
		TbProductSku tbProductSku=tbProductSkuService.findById(Long.parseLong(skuId));
		return new ResponseRestful(200,"查询成功",tbProductSku);
	}

	/**
	 * 根据上级查商品分类ajax
	 * @param request
	 * @param session
	 * @param tpcId
	 * @return
	 */
	@RequestMapping(value="/productCategoryList.ajax")
	@ResponseBody
	public  ResponseRestful productCategoryList(HttpServletRequest request,HttpSession session,
														@RequestParam(value = "tpcId", required = true ) Long tpcId){
		logger.error("根据上级查商品分类ajax");
		logger.error("tpcId:"+tpcId);
		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			String TOken = httpServletRequest.getHeader("Authorization");
			String name= JWTUtil.getLoginName(TOken);
			TbCompany tbCompany=  tbCompanyService.findByPhone(name);
			//登录单位信息
			List<TbProductCategory> tbProductCategoryList = tbProductCategoryService.getByparentId(tpcId,tbCompany.getTcId());
			return new ResponseRestful(200,"查询成功 ",tbProductCategoryList);
		} catch (Exception e) {
			logger.error("[product/productCategoryList.ajax]出错，错误原因："+e.getMessage());
			e.printStackTrace();
			return new ResponseRestful(100,"查询失败 ",null);
		}
	}

	/**
	 * 类型规格ajax
	 * @param request
	 * @param session
	 * @param tcId
	 * @param tpId
	 * @return
	 */
	@RequestMapping(value="/specList.ajax")
	@ResponseBody
	public  ResponseRestful specList(HttpServletRequest request,HttpSession session,
											   @RequestParam(value = "tcId", required = true ) Long tcId,
											   @RequestParam(value = "tpId",required=false) Long tpId) {
		logger.error("类型规格ajax");
		logger.error("tcId:"+tcId);
		try {
			List<Map<String, Object>> tbSpecList = tbSpecService.getByCategoryId(tcId);//规格
			if(tbSpecList.size()>0){
				for (Map<String, Object> map : tbSpecList) {
					List<TbSpecValue> tbSpecValueList = tbSpecService.findValueBytsId(map.get("tsId"));
					map.put("specValue", tbSpecValueList);//规格值
					//商品选中的规格值
					List<Map<String, Object>> tbProductSpecValueList = tbProductSpecService.getBytpId(tpId,map.get("tsId"));
					map.put("productSpecValue", tbProductSpecValueList);
					//商品规格详情（sku 价格 库存）
					List<Map<String, Object>> tbProductSkuList = tbProductSpecService.getSkuBytpId(tpId);
					map.put("productSku", tbProductSkuList);
				}
			}else{
				if (tpId != null){
					Map<String, Object> map = new HashMap<>();
					//商品规格详情（sku 价格 库存）
					List<Map<String, Object>> tbProductSkuList = tbProductSpecService.getSkuBytpId(tpId);
					map.put("productSku", tbProductSkuList);
					tbSpecList.add(map);
				}
			}
			return new ResponseRestful(200,"查询成功 ",tbSpecList);
		} catch (Exception e) {
			logger.error("[product/specList.ajax]出错，错误原因："+e.getMessage());
			e.printStackTrace();
			return new ResponseRestful(100,"查询失败 ",null);
		}
	}

	/**
	 * 延保套餐列表ajax
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/feesList.ajax")
	@ResponseBody
	public  ResponseRestful feesList(HttpServletRequest request,HttpSession session){
		logger.error("延保套餐  列表ajax");
		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			String TOken = httpServletRequest.getHeader("Authorization");
			System.out.println(TOken);
			String name= JWTUtil.getLoginName(TOken);
			TbCompany tbCompany=  tbCompanyService.findByPhone(name);//单位

			List<Map<String, Object>> list=tbServiceFeesService.findByCompany(tbCompany.getTcId());
			return new ResponseRestful(200,"查询成功 ",list);
		} catch (Exception e) {
			logger.error("[product/feesList.ajax]出错，错误原因："+e.getMessage());
			e.printStackTrace();
			return new ResponseRestful(100,"查询失败 ",null);
		}
	}
}
