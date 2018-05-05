package com.src.api.controller;


import com.src.api.entity.TbBrand;
import com.src.api.entity.TbCategory;
import com.src.api.entity.TbCompany;
import com.src.api.service.TbBrandService;
import com.src.api.service.TbCategoryService;
import com.src.api.service.TbCompanyService;
import com.src.common.shiro.config.JWTUtil;
import com.src.common.shiro.config.ResponseRestful;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/brand")
public class TbBrandController {

    private static final Logger logger = LogManager.getLogger(TbCompanyController.class);
    @Resource
    TbCompanyService tbCompanyService;
    @Resource
    TbBrandService tbBrandService;
    @Resource
    TbCategoryService tbCategoryService;


    /**
     * 商品品牌列表
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value="/getBrand")
    public ResponseRestful getBrand(HttpServletRequest request, HttpSession session) {
        try {
            logger.error("进入商品品牌列表");
            Map<String, String> param = new HashMap<String,String>();
            param.put("tbName", request.getParameter("keywords"));//品牌名称
            param.put("tbCategoryId", request.getParameter("tbCategoryId"));//品牌类型
            param.put("page", request.getParameter("pageNum"));
            param.put("pageSize", request.getParameter("pageSize"));
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String TOken = httpServletRequest.getHeader("Authorization");
            System.out.println(TOken);
            String name= JWTUtil.getLoginName(TOken);
            TbCompany tbCompany=  tbCompanyService.findByPhone(name);
            param.put("companyId", ""+tbCompany.getTcId());
            Map<String, Object> json = tbBrandService.getByParam(param);

            return new ResponseRestful(200,"查询成功",json);
        } catch (Exception e) {
            logger.error("[brand/brand]出错，错误原因："+e.getMessage());
            e.printStackTrace();
            return new ResponseRestful(100,"查询失败",null);
        }
    }

    /**
     * 删除品牌
     * @param request
     * @return
     */
    @RequestMapping(value="/delBrand")
    @ResponseBody
    public  ResponseRestful delBrand(HttpServletRequest request){
        logger.error("批量删除商品品牌");
        String id = request.getParameter("ids");
        logger.error("id:"+id);
        try {
            String [] _tbId = id.split(",");
            for (int i = 0; i < _tbId.length; i++) {//循环删除
                TbBrand tbBrandById = tbBrandService.findById(Long.valueOf(_tbId[i]));
                if(tbBrandById==null){
                    return  new ResponseRestful(100,"品牌不存在",null);//帐号不存在
                }
                tbBrandById.setTbStatus(0);//逻辑删除状态为0
                tbBrandService.update(tbBrandById);
            }

            return new ResponseRestful(200,"删除成功 ",null);
        } catch (Exception e) {
            logger.error("[brand/del]出错，错误原因："+e.getMessage());
            e.printStackTrace();
            return new ResponseRestful(100,"删除失败 ",null);
        }
    }

    /**
     * 商品品牌新增
     * @param request
     * @param session
     * @param tbBrand
     * @return
     */
    @RequestMapping(value="/addBrand")
    @ResponseBody
    public ResponseRestful add(HttpServletRequest request,HttpSession session,TbBrand tbBrand) {
        try {
            logger.error("进入品牌新增");
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String TOken = httpServletRequest.getHeader("Authorization");
            System.out.println(TOken);
            String name= JWTUtil.getLoginName(TOken);
            TbCompany tbCompany=  tbCompanyService.findByPhone(name);
            //当前登录单位
//			//判断同一单位 类型 品牌不能相同
//			TbBrand tbBrandOne = new TbBrand();
//			tbBrandOne.setTbAdduser(tbCompany.getTcId());
//			tbBrandOne.setTbCategoryId(tbBrand.getTbCategoryId());
//			tbBrandOne.setTbName(tbBrand.getTbName());
//			tbBrandOne = tbBrandService.findOne(tbBrandOne);
//			if(tbBrandOne!=null){
//				return "102";//同品牌已存在
//			}
            //查询是否有相同品牌名称在相同类型下除当前编辑tbId以外
            TbBrand brand = tbBrandService.findBrandByTcIdAndTbName(tbBrand.getTbCategoryId().toString(),tbBrand.getTbName(),null,tbCompany.getTcId().toString());
            if(brand != null){
                return new ResponseRestful(100,"品牌已存在 ",null);
            }

            tbBrand.setTbAddtime(new Timestamp(new Date().getTime()));
            tbBrand.setTbAdduser(tbCompany.getTcId());
            tbBrand.setTbStatus(1);
            tbBrandService.save(tbBrand);
            return new ResponseRestful(200,"新增成功 ",null);
        } catch (Exception e) {
            logger.error("[brand/add]出错，错误原因："+e.getMessage());
            e.printStackTrace();
            return new ResponseRestful(100,"新增失败 ",null);
        }
    }

