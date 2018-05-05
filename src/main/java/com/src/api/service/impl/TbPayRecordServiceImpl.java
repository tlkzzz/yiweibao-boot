package com.src.api.service.impl;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.src.common.utils.IdGen;
import com.src.common.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.TbOrderDetailDao;
import com.src.api.dao.TbOrderHeadDao;
import com.src.api.dao.TbOrderInsuranceDao;
import com.src.api.dao.TbPayRecordDao;
import com.src.api.dao.TbServiceOrderDao;
import com.src.api.entity.TbPayRecord;
import com.src.api.entity.TbSmsRechargeRecord;
import com.src.api.service.TbPayRecordService;
import com.src.api.service.TbServiceOrderService;
import com.src.api.service.TbSmsRechargeRecordService;

@Service
public class TbPayRecordServiceImpl extends BaseServiceImpl<TbPayRecord, Long> implements TbPayRecordService {

//	@Value("#{config['base_url']}")
	String baseUrl="http://hjtech.wicp.net:8210/rabbit-apihttp://hjtech.wicp.net:8210/rabbit-api";
//	@Value("#{config['appId']}")
	String appId="wxe1312e10d0a77bc3";
	public Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	TbPayRecordDao tbPayRecordDao;
	@Resource TbOrderHeadDao tbOrderHeadDao;
	@Resource TbOrderDetailDao tbOrderDetailDao;
	@Resource TbOrderInsuranceDao tbOrderInsuranceDao;
	@Resource TbServiceOrderDao tbServiceOrderDao;
	@Resource TbServiceOrderService tbServiceOrderService;
	@Resource TbSmsRechargeRecordService tbSmsRechargeRecordService;
	
	@Override
	public BaseDao<TbPayRecord, Long> getGenericDao() {
		return tbPayRecordDao;
	}

	@Override
	public TbPayRecord findByNumber(String number) {
		TbPayRecord record = new TbPayRecord();
		record.setTprNumber(number);
		record = tbPayRecordDao.searchOne(record);
		return record;
	}

	@Override
	public Map<String, Object> saveRecord(String toId, String way,
			Integer type, String payResource) throws Exception {
		Map<String, Object> result = new HashMap<>();
		Double amount = 0.00;//支付总金额
		String tradeNo = "";//宝付第三方订单交易号
		String[] arrToId = toId.split(",");
		String tprNumber = String.valueOf(IdGen.get().nextId());//订单支付记录编号
		//遍历每个订单
		for (int i = 0; i < arrToId.length; i++) {
			//添加支付记录(多个订单id)
			TbPayRecord tbPayRecord = new TbPayRecord();
			//每个订单支付金额
			Double totalPrice = 0.00;
			if (type == 4) {//充值
				if(!StringUtil.isNumber(arrToId[i])){
					result.put("code", "107");
					result.put("message", "订单id参数有误");
					logger.error(result.toString());
					return result;
				}
				TbSmsRechargeRecord smsRechargeRecord = tbSmsRechargeRecordService.findById(Long.parseLong(arrToId[i]));
				if (smsRechargeRecord == null) {
					result.put("code", "104");
					result.put("message", "充值记录不存在");
					logger.error(result.toString());
					return result;
				}
				if (smsRechargeRecord.getTsrrStatus() == 1) {//1成功 0失败 2充值中
					result.put("code", "105");
					result.put("message", "已充值不能重复充值");
					logger.error(result.toString());
					return result;
				}
				amount += smsRechargeRecord.getTsrrAmount();
				tprNumber = smsRechargeRecord.getTsrrNumber();
			}
//			else if (type == 3) {//立即买单支付
//				if(!StringUtil.isNumber(arrToId[i])){
//					result.put("code", "107");
//					result.put("message", "订单id参数有误");
//					logger.error(result.toString());
//					return result;
//				}
//				TbOrderDirect orderDirect = tbOrderDirectService.findById(Long.valueOf(arrToId[i]));
//				TbRechargeRecord rechargeRecord = tbRechargeRecordService.findById(Long.parseLong(arrToId[i]));
//				if (orderDirect == null || orderDirect.getToStatus() == 0) {
//					result.put("code", "104");
//					result.put("message", "订单不存在");
//					logger.error(result.toString());
//					return result;
//				}
//				
//				if (orderDirect.getToStatus() != 1) {
//					result.put("code", "105");
//					result.put("message", "订单已支付不能重复支付");
//					logger.error(result.toString());
//					return result;
//				}
//				logger.error("orderAmount:"+orderDirect.getToTotalPrice());
//				amount = amount + orderDirect.getToTotalPrice()-orderDirect.getToDeductionPrice();
//				totalPrice = orderDirect.getToTotalPrice()-orderDirect.getToDeductionPrice();
//				tbPayRecord.setTprNumber(tprNumber);
//				tbPayRecord.setTprAmount(0.00);//账户金额
//				tbPayRecord.setTprAchievementAmount(0.00);//业绩金额
//				tbPayRecord.setTprRealAmount(totalPrice);//实付款
//				tbPayRecord.setTprStatus(0);//0:未支付 1:支付成功 2:支付失败
//				tbPayRecord.setTprTarget(Long.parseLong(arrToId[i]));//普通订单/退货订单/立即买单订单id
//				tbPayRecord.setTprType(3);//1:普通订单 2:退货订单 3:直接下单
//				tbPayRecord.setTprWay(0);//1:支付宝 2:微信 3:银联4:账户余额
//				tbPayRecord.setTprAddtime(new Timestamp(System.currentTimeMillis()));
//				tbPayRecord.setTprTicketNum(orderDirect.getToDeductionPrice());//年票数量
//				logger.error("amount:"+amount);
//				tbPayRecordDao.save(tbPayRecord);
//			}
		}
		
		//添加支付记录(单个订单id)
//		TbPayRecord tbPayRecord = new TbPayRecord();
//		tbPayRecord.setTprNumber(String.valueOf(IdGen.get().nextId()));
//		tbPayRecord.setTprAmount(amount);//支付金额
//		tbPayRecord.setTprStatus(1);
//		tbPayRecord.setTprTarget(toId);
//		tbPayRecord.setTprType(type);
//		tbPayRecord.setTprWay(0);
//		tbPayRecord.setTprAddtime(new Timestamp(System.currentTimeMillis()));
//		tbPayRecordDao.save(tbPayRecord);
		result.put("code", "100");
		result.put("message", "支付成功");
		result.put("number", tprNumber);
		result.put("amount", amount);
		String notifyurl = baseUrl + "/pay/notify/" + way + "/" +tprNumber+"/"+type+"/"+payResource;
		if(payResource.equals("3")){//付款来源：1安卓端 2iOS端 3微信端 4 web端'
			StringBuffer sb = new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?");
			sb.append("appid="+appId);
			sb.append("&redirect_uri="+URLEncoder.encode(baseUrl + "/pay/wx?notifurl="+notifyurl+"&number="+tprNumber+"&balance="+amount+"&type="+type,"UTF-8"));
			sb.append("&response_type=code");
			sb.append("&scope=snsapi_userinfo");
			sb.append("&state=");
			sb.append("#wechat_redirect");
			String openIdUrl = sb.toString();
//			System.out.println("sb:"+sb.toString());
			result.put("wxPayUrl",openIdUrl);
		}
		result.put("notifurl", notifyurl);
		result.put("tradeNo", tradeNo);
//		logger.error(result.toString());
		return result;
	}
	

}
