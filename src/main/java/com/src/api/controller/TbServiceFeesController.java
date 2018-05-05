package com.src.api.controller;

import com.src.api.entity.TbCompany;
import com.src.api.entity.TbServiceFees;
import com.src.api.service.TbCompanyService;
import com.src.api.service.TbServiceFeesService;
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
@RequestMapping(value="/serviceFees")
public class TbServiceFeesController {

    private static final Logger logger = LogManager.getLogger(TbCompanyController.class);
    @Resource
    TbCompanyService tbCompanyService;

    @Resource
    TbServiceFeesService tbServiceFeesService;

    /**
     * 售后套餐管理列表
     * @param request
     * @return
     */
    @RequestMapping(value="/getServiceFees")
    public ResponseRestful getServiceFees(HttpServletRequest request) {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String TOken = httpServletRequest.getHeader("Authorization");
            System.out.println(TOken);
            String name= JWTUtil.getLoginName(TOken);
            TbCompany tbCompany=  tbCompanyService.findByPhone(name);


            Map<String, String> param = new HashMap<String,String>();
            param.put("companyId", tbCompany.getTcId().toString());
            param.put("tsfName", request.getParameter("keywords"));
            param.put("tsfStatus", request.getParameter("tsfStatus"));
            param.put("page", request.getParameter("pageNum"));
            param.put("pageSize", request.getParameter("pageSize"));
            Map<String, Object> json = tbServiceFeesService.getByParam(param);

            return new ResponseRestful(200,"查询成功",json);
        } catch (Exception e) {
            logger.error("[serviceFees/fees]出错，错误原因："+e.getMessage());
            e.printStackTrace();
            return new ResponseRestful(100,"查询失败",null);
        }
    }
    /**
     * 批量删除售后套餐
     * @param request
     * @return
     */
    @RequestMapping(value="/delServiceFees")
    @ResponseBody
    public  ResponseRestful delServiceFees(HttpServletRequest request){
        logger.error("批量删除售后套餐");
        String id = request.getParameter("ids");
        try {
            String[] _tsfId =  id.split(",");//去掉最后一个逗号，并以逗号分割
            for (int i = 0; i < _tsfId.length; i++) {
                TbServiceFees tbServiceFeesById = tbServiceFeesService.findById(Long.valueOf(_tsfId[i]));
                if(tbServiceFeesById==null){
                    return new ResponseRestful(100,"套餐不存在",null);
                }

                tbServiceFeesById.setTsfStatus(0);//逻辑删除
//                tbServiceFeesService.update(tbServiceFeesById);
            }

            return new ResponseRestful(200,"删除成功 ",null);
        } catch (Exception e) {
            logger.error("[serviceFees/del]出错，错误原因："+e.getMessage());
            e.printStackTrace();
            return new ResponseRestful(100,"删除失败 ",null);
        }
    }
}
