package com.src.api.controller;


import com.src.api.entity.TbBrand;
import com.src.api.entity.TbCompany;
import com.src.api.service.TbBrandService;
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
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="/brand")
public class TbBrandController {

    private static final Logger logger = LogManager.getLogger(TbCompanyController.class);
    @Resource
    TbCompanyService tbCompanyService;
    @Resource
    TbBrandService tbBrandService;



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

    @RequestMapping(value="/delBrand")
    @ResponseBody
    public  ResponseRestful delBrand(HttpServletRequest request
                      ){
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






}
