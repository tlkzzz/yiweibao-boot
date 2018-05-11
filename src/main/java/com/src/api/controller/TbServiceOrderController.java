package com.src.api.controller;

import com.src.api.entity.*;
import com.src.api.service.*;
import com.src.common.entity.Userinfo;
import com.src.common.service.UserinfoService;
import com.src.common.shiro.config.JWTUtil;
import com.src.common.shiro.config.ResponseRestful;
import com.src.common.utils.StringUtil;
import com.src.common.utils.XGPushUtil;
import com.src.common.utils.hjtech.util.LogTool;
import com.src.common.utils.org.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

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
    @Resource
    TbAssetService  tbAssetServiceImpl;


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


    @ResponseBody
    @RequestMapping(value = "/getAssigned")
    public ResponseRestful getAssigned(HttpServletRequest request) {
        LogTool.WriteLog("待分派工单详情。。。。");
        try {
         Map<String, Object> json=new HashMap<String, Object>();

        String tsoId = request.getParameter("tsoId");

        TbServiceOrder serviceOrder = tbServiceOrderService.findById(Long.valueOf(tsoId));
//        TbOrderDetail orderDetail=null;
//        if(serviceOrder.getTsoOrderDetailId().contains(",")){
//            orderDetail = tbOrderDetailService.findById(Long.valueOf(serviceOrder.getTsoOrderDetailId().split(",")[0]));
//        }else{
//            orderDetail = tbOrderDetailService.findById(Long.valueOf(serviceOrder.getTsoOrderDetailId()));
//        }
//        //判断当前时间与报修截止时间
        String ifOutTime = "0";//0:没有超出报修时间  1：超出报修时间
            TbOrderDetail orderDetail=null;
        if(serviceOrder.getTsoOrderDetailId()!=null){
            orderDetail = tbOrderDetailService.findById(Long.valueOf(serviceOrder.getTsoOrderDetailId().split(",")[0]));
            if((new Date().getTime())>orderDetail.getTodEndTime().getTime()){
                ifOutTime = "1";
            }
        }
        List<Map<String, Object>> list = tbServiceOrderService.findTbServiceOrderDetail(Long.valueOf(tsoId),"2");
        if(list.size()>0){
            //详情中可以用此方法(只有一个订单可以)
            String specJsonValue = "";
            for (Map<String, Object> m : list) {
                if(m.get("sousrceType").equals("product")){//商品
                    m.put("ifOutTime", ifOutTime);
                    //tod_spec_json中截取value的值空格拼接([{"tsName":"硬盘","value":"500G"},{"tsName":"cpu","value":"双核"}])
                    String todSpecJson = m.get("tod_spec_json").toString();
                    LogTool.WriteLog("todSpecJson:"+todSpecJson);
                    //todSpecJson不为空的时候进行以下操作
                    if(!StringUtils.isBlank(todSpecJson)){
                        //示例：    []
                        todSpecJson = todSpecJson.replaceAll(" ", "");//去空格
                        todSpecJson = todSpecJson.replaceAll("\\[", "");
                        todSpecJson = todSpecJson.replaceAll("\\]", "");//{tsName=内存, value=8GB}, {tsName=内存, value=8GB}
                        todSpecJson = todSpecJson.replaceAll("\\{", "");
                        todSpecJson = todSpecJson.replaceAll("\\}", "");
                        todSpecJson = todSpecJson.replaceAll("\"", "");//去掉双引号
                        //tsName=内存, value=8GB, tsName=内存, value=8GB
                        String[] arrTodSpecJson = todSpecJson.split(",");
                        for (int i = 0; i < arrTodSpecJson.length; i++) {
                            //如果是数组第奇数个元素则获取值
                            if(i%2==1){
                                String[] arrSpec = arrTodSpecJson[i].split("=");//value=8GB
                                //如果字符串用逗号分割后数组长度没有变为2则用:分割
                                if(arrSpec.length!=2){
                                    arrSpec = arrTodSpecJson[i].split(":");
                                }
                                if(specJsonValue.equals("")){
                                    specJsonValue = arrSpec[1];//获取第二个元素
                                }else{
                                    specJsonValue = specJsonValue + " " +arrSpec[1];//获取第二个元素用空格隔开
                                }
                            }
                        }
                    }
                    m.put("specJsonValue", specJsonValue);//返回商品规格值
                    //获取工单图片，用逗号分隔
                    String tsoPicsFile = "";
                    if(m.get("tsoPicsFile")!=null){
                        tsoPicsFile = m.get("tsoPicsFile").toString();
                    }
                    String[] arrTpPics = tsoPicsFile.split(",");//获取图片
                    List<Map<String, Object>> listPics = new ArrayList<Map<String, Object>>();

                    for (int i = 0; i < arrTpPics.length; i++) {//遍历上传工单图片
                        Map<String, Object> picsMap = new HashMap<String, Object>();
                        if(!arrTpPics[i].equals("")){
                            picsMap.put("tsoPic", arrTpPics[i]);
                            listPics.add(picsMap);
                        }
                    }
                    m.put("tsoPicsList", listPics);

                }else if(m.get("sousrceType").equals("asset")){//设备
                    Long goodId=	(Long) m.get("goodId");
                    List<Map<String, Object>> li=this.tbAssetServiceImpl.asseSearch(goodId.toString());
                    if(li.size()!=0){
                        m.put("assetTypeId",  li.get(0).get("assetTypeId"));
                        m.put("assetTypeName",  li.get(0).get("assetTypeName"));
                        m.put("assetId",  li.get(0).get("assetId"));
                        m.put("assetName",  li.get(0).get("assetName"));
                        m.put("simpleName",  li.get(0).get("simpleName"));
                        m.put("position",  li.get(0).get("position"));
                        m.put("desp",  li.get(0).get("desp"));
                        m.put("pics",  li.get(0).get("pics"));
                    }
                }
            }
           json.put("detail", list.get(0));
        }else{
            json.put("detail", new HashMap<String, Object>());
        }
        TbCustomers tbCustomers = tbCustomersService.findById(serviceOrder.getTsoCustomerId());
        if(tbCustomers.getTcLevel()<2000){
            json.put("tcLevelValue", "普通会员");
        }else{
            json.put("tcLevelValue", "一星会员");
        }
        if(serviceOrder.getTsoPayType() == null || serviceOrder.getTsoPayType().equals("")){
            json.put("tsoPayType", 0);
        }else{
            json.put("tsoPayType", serviceOrder.getTsoPayType());
        }

        return new ResponseRestful(200,"查询成功",json);

    } catch (Exception e) {
        logger.error("[]出错，错误原因："+e.getMessage());
        e.printStackTrace();
        return new ResponseRestful(100,"查询失败",null);
    }
    }

    @ResponseBody
    @RequestMapping(value="/dispatch",method= RequestMethod.GET)
    public ResponseRestful dispatch(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        String tsoId = request.getParameter("tsoId");
        String tseId = request.getParameter("tseId");
        //参数验证
        if(StringUtil.isEmptyNull(tsoId) ||StringUtil.isEmptyNull(tseId)){
            return new ResponseRestful(100,"验证不通过",null);
        }


        TbServiceOrder tbServiceOrder = null;
        try {
            //验证tmId、tseId是否存在
//			TbServicePoints servicePoints = tbServicePointsService.findById(Long.valueOf(tmId));
//			if(servicePoints==null||servicePoints.getTspStatus()!=1){
//				map.put("code", "107");
//				map.put("message", "登录用户不存在");
//				return map;
//			}
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String TOken = httpServletRequest.getHeader("Authorization");
            System.out.println(TOken);
            String name= JWTUtil.getLoginName(TOken);
            TbCompany tbCompany=  tbCompanyService.findByPhone(name);




            if(tbCompany==null||tbCompany.getTcStatus()!=1){
                return new ResponseRestful(100,"登录用户不存在",null);
            }
            Userinfo companyUser = userinfoService.findUserByNameANDGroupId(tbCompany.getTcLoginUser(),"2");
            if(companyUser == null || companyUser.getDeleteMark() != 1){
                LogTool.WriteLog(map.toString());
                return new ResponseRestful(100,"账户不存在",null);
            }
            TbServiceEmployee serviceEmployee = tbServiceEmployeeService.findById(Long.valueOf(tseId));
            if(serviceEmployee==null||serviceEmployee.getTseStatus()!=1){
                return new ResponseRestful(100,"员工不存在",null);
            }
            tbServiceOrder = tbServiceOrderService.findById(Long.valueOf(tsoId));
//			TbOrderDetail orderDetail = new TbOrderDetail();
//			orderDetail.setTodEndTime(todEndTime);
//			orderDetail.setTodCount(todCount);
            if(null == tbServiceOrder){
                return new ResponseRestful(100,"订单不存在，不能派工",null);
            }else if(tbServiceOrder.getTsoStatus() >= 2){
                return new ResponseRestful(100,"此订单已派工，不能重复派工",null);
            }else{
                tbServiceOrder.setTsoStatus(2);//已派工
//				tbServiceOrderService.update(tbServiceOrder);

                TbServiceOrderDetail tbServiceOrderDetail = new TbServiceOrderDetail();
                tbServiceOrderDetail.setTsodHeadId(Long.valueOf(tsoId));
                tbServiceOrderDetail.setTsodAllocateDate(new Timestamp(new Date().getTime()));
                tbServiceOrderDetail.setTsodAllocatePerson(companyUser.getId());//派工人
//				tbServiceOrderDetail.setTsodAllocatePerson(tbCompany.getTcId());//
                tbServiceOrderDetail.setTsodWorkerId(Long.valueOf(tseId));
                tbServiceOrderDetail.setTsodStatus(1);//1：初始分配
//				tbServiceOrderDetailService.save(tbServiceOrderDetail);

                /**
                 * 消息推送
                 */
                TbMessage message = new TbMessage();
                message.setTmReceiveId(Long.valueOf(tseId));
                message.setTmType(2);
                message.setTmTitle("系统消息");
                message.setTmContent("您有一条新的维修工单，请尽快确认。");
                message.setTmStatus(1);
                message.setTmAddDate(new Timestamp(new Date().getTime()));
                tbMessageServicer.save(message);


               // tbServiceOrderService.updateOrderDispatch(tbServiceOrder,tbServiceOrderDetail,message);
                //新增的消息
                TbMessage m = tbMessageServicer.findOne(message);
                Map<String, Object> custom = new HashMap<String, Object>();
                custom.put("messageId", m.getTmId());
                custom.put("messageTitle", m.getTmTitle());
                custom.put("messageContent", m.getTmContent());
                custom.put("messageTime", m.getTmAddDate());
                //接收人
                TbServiceEmployee employee = tbServiceEmployeeService.findById(Long.valueOf(tseId));
                JSONObject obj = XGPushUtil.pushSingleAccount2(employee.getTseId().toString(), "您有新的派工", "您有一条新的维修工单，请尽快确认。编号："+tbServiceOrder.getTsoNumber(), custom);
                JSONObject objIOS = XGPushUtil.pushSingleAccountIOS1(custom,employee.getTseId().toString(),"您有一条新的维修工单，请尽快确认。编号："+tbServiceOrder.getTsoNumber());
                System.out.println("消息推送："+obj);
                System.out.println("消息推送IOS："+objIOS);
                /**
                 * 发送短信
                 */
//                Client client = new Client(new URL("http://smsapi.hjtechcn.cn:6080/smsWs/sms.ws?wsdl"));
//                Object[] o = client.invoke("sendSMS", new Object[]{"tuzi","e10adc3949ba59abbe56e057f20f883e",employee.getTseMobile(),"您有一条新的维修工单，请尽快确认。"+"【易维保】","10659800","shcmcc"});
//                System.out.println("短信发送："+o[0].toString());
                return new ResponseRestful(200,"派工成功",null);
            }
        } catch (Exception e) {
            logger.error("[TbServiceOrderController --> dispatch]:出错,错误原因:" + e.getMessage());
            LogTool.WriteLog(e.getMessage());
            e.printStackTrace();
            return new ResponseRestful(100,"派工失败",null);
        }
    }



    @ResponseBody
    @RequestMapping(value = "/dispatchList")
    public ResponseRestful dispatchList(HttpServletRequest request) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("tsoId", request.getParameter("tsoId"));

        HashMap<String, Object> json=   tbServiceOrderService.findOrderEmpForJson(params);

        return new ResponseRestful(200,"查询成功",json);
    }





}
