package com.src.api.controller;


import com.src.api.entity.TbAsset;
import com.src.api.entity.TbCompany;
import com.src.api.entity.TbProduct;
import com.src.api.entity.TbQrcode;
import com.src.api.service.TbAssetService;
import com.src.api.service.TbCompanyService;
import com.src.api.service.TbProductService;
import com.src.api.service.TbQrcodeService;
import com.src.common.shiro.config.JWTUtil;
import com.src.common.shiro.config.ResponseRestful;
import com.src.common.utils.StringUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/qrcode")
public class TbQrcodeController {
    private static final Logger logger = LogManager.getLogger(TbProductController.class);
    @Resource
    TbCompanyService tbCompanyService;
    @Resource
    TbQrcodeService tbQrcodeServiceImpl;
    @Resource
    TbAssetService tbAssetServiceImpl;
    @Resource
    TbProductService tbProductService;


    @RequestMapping(value="/getGrcode")
    @ResponseBody
    public ResponseRestful getGrcode(HttpServletRequest request, HttpSession session) {

        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String TOken = httpServletRequest.getHeader("Authorization");
            String name= JWTUtil.getLoginName(TOken);
            TbCompany tbCompany=  tbCompanyService.findByPhone(name);


            logger.error("进入二维码列表");
            Map<String, String> param = new HashMap<String,String>();

            param.put("page", request.getParameter("pageNum")); //页数
            param.put("pageSize", request.getParameter("pageSize"));//每页条数
            param.put("tpName", request.getParameter("name"));

            param.put("type", request.getParameter("type"));
            param.put("companyId",tbCompany.getTcId().toString());
            Map<String, Object> json = tbQrcodeServiceImpl.getByParam(param);
//

            List<Map<String,Object>> list = (List<Map<String, Object>>) json.get("list");
            List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
            if(list.size()!=0){
                for (int i=0;i<list.size();i++) {
                    Map<String,Object> maps=new HashMap<String,Object>();
                    maps.put("id",list.get(i).get("id"));
                    maps.put("qrcodenum",list.get(i).get("qrcodenum"));
                    maps.put("qrcodenums",list.get(i).get("qrcodenum"));
                    maps.put("createDate",list.get(i).get("create_date"));
                    maps.put("createPerson",list.get(i).get("create_person"));
                    String sourcetype =(String) list.get(i).get("sourcetype");
                    Long sourceid = (Long) list.get(i).get("sourceid");
                    maps.put("sourcetype",sourcetype);
                    maps.put("sourceid",sourceid);
                    if(sourcetype.equals("asset")){//台账
                        TbAsset asset=  tbAssetServiceImpl.findById(sourceid);
                        maps.put("name",asset.getName());
                    }else if(sourcetype.equals("product")){//商品
                        TbProduct product= tbProductService.findById(sourceid);
                        maps.put("name",product.getTpName());
                    }
                    listMap.add(maps);
                }
                json.put("list",listMap);
            }
//            request.setAttribute("qrcode", json);



            return  new ResponseRestful(200,"查询失败",json);
        } catch (Exception e) {
            logger.error("[]出错，错误原因："+e.getMessage());
            e.printStackTrace();
            return  new ResponseRestful(100,"查询失败",null);
        }
    }
    @RequestMapping(value="/delQrcode")
    @ResponseBody
    public ResponseRestful delQrcode(HttpServletRequest request,HttpSession session) {
        String id=	request.getParameter("ids");

        logger.error("进入二维码删除");
        try {
            String  [] str=id.split(",");
            for(int i=0;i<str.length;i++){
                TbQrcode asset = tbQrcodeServiceImpl.findById(Long.valueOf(id));
                asset.setStatus(0);
//                tbQrcodeServiceImpl.update(asset);
            }
            return  new ResponseRestful(200,"删除成功",null);
        } catch (Exception e) {
            logger.error("[delQrcode/del]出错，错误原因："+e.getMessage());
            e.printStackTrace();

            return new ResponseRestful(100,"删除失败",null);
        }
    }
}
