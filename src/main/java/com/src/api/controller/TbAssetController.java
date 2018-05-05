package com.src.api.controller;


import com.src.api.entity.TbAsset;
import com.src.api.entity.TbAssetType;
import com.src.api.entity.TbCompany;
import com.src.api.entity.TbQrcode;
import com.src.api.service.*;
import com.src.common.shiro.config.JWTUtil;
import com.src.common.shiro.config.ResponseRestful;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/asset")
public class TbAssetController {

    private static final Logger logger = LogManager.getLogger(TbCompanyController.class);



    @Autowired
    TbAssetService tbAssetServiceImpl;
    @Autowired
    TbAssetTypeService tbAssetTypeServiceImpl;
    @Autowired
    TbServicePointsService tbServicePointsService;
    @Autowired
    TbCompanyService tbCompanyService;
    @Autowired
    TbQrcodeService tbQrcodeServiceImpl;

    @RequestMapping(value="/getAsset")
    public ResponseRestful getAsset(HttpServletRequest request) {
//        Map<String,Object> map=new HashMap<String,Object>();
        logger.error("进入设备列表");
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String TOken = httpServletRequest.getHeader("Authorization");
            System.out.println(TOken);
            String name= JWTUtil.getLoginName(TOken);
            TbCompany tbCompany=  tbCompanyService.findByPhone(name);

            Map<String, String> param = new HashMap<String,String>();
            param.put("page", request.getParameter("pageNum"));
            param.put("pageSize", request.getParameter("pageSize"));
            param.put("companyId",tbCompany.getTcId().toString());
            param.put("name", request.getParameter("keyword"));
            Map<String, Object> json = tbAssetServiceImpl.getByParam(param);
//            map.put("list",assetType);
//            return map;

            return new ResponseRestful(200,"查询成功",json);
        } catch (Exception e) {
            logger.error("[]出错，错误原因："+e.getMessage());
            e.printStackTrace();
            return new ResponseRestful(100,"查询失败",null);
        }
    }


    /**
     * 删除设备
     * @param request

     * @return
     */
    @RequestMapping(value="/delAsset")
    @ResponseBody
    public ResponseRestful delAsset(HttpServletRequest request) {
        Map<String,Object> map=new HashMap<String,Object>();
        String id = request.getParameter("ids");
        logger.error("进入台账删除");
        try {
            String  [] str=id.split(",");
            for(int i=0;i<str.length;i++){
                TbAsset asset = tbAssetServiceImpl.findById(Long.valueOf(str[i]));
                asset.setStatus(0);


                //删除设备二维码
                TbQrcode qrcode = new TbQrcode();
                qrcode.setSourcetype("asset");
                qrcode.setSourceid(Long.valueOf(str[i]));
                qrcode.setStatus(1);
                List<TbQrcode> list =tbQrcodeServiceImpl.search(qrcode);
                if(list.size()==1){
                    Long ids= list.get(0).getId();
                    TbQrcode qr = tbQrcodeServiceImpl.findById(ids);
                    qr.setStatus(0);
                    tbQrcodeServiceImpl.update(qr);
                }

                tbAssetServiceImpl.update(asset);
            }
            return  new ResponseRestful(200,"删除成功",null);
        } catch (Exception e) {
            logger.error("[assetType/edit]出错，错误原因："+e.getMessage());
            e.printStackTrace();
            return new ResponseRestful(100,"删除失败",null);
        }
    }
    @RequestMapping(value="/getAssetType")
    public ResponseRestful getAssetType(HttpServletRequest request,HttpSession session) {
        logger.error("进入设备分类列表");
        try {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("page", request.getParameter("pageNum"));
            params.put("pageSize", request.getParameter("pageSize"));
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String TOken = httpServletRequest.getHeader("Authorization");
            String name= JWTUtil.getLoginName(TOken);
            TbCompany tbCompany=  tbCompanyService.findByPhone(name);
            params.put("name", request.getParameter("keyword"));
            params.put("companyId",tbCompany.getTcId().toString());
            Map<String, Object> json = tbAssetTypeServiceImpl.getByParam(params);

            return new ResponseRestful(200,"查询成功",json);
        } catch (Exception e) {
            logger.error("[]出错，错误原因："+e.getMessage());
            e.printStackTrace();
            return new ResponseRestful(100,"查询失败",null);
        }
    }

    @RequestMapping(value="/delAssetType")
    @ResponseBody
    public ResponseRestful delAssetType(HttpServletRequest request,HttpSession session) {

        logger.error("进入台账分类删除");

        String  id = request.getParameter("ids");
        try {
            String  [] str=id.split(",");
            for(int i=0;i<str.length;i++){

                TbAssetType type=tbAssetTypeServiceImpl.findById(Long.valueOf(str[i]));
                type.setStatus(0);
               // tbAssetTypeServiceImpl.update(type);
            }
            return new ResponseRestful(200,"删除成功",null);
        } catch (Exception e) {
            logger.error("[assetType/edit]出错，错误原因："+e.getMessage());
            e.printStackTrace();

            return  new ResponseRestful(100,"删除失败",null);
        }
    }

}
