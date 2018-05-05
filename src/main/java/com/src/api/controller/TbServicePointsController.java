package com.src.api.controller;

import com.src.api.entity.TbCompany;
import com.src.api.entity.TbServicePoints;
import com.src.api.service.TbCompanyService;
import com.src.api.service.TbServicePointsService;
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
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value="/servicePoints")
public class TbServicePointsController {
    private static final Logger logger = LogManager.getLogger(TbServicePointsController.class);
    @Resource
    TbCompanyService tbCompanyService;
    @Resource
    TbServicePointsService tbServicePointsService;

    @ResponseBody
    @RequestMapping(value="/getServicePoints")
    public ResponseRestful getServicePoints(HttpServletRequest request, HttpSession session) {
        logger.error("进入网点管理列表");
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String TOken = httpServletRequest.getHeader("Authorization");
            String name= JWTUtil.getLoginName(TOken);
            TbCompany tbCompany=  tbCompanyService.findByPhone(name);
            Map<String, String> param = new HashMap<String, String>();
            param.put("companyId", tbCompany.getTcId().toString());
            param.put("tspType", request.getParameter("tspType"));
            param.put("tspStatus", request.getParameter("tspStatus"));
            param.put("tspName", request.getParameter("name"));
            param.put("page", request.getParameter("pageNum")); //页数
            param.put("pageSize", request.getParameter("pageSize"));//每页条数
            Map<String, Object> json = tbServicePointsService.getByparam(param);
            //登录单位下的网点
//			Map<String, Object> tbServicePoints = tbServicePointsService.getByCompanyId(tbCompany.getTcId());

            return new ResponseRestful(200,"查询成功",json);
        } catch (Exception e) {
            logger.error("[servicePoints/points]出错，错误原因："+e.getMessage());
            e.printStackTrace();
            return new ResponseRestful(100,"查询失败",null);
        }
    }
    @RequestMapping(value="/delServicePoints")
    @ResponseBody
    public  ResponseRestful delServicePoints(HttpServletRequest request,HttpSession session){
        logger.error("删除网点");
        String id=	request.getParameter("ids");
        try {
            TbServicePoints tbServicePoints = tbServicePointsService.findById(Long.valueOf(id));
            if(tbServicePoints==null){
                new ResponseRestful(100,"项目不存在",null);//网点不存在
            }
            //判断网点下有没有员工
            List<Map<String, Object>> list = tbServicePointsService.findEmpByPointId(tbServicePoints.getTspId().toString());
            if(list.size()>0){
                return new ResponseRestful(100,"该项目下还有员工不能删除",null);//该网点下还有员工不能删除
            }
            tbServicePoints.setTspStatus(0);//逻辑删除
            tbServicePointsService.update(tbServicePoints);
            return new ResponseRestful(200,"删除成功",null);
        } catch (Exception e) {
            logger.error("[servicePoints/del]出错，错误原因："+e.getMessage());
            e.printStackTrace();
            return new ResponseRestful(100,"删除失败",null);
        }
    }




    @RequestMapping(value="/getCompanyServicePoints")
    @ResponseBody
    public ResponseRestful getCompanyServicePoints(HttpServletRequest request,HttpSession session){
        logger.error("ajax加在单位下的网点列表");
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String TOken = httpServletRequest.getHeader("Authorization");
            String name= JWTUtil.getLoginName(TOken);
            TbCompany tbCompany=  tbCompanyService.findByPhone(name);
            List<TbServicePoints> list=tbServicePointsService.findByCompanyId(tbCompany.getTcId());
            return new ResponseRestful(200,"查询成功",list);
        } catch (Exception e) {
            logger.error("[servicePoints/pointslist.ajax]出错，错误原因："+e.getMessage());
            e.printStackTrace();
            return new ResponseRestful(100,"查询失败",null);
        }
    }
}