    @RequestMapping(value="/editBrand")
    @ResponseBody
    public ResponseRestful edit(HttpServletRequest request,HttpSession session,TbBrand tbBrand) {
        logger.error("进入商品编辑");
        try {
            TbBrand tbBrandById = tbBrandService.findById(tbBrand.getTbId());
            if(tbBrandById==null){
                return new ResponseRestful(100,"品牌不存在 ",null);//品牌不存在
            }
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String TOken = httpServletRequest.getHeader("Authorization");
            System.out.println(TOken);
            String name= JWTUtil.getLoginName(TOken);
            TbCompany tbCompany=  tbCompanyService.findByPhone(name);//当前登录单位
            //如果改变类型 或  改变 品牌名称
            if(!tbBrandById.getTbCategoryId().equals(tbBrand.getTbCategoryId())||!tbBrandById.getTbName().equals(tbBrand.getTbName())){
//				TbBrand tbBrandOne = new TbBrand();
//				tbBrandOne.setTbAdduser(tbCompany.getTcId());
//				tbBrandOne.setTbCategoryId(tbBrand.getTbCategoryId());
//				tbBrandOne.setTbName(tbBrand.getTbName());
//				tbBrandOne = tbBrandService.findOne(tbBrandOne);
//				if(tbBrandOne!=null){
//					return "103";//同品牌已存在
//				}
                //查询是否有相同品牌名称在相同类型下除当前编辑tbId以外
                TbBrand brand = tbBrandService.findBrandByTcIdAndTbName(tbBrand.getTbCategoryId().toString(),tbBrand.getTbName(),tbBrand.getTbId().toString(),tbCompany.getTcId().toString());
                if(brand != null){
                    return new ResponseRestful(100,"修改失败 ",null);
                }
            }
            tbBrand.setTbAddtime(tbBrandById.getTbAddtime());
            tbBrand.setTbAdduser(tbBrandById.getTbAdduser());
            tbBrand.setTbStatus(tbBrandById.getTbStatus());
            tbBrandService.update(tbBrand);
            return new ResponseRestful(200,"修改成功 ",null);
        } catch (Exception e) {
            logger.error("[brand/edit]出错，错误原因："+e.getMessage());
            e.printStackTrace();
            return new ResponseRestful(100,"修改失败 ",null);
        }
    }

    /**
     * 商品类型列表ajax
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value="/categorylist.ajax")
    @ResponseBody
    public  ResponseRestful categorylist(HttpServletRequest request,HttpSession session){
        logger.error("商品类型列表ajax");
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String TOken = httpServletRequest.getHeader("Authorization");
            System.out.println(TOken);
            String name= JWTUtil.getLoginName(TOken);
            TbCompany tbCompany=  tbCompanyService.findByPhone(name);//登录的单位信息
            List<TbCategory> list =tbCategoryService.findList(tbCompany.getTcId());
            return new ResponseRestful(200,"查询成功 ",list);
        } catch (Exception e) {
            logger.error("[brand/categorylist.ajax]出错，错误原因："+e.getMessage());
            e.printStackTrace();
            return new ResponseRestful(100,"查询失败 ",null);
        }
    }

    /**
     * 商品品牌列表ajax
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value="/brandList.ajax")
    @ResponseBody
    public  ResponseRestful brandList(HttpServletRequest request,HttpSession session){
        logger.error("商品品牌列表ajax");
        String tcId=	request.getParameter("ids");
        try {
            List<TbBrand> list=tbBrandService.findList(Long.valueOf(tcId));
            return new ResponseRestful(200,"查询成功 ",list);
        } catch (Exception e) {
            logger.error("[brand/categorylist.ajax]出错，错误原因："+e.getMessage());
            e.printStackTrace();
            return new ResponseRestful(100,"查询失败 ",null);
        }
    }


}
