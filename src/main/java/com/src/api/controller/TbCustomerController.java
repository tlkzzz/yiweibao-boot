package com.src.api.controller;


import com.src.api.entity.TbCompany;
import com.src.api.entity.TbCustomers;
import com.src.api.service.TbCompanyService;
import com.src.api.service.TbCustomersService;
import com.src.api.service.TbOrderHeadService;
import com.src.common.entity.Userinfo;
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
@RequestMapping(value="/customer")
public class TbCustomerController {
    private static final Logger logger = LogManager.getLogger(TbCompanyController.class);
    @Resource
    TbCompanyService tbCompanyService;
    @Resource
    TbCustomersService tbCustomersService;
    @Resource
    UserinfoService userinfoService;
    @Resource
    TbOrderHeadService tbOrderHeadService;



    @ResponseBody
    @RequestMapping(value = "/getCustomer")
    public ResponseRestful getCustomer(HttpServletRequest request) {

        LogTool.WriteLog("page:" + request.getParameter("page"));
        try {

            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String TOken = httpServletRequest.getHeader("Authorization");
            System.out.println(TOken);
            String name= JWTUtil.getLoginName(TOken);
            TbCompany tbCompany=  tbCompanyService.findByPhone(name);


        HashMap<String, String> params = new HashMap<String, String>();
        params.put("page", request.getParameter("pageNum"));
        params.put("pageSize", request.getParameter("pageSize"));
        params.put("sort", request.getParameter("sort"));
        params.put("provice", request.getParameter("provice"));
        params.put("city", request.getParameter("city"));
        params.put("country", request.getParameter("country"));
        params.put("AddDate", request.getParameter("AddDate"));
        params.put("AddDateEnd", request.getParameter("AddDateEnd"));
        params.put("tcType", request.getParameter("tcType"));
        params.put("tcName", request.getParameter("keywords"));
        params.put("tseUnitid", request.getParameter("tseUnitid"));
        params.put("order", request.getParameter("order"));
        params.put("tcStatus", request.getParameter("tcStatus"));
        params.put("companyId", ""+tbCompany.getTcId());

            Map<String, Object> json =  tbCustomersService.findForJsonOfMember(params);
            return new ResponseRestful(200,"查询成功",json);

        } catch (Exception e) {
            logger.error("[]出错，错误原因："+e.getMessage());
            e.printStackTrace();
            return new ResponseRestful(100,"查询失败",null);
        }
    }

    /**
     * 批量删除
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delCustomer")
    public ResponseRestful deletes(HttpServletRequest request) {
        LogTool.WriteLog("删除:");
        LogTool.WriteLog("tcIds:"+request.getParameter("tcIds"));
        String id = request.getParameter("ids");//客户id，逗号拼接
        try {
            //客户id数组
            String[] arrTcIds = id.split(",");
            for (int i = 0; i < arrTcIds.length; i++) {
                TbCustomers tbCustomers = tbCustomersService.findById(Long.valueOf(arrTcIds[i]));
                if(tbCustomers == null){
                    return  new ResponseRestful(100,"帐号不存在",null);//帐号不存在
                }
                Userinfo userinfo = new Userinfo();
                userinfo.setAccount(tbCustomers.getTcLoginUser());
                userinfo.setName(tbCustomers.getTcName());
                userinfo.setPassword(tbCustomers.getTcLoginPass());
                userinfo.setGroupId(3);//会员（tb_customer）
                userinfo.setDeleteMark(1);

                TbCustomers tempBean = tbCustomersService.findById(Long.valueOf(arrTcIds[i]));
                tempBean.setTcStatus(0);
                if (!tbOrderHeadService.canDelete(tempBean)) {//查询该客户（会员）是否有订单
                    return new ResponseRestful(100,"会员有订单不能删除",null);//会员有订单不能删除
                }
            }
            String ret = tbCustomersService.deleteCustomersByIds(arrTcIds);

            if(ret.equals("100")){
                return new ResponseRestful(200,"删除成功 ",null);
            }else {
                return new ResponseRestful(100,"删除失败 ",null);
            }


        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[/customer/deletes()]：错误原因:" + e.getMessage());
            return  new ResponseRestful(100,"删除失败",null);
        }
    }

}
