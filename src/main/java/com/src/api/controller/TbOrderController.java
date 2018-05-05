package com.src.api.controller;


import com.src.api.entity.TbCompany;
import com.src.api.entity.TbOrderHead;
import com.src.api.service.TbCompanyService;
import com.src.api.service.TbOrderService;
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
@RequestMapping(value="/order")
public class TbOrderController {
    private static final Logger logger = LogManager.getLogger(TbCompanyController.class);
    @Resource
    TbCompanyService tbCompanyService;

    @Resource
    TbOrderService tbOrderService;
    /**
     * 订单列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public ResponseRestful list(HttpServletRequest request) {
        LogTool.WriteLog("查询工单列表。。。。");

        try {

            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String TOken = httpServletRequest.getHeader("Authorization");
            System.out.println(TOken);
            String name= JWTUtil.getLoginName(TOken);
            TbCompany tbCompany=  tbCompanyService.findByPhone(name);


        HashMap<String, String> params = new HashMap<String, String>();
        params.put("page", request.getParameter("pageNum"));
        params.put("pageSize", request.getParameter("pageSize"));
        params.put("order", request.getParameter("order"));
        params.put("tohNumber", request.getParameter("tohNumber"));
        params.put("tmName", request.getParameter("tmName"));
        params.put("tohStatus", request.getParameter("tohStatus"));
        params.put("toType", request.getParameter("tsoType"));
        params.put("keywords", request.getParameter("keywords"));
        params.put("AddDate", request.getParameter("AddDate"));
        params.put("AddDateEnd", request.getParameter("AddDateEnd"));
        params.put("companyId", ""+tbCompany.getTcId());



            Map<String, Object> json =  tbOrderService.findForJson(params);
            return new ResponseRestful(200,"查询成功",json);


        } catch (Exception e) {
            logger.error("[]出错，错误原因："+e.getMessage());
            e.printStackTrace();
            return new ResponseRestful(100,"查询失败",null);
        }
    }


    @RequestMapping(value="/delete")
    public ResponseRestful delete(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        String id = request.getParameter("ids");
        logger.error("进入订单删除");


        TbOrderHead orderHead = tbOrderService.findById(Long.parseLong(id));
        if (orderHead == null || orderHead.getTohStatus() == 0) {

            return new ResponseRestful(100,"订单不存在",null);
        }

        if (orderHead.getTohStatus() != 8 && orderHead.getTohStatus() != 5
                && orderHead.getTohStatus() != 4) {//暂没有状态5的订单，所以4就是已完成
            result.put("code", "103");
            result.put("message", "订单不能删除");
            return new ResponseRestful(100,"订单不能删除",null);
        }
        orderHead.setTohStatus(0);
        try {
            tbOrderService.update(orderHead);

            return new ResponseRestful(200,"删除成功",null);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseRestful(100,"删除失败",null);
        }

    }
}
