package com.src.api.controller;

import com.src.api.entity.TbCompany;
import com.src.api.service.TbCompanyService;
import com.src.common.entity.Userinfo;
import com.src.common.service.UserinfoService;
import com.src.common.shiro.config.JWTUtil;
import com.src.common.shiro.config.ResponseRestful;
import com.src.common.utils.StringUtil;
import com.src.common.utils.hjtech.util.LogTool;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value="/company")
public class TbCompanyController {

    private static final Logger LOGGER = LogManager.getLogger(TbCompanyController.class);
    @Autowired
    TbCompanyService tbCompanyService;
    @Autowired
    UserinfoService userinfoService;

    @RequestMapping(value = "/getCompany",method = RequestMethod.GET)
    public ResponseRestful getCompany(ServletRequest request, ServletResponse response){

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String TOken = httpServletRequest.getHeader("Authorization");
        System.out.println(TOken);
        String name= JWTUtil.getLoginName(TOken);
        TbCompany tbCompany=  tbCompanyService.findByPhone(name);



        return new ResponseRestful(200,"查询成功",tbCompany);

    }


    /**
     * 编辑单位信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editCompany", method = RequestMethod.POST)
    public ResponseRestful editCompany(HttpServletRequest request) {
        LOGGER.info("editCompany(编辑信息)....");
        String tcId = request.getParameter("tcId");//单位id
        String tcName = request.getParameter("tcName");//单位名称
//        String prov = request.getParameter("prov");//省id
//        String city = request.getParameter("city");//市id
//        String county = request.getParameter("county");//区id
        String tcAddress = request.getParameter("tcAddress");//地址详情
        String tcContactName = request.getParameter("tcContactName");//联系人
        String tcContactPhone = request.getParameter("tcContactPhone");//联系电话
        String tcLogo = request.getParameter("tcLogo");//logo


        if(StringUtils.isBlank(tcId)){
            if(StringUtil.isEmptyNull(tcName)
                    ||StringUtil.isEmptyNull(tcAddress)||StringUtil.isEmptyNull(tcContactName)
                    ||StringUtil.isEmptyNull(tcContactPhone)||StringUtil.isEmptyNull(tcLogo)){
                return new ResponseRestful(100,"提交参数不完整",null);
            }
        }
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String TOken = httpServletRequest.getHeader("Authorization");
            System.out.println(TOken);
            String name= JWTUtil.getLoginName(TOken);
            String userName =JWTUtil.getLoginName(TOken);
            TbCompany tbCompany=  tbCompanyService.findByPhone(name);

            //查询单位信息表(tb_company)根据账号和状态(tc_status=1)验证
            if(tbCompany==null||tbCompany.getTcStatus()!=1){
                return  new ResponseRestful(100,"账户不存在",null);
            }
            if(!StringUtils.isBlank(tcName)){
                //tcName验重（判断为状态1,2不能，状态为0也就是被删除的时候可以）,除单位自己以外的
                TbCompany companyName = tbCompanyService.findByName(tcName,tbCompany.getTcId().toString());
                if(companyName != null){

                    return  new ResponseRestful(100,"该帐号单位名称已注册",null);
                }
            }
            Userinfo companyUser = userinfoService.findUserByNameANDGroupId(userName,"2");
            if(companyUser == null || companyUser.getDeleteMark() != 1){
                return new ResponseRestful(100,"账户不存在",null);
            }

                tbCompany.setTcName(tcName);
                companyUser.setName(tcName);
//                tbCompany.setTcProvId(Long.parseLong(prov));
//                tbCompany.setTcCityId(Long.valueOf(city));
//                tbCompany.setTcRegionId(Long.valueOf(county));
                tbCompany.setTcAddress(tcAddress);
                tbCompany.setTcContactName(tcContactName);
                tbCompany.setTcContactPhone(tcContactPhone);
                    tbCompany.setTcLogo(tcLogo);
            tbCompanyService.updatePassANDInfo(tbCompany, companyUser);
            return new ResponseRestful(200,"修改成功",null);
        } catch (Exception e) {
            LOGGER.error("[TbCompanyRegistController-modify_info]:数据修改错误,错误原因:"
                    + e.getMessage());
            e.printStackTrace();
            LogTool.WriteLog(e.getMessage());

            return new ResponseRestful(100,"修改失败",null);
        }
    }

}
