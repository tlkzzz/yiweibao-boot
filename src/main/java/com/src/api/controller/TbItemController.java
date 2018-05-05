package com.src.api.controller;

import com.src.api.entity.TbCompany;
import com.src.api.entity.TbItem;
import com.src.api.service.TbCompanyService;
import com.src.api.service.TbItemService;
import com.src.api.service.TbServiceOrderService;
import com.src.common.shiro.config.JWTUtil;
import com.src.common.shiro.config.ResponseRestful;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping(value="/item")
public class TbItemController {
    private static final Logger logger = LogManager.getLogger(TbCompanyController.class);
    @Resource
    TbCompanyService tbCompanyService;
    @Resource
    TbItemService tbItemService;
    @Resource
    TbServiceOrderService tbServiceOrderService;



    @ResponseBody
    @RequestMapping(value="/getItem")
    public ResponseRestful getItem(HttpServletRequest request, HttpSession session) {
        try {
        System.out.println("page:"+request.getParameter("page"));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("page", request.getParameter("pageNum"));
        params.put("pageSize", request.getParameter("rows"));
        params.put("tiName", request.getParameter("keywords"));
        params.put("tcId", request.getParameter("tcId"));//商品类型
        params.put("orderBy", request.getParameter("sort"));
        params.put("orderType", request.getParameter("order"));
        params.put("itemStartDate", request.getParameter("itemStartDate"));
        params.put("itemEndDate", request.getParameter("itemEndDate"));
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String TOken = httpServletRequest.getHeader("Authorization");
            System.out.println(TOken);
            String name= JWTUtil.getLoginName(TOken);
            TbCompany tbCompany=  tbCompanyService.findByPhone(name);
        params.put("companyId", tbCompany.getTcId().toString());
        Map<String, Object> json = tbItemService.findForJson(params);
            return new ResponseRestful(200,"查询成功",json);

        } catch (Exception e) {
            logger.error("[]出错，错误原因："+e.getMessage());
            e.printStackTrace();
            return new ResponseRestful(100,"查询失败",null);
        }
    }

    @ResponseBody
    @RequestMapping(value="/delItem")
    public ResponseRestful delItem(HttpServletRequest request) {
        String id= request.getParameter("ids");//客户id，逗号拼接
        if (id == null) {
            return  new ResponseRestful(100,"分类不存在",null);//帐号不存在
        }
        try {
            String[] arrTiId = id.split(",");
            for (int i = 0; i < arrTiId.length; i++) {
                if (!StringUtils.isBlank(arrTiId[i])){
                    TbItem tempBean = tbItemService.findById(Long.parseLong(arrTiId[i]));
                    tempBean.setTiStatus(0);
                    tempBean.setTiId(Long.parseLong(arrTiId[i]));

                    if(tbServiceOrderService.canDelete(tempBean))
                        tbItemService.update(tempBean);
                    else
                        return  new ResponseRestful(100,"删除失败",null);//帐号不存在
                }
            }
            return new ResponseRestful(200,"删除成功",null);//帐号不存在
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return  new ResponseRestful(100,"删除失败",null);//帐号不存在
        }
    }
}
