package com.src.api.controller;

import com.src.api.entity.TbCompany;
import com.src.api.service.*;
import com.src.common.service.UserinfoService;
import com.src.common.shiro.config.JWTUtil;
import com.src.common.shiro.config.ResponseRestful;
import com.src.common.utils.hjtech.util.LogTool;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="/serviceOrder")
public class TbServiceOrderController {
    private static final Logger logger = LogManager.getLogger(TbCompanyController.class);

    @Resource
    TbServiceOrderService tbServiceOrderService;
    @Resource
    TbOrderDetailService tbOrderDetailService;
    @Resource
    TbCustomersService tbCustomersService;
    @Resource
    TbServicePointsService tbServicePointsService;
    @Resource
    TbServiceEmployeeService tbServiceEmployeeService;
    @Resource
    TbMessageServicer tbMessageServicer;
    @Resource
    UserinfoService userinfoService;
    @Resource
    TbCategoryService tbCategoryService;
    @Resource
    TbCompanyService tbCompanyService;




    /**
     * 报修工单（包括待处理）
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public ResponseRestful list(HttpServletRequest request) {
        LogTool.WriteLog("查询工单列表。。。。");

        try {

        HashMap<String, String> params = new HashMap<String, String>();

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String TOken = httpServletRequest.getHeader("Authorization");
        System.out.println(TOken);
        String name= JWTUtil.getLoginName(TOken);
        TbCompany tbCompany=  tbCompanyService.findByPhone(name);


        params.put("page", request.getParameter("page"));
        params.put("pageSize", request.getParameter("pageSize"));
        params.put("order", request.getParameter("order"));
        params.put("tsoNumber", request.getParameter("tsoNumber"));
        params.put("tmName", request.getParameter("tmName"));
        params.put("tsoStatus", request.getParameter("tsoStatus"));
        params.put("tsoType", request.getParameter("tsoType"));
        params.put("keywords", request.getParameter("keywords"));
        params.put("AddDate", request.getParameter("AddDate"));
        params.put("AddDateEnd", request.getParameter("AddDateEnd"));
        params.put("companyId", "" + tbCompany.getTcId());
            Map<String, Object> json =  tbServiceOrderService.findForJson(params);
            return new ResponseRestful(200,"查询成功",json);
        } catch (Exception e) {
            logger.error("[]出错，错误原因："+e.getMessage());
            e.printStackTrace();
            return new ResponseRestful(100,"查询失败",null);
        }
    }

}
