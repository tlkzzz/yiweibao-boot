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
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
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

    /**
     * 新增套餐
     * @param request
     * @param session
     * @param tbServiceFees
     * @return
     */
    @RequestMapping(value="/addServiceFees")
    @ResponseBody
    public ResponseRestful addServiceFees(HttpServletRequest request, HttpSession session, TbServiceFees tbServiceFees) {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String TOken = httpServletRequest.getHeader("Authorization");
            System.out.println(TOken);
            String name= JWTUtil.getLoginName(TOken);
            TbCompany tbCompany=  tbCompanyService.findByPhone(name);
            //登录的单位信息
//			TbServiceFees tbServiceFeesOne = new TbServiceFees();
//			tbServiceFeesOne.setTsfCompanyId(tbCompany.getTcId());
//			tbServiceFeesOne.setTsfName(tbServiceFees.getTsfName());
//			tbServiceFeesOne = tbServiceFeesService.findOne(tbServiceFeesOne);
//			if(tbServiceFeesOne!=null){
//				return "102";//套餐名称已存在
//			}
            //验证售后套餐套餐名称是否重复
            TbServiceFees fees = tbServiceFeesService.findByNameAndFeesId(tbServiceFees.getTsfName(),null,tbCompany.getTcId().toString());
            if(fees != null){
                return new ResponseRestful(100,"套餐名称已存在",null);
            }
            tbServiceFees.setTsfAddDate(new Timestamp(new Date().getTime()));
            tbServiceFees.setTsfAddPeoper(tbCompany.getTcId());
            tbServiceFees.setTsfCompanyId(tbCompany.getTcId());
            tbServiceFees.setTsfStatus(1);//默认状态为正常
            tbServiceFeesService.save(tbServiceFees);
            return new ResponseRestful(200,"新增成功",null);
        } catch (Exception e) {
            logger.error("[serviceFees/add]出错，错误原因："+e.getMessage());
            e.printStackTrace();
            return new ResponseRestful(100,"新增失败",null);
        }
    }

    /**
     * 修改套餐
     * @param request
     * @param session
     * @param tbServiceFees
     * @return
     */
    @RequestMapping(value="/editServiceFees")
    @ResponseBody
    public ResponseRestful editServiceFees(HttpServletRequest request,HttpSession session,TbServiceFees tbServiceFees) {
        try {
            TbServiceFees tbServiceFeesById = tbServiceFeesService.findById(tbServiceFees.getTsfId());
            if(tbServiceFeesById==null){
                return new ResponseRestful(100,"套餐不存在 ",null);//套餐不存在
            }
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String TOken = httpServletRequest.getHeader("Authorization");
            System.out.println(TOken);
            String name= JWTUtil.getLoginName(TOken);
            TbCompany tbCompany=  tbCompanyService.findByPhone(name);
            //登录的单位信息
            //运费名称被改变
            if(!tbServiceFeesById.getTsfName().equals(tbServiceFees.getTsfName())){
//				TbServiceFees tbServiceFeesOne = new TbServiceFees();
//				tbServiceFeesOne.setTsfCompanyId(tbCompany.getTcId());
//				tbServiceFeesOne.setTsfName(tbServiceFees.getTsfName());
//				tbServiceFeesOne = tbServiceFeesService.findOne(tbServiceFeesOne);
//				if(tbServiceFeesOne!=null){
//					return "103";//套餐名称已存在
//				}
                //验证售后套餐套餐名称是否重复
                TbServiceFees fees = tbServiceFeesService.findByNameAndFeesId(tbServiceFeesById.getTsfName(),tbServiceFeesById.getTsfId().toString(),tbCompany.getTcId().toString());
                if(fees != null){
                    return new ResponseRestful(100,"套餐名称已存在 ",null);
                }
            }
            tbServiceFees.setTsfAddDate(tbServiceFeesById.getTsfAddDate());
            tbServiceFees.setTsfAddPeoper(tbServiceFeesById.getTsfAddPeoper());
            tbServiceFees.setTsfCompanyId(tbServiceFeesById.getTsfCompanyId());
            tbServiceFees.setTsfStatus(tbServiceFeesById.getTsfStatus());
            tbServiceFeesService.update(tbServiceFees);
            return new ResponseRestful(200,"修改成功",null);
        } catch (Exception e) {
            logger.error("[serviceFees/edit]出错，错误原因："+e.getMessage());
            e.printStackTrace();
            return new ResponseRestful(100,"修改失败",null);
        }
    }

    /**
     * 售后套餐状态的改变
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value="/updateStatus")
    @ResponseBody
    public  ResponseRestful updateStatus(HttpServletRequest request,HttpSession session){
        logger.error("售后套餐状态的改变");
        String tsfId = request.getParameter("ids");
        logger.error("tsfId:"+tsfId);
        try {
            TbServiceFees tbServiceFeesById = tbServiceFeesService.findById(Long.valueOf(tsfId));
            if(tbServiceFeesById==null){
                return new ResponseRestful(100,"套餐不存在 ",null);//套餐不存在
            }
            if(tbServiceFeesById.getTsfStatus()==1){//如果正常/使用
                tbServiceFeesById.setTsfStatus(2);//改为暂停/停用
            }else{//否则反之
                tbServiceFeesById.setTsfStatus(1);
            }
            tbServiceFeesService.update(tbServiceFeesById);
            return new ResponseRestful(200,"套餐状态更改成功",null);
        } catch (Exception e) {
            logger.error("[serviceFees/updateStatus]出错，错误原因："+e.getMessage());
            e.printStackTrace();
            return new ResponseRestful(100,"更改失败",null);
        }
    }

}
