package com.src.api.service.impl;

import java.sql.Timestamp;
import java.util.*;

import com.src.api.dao.TbQrcodeDao;
import com.src.api.entity.*;
import com.src.common.base.entity.PageBean;
import com.src.common.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.src.common.base.dao.BaseDao;
import com.src.common.base.service.impl.BaseServiceImpl;
import com.src.api.dao.ProductSkuDao;
import com.src.api.dao.ProductSpecDao;
import com.src.api.dao.TbProductDao;
import com.src.api.service.TbProductService;

@Service
public class TbProductServiceImpl extends BaseServiceImpl<TbProduct, Long>
implements TbProductService{

	@Autowired
	TbProductDao tbProductDao;
	@Autowired
	ProductSkuDao tbProductSkuDao;
	@Autowired
	ProductSpecDao tbProductSpecDao;
	@Autowired
	TbQrcodeDao tbQrcodeDao;
	@Override
	public BaseDao<TbProduct, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return tbProductDao;
	}

	@Override
	public HashMap<String, Object> findForJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		String order = params.get("order");
		String tpName = params.get("tpName");
		String tcId = params.get("tcId");
		String tpStatus = params.get("tpStatus");
		String AddDate = params.get("AddDate");
		String AddDateEnd = params.get("AddDateEnd");
		String companyId =params.get("companyId");
		
		StringBuffer strSql = new StringBuffer();
		strSql.append("SELECT ");
		strSql.append("tp_id tpId, ");//产品id
		strSql.append("tp_logo tpLogo, ");//产品logo
		strSql.append("tp_name tpName, ");//产品名称
		strSql.append("tp_number tpNumber, ");//商品编码
		strSql.append("tp_price tpPrice, ");//市场价
		strSql.append("tp_store tpStore, ");//库存
		strSql.append("DATE_FORMAT(tp_addtime,'%Y-%m-%d %H:%i:%s') tpAddtime, ");//添加时间
		strSql.append("tp_status tpStatus ");//状态
		strSql.append("FROM tb_product ");//查询表 tb_product
		strSql.append("WHERE tp_status in (1,2) ");//查询条件
		
		if (!StringUtils.isBlank(companyId)) {
			strSql.append("AND tp_company_id = "+companyId+" ");
		}
		
		if (!StringUtils.isBlank(tpName)) {
			strSql.append("AND tp_name LIKE '%"+tpName+"%' ");
		}
		
		if (!StringUtils.isBlank(tpStatus)) {
			strSql.append("AND tp_status like '%"+tpStatus+"%' ");
		}
		
		if (!StringUtils.isBlank(tcId)) {
			strSql.append("AND tp_pc_id = "+tcId+" ");
		}
		if (AddDate != null && !AddDate.equalsIgnoreCase("")) {
			strSql.append("AND DATE_FORMAT(tp_addtime,'%Y-%m-%d') >= DATE_FORMAT('"+AddDate+"','%Y-%m-%d') ");
		} 
		
		if (AddDateEnd!= null && !AddDateEnd.equalsIgnoreCase("")) {
			strSql.append("AND DATE_FORMAT(tp_addtime,'%Y-%m-%d') <= DATE_FORMAT('"+AddDateEnd+"','%Y-%m-%d') ");
		}
		
		if (!StringUtils.isBlank(order)) {
			strSql.append("ORDER BY tp_addtime "+order+" ");
		} else {
			strSql.append("ORDER BY tp_addtime DESC ");
		}
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbProductDao.searchForMap(strSql.toString(), null);
			for (Map<String, Object> map : list) {
				map.put("tpPrice", String.format("%.2f",map.get("tpPrice")));
			}
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbProductDao.searchForMap(strSql.toString(), null, pageBean);
			for (Map<String, Object> map : pageBean.getList()) {
				map.put("tpPrice", String.format("%.2f",map.get("tpPrice")));
			}
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public Map<String, Object> findByCompanyId(Long tcId) {
		StringBuffer strSql = new StringBuffer();
		strSql.append("SELECT ");
		strSql.append("tp_id ,tp_logo ,tp_number ,tp_price ,tp_name ,tp_status , ");
		strSql.append("tp_store, (SELECT GROUP_CONCAT(tsf_name) ");
		strSql.append("FROM tb_service_fees ");
		strSql.append("WHERE FIND_IN_SET(tsf_id,tp_fees_ids)) fees ");
		strSql.append("FROM tb_product WHERE tp_status<>0 AND tp_company_id = "+tcId+" ");
		strSql.append("ORDER BY tp_addtime DESC ");

		PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(1, 10);
		pageBean = tbProductDao.searchForMap(strSql.toString(), null, pageBean);
		Map<String, Object> map = new HashMap<>();
		map.put("list", pageBean.getList());
		map.put("count", pageBean.getRowCount());
		map.put("rows", pageBean.getPageCount());
		return map;
	}

	@Override
	@Transactional
	public void addProductAndSpec(TbProduct tbProduct, List<TbProductSku> tbProductSkuList,
			List<List<TbProductSpec>> tbProductSpecListList) {
		TbProduct tbp=	tbProductDao.save(tbProduct);
		//生成二维码编码
		TbQrcode qrcode = new TbQrcode();
		//生成6位随机数
		int uuid=(int)((Math.random()*9+1)*100000);
		qrcode.setStatus(1);
		qrcode.setCompanyId(tbp.getTpCompanyId());
		qrcode.setCreateDate(new Timestamp(new Date().getTime()));
//		qrcode.setCreatePerson("admin");
//		qrcode.setServicePointsId(Long.valueOf(points));
		qrcode.setSourcetype("product");
		qrcode.setSourceid(tbp.getTpId());
		qrcode.setQrcodenum("SP-"+uuid);
		tbQrcodeDao.save(qrcode);


		
		TbProductSku  tbProductSku = null;
		List<TbProductSpec> tbProductSpecList = null;
		for (int i = 0; i < tbProductSkuList.size(); i++) {
			tbProductSku = tbProductSkuList.get(i);
			tbProductSku.setTpsProductId(tbProduct.getTpId());
			tbProductSkuDao.save(tbProductSku);
			
			tbProductSpecList = tbProductSpecListList.get(i);
			for (TbProductSpec tbProductSpec : tbProductSpecList) {
				tbProductSpec.setTpsProductId(tbProduct.getTpId());
				tbProductSpec.setTpsSkuId(tbProductSku.getTpsId());
				tbProductSpecDao.save(tbProductSpec);
			}
		}
		
	}

	@Override
	public List<Map<String, Object>> getById(Long tpId) {
		String sql = "SELECT *,"
				+ "(SELECT tpc_parent_id FROM tb_product_category WHERE tpc_id = tp_pc_id) tpPcId2,"
				+ "(SELECT tpc_parent_id FROM tb_product_category WHERE tpc_id = tpPcId2) tpPcId1 "
				+ "FROM tb_product WHERE tp_id = "+tpId;
		return tbProductDao.searchForMap(sql, null);
	}

	@Override
	@Transactional
	public void updateProductAndSpec(TbProduct tbProduct, List<Long> lList, List<TbProductSku> tbProductSkuList,
			List<List<TbProductSpec>> tbProductSpecListList) {
		tbProductDao.update(tbProduct);
		
		if(lList!=null&&lList.size()>0){
			for (Long l : lList) {
				tbProductSkuDao.del(l);
				String sql = "DELETE FROM tb_product_spec where tps_sku_id = "+l+" and tps_product_id = "+tbProduct.getTpId();
				tbProductSpecDao.execute(sql);
			}
		}
		
		if(tbProductSkuList!=null){
			for (int i = 0; i < tbProductSkuList.size(); i++) {
				TbProductSku tbProductSku = tbProductSkuList.get(i);
				if(tbProductSku.getTpsId()==null){
					tbProductSkuDao.save(tbProductSku);
					if(tbProductSpecListList!=null&&tbProductSpecListList.size()>0){
						for (TbProductSpec tbProductSpec : tbProductSpecListList.get(i)) {
							if(tbProductSpec.getTpsSkuId()==null){
								tbProductSpec.setTpsSkuId(tbProductSku.getTpsId());
								tbProductSpecDao.save(tbProductSpec);
							}
							
						}
					}
				}else{
					tbProductSkuDao.update(tbProductSku);
				}
			}
		}
		
	}

	@Override
	public Map<String, Object> getByParam(Map<String, String> param) {
		Map<String, Object> json = new HashMap<String, Object>();
		String companyId = param.get("companyId");
		String tpName = param.get("tpName");
		String tpStatus = param.get("tpStatus");
		int page = param.get("page") == null ? 0:Integer.parseInt(param.get("page"));
		int pageSize = param.get("pageSize") == null ? 0:Integer.parseInt(param.get("pageSize"));
		
		StringBuffer strSql = new StringBuffer();
		strSql.append("SELECT ");
		strSql.append("tp_id ,tp_logo ,tp_number,tc_name AS tpcName, ");
		strSql.append("(SELECT count(0) FROM tb_order_head, tb_order_detail ");
		strSql.append("WHERE toh_id =tod_head_id AND tod_product_id = tp_id ");
		strSql.append("AND toh_status in (1,2,3,6,10) AND tod_type = 1) orderCount, ");
		strSql.append("FORMAT(tp_price,2) tp_price, tp_name, tp_status, tp_store,");
		strSql.append("(SELECT GROUP_CONCAT(tsf_name) FROM tb_service_fees WHERE FIND_IN_SET(tsf_id,tp_fees_ids)) fees ");
		strSql.append("FROM tb_product LEFT JOIN tb_category ON tc_id=tp_category_id WHERE tp_status<>0 AND tp_company_id = "+companyId+" ");
		
		if(!StringUtils.isBlank(tpName)){
			strSql.append("AND tp_name LIKE '%"+tpName+"%' ");
		}
		if(!StringUtils.isBlank(tpStatus)){
			strSql.append("AND tp_status = "+tpStatus+" ");
		}
		if(!StringUtils.isBlank(param.get("tpcId"))){
			strSql.append(" and tp_category_id =  "+param.get("tpcId")+" ");
		}

		strSql.append("ORDER BY tp_addtime DESC ");

//		PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(nowPage, 10);
//		pageBean = tbProductDao.searchForMap(strSql.toString(), null, pageBean);
//		Map<String, Object> map = new HashMap<>();
//		map.put("list", pageBean.getList());//数据列表
//		map.put("rows", pageBean.getPageCount());//总页数
//		map.put("rowcount", pageBean.getRowCount());//符合条件的记录数
//		map.put("nowPage", nowPage);//当前页
//		return map;
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbProductDao.searchForMap(strSql.toString(), null);
			json.put("pageCount", StringUtil.getPageCount(list.size(),pageSize));
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbProductDao.searchForMap(strSql.toString(), null, pageBean);
			json.put("pageCount", StringUtil.getPageCount(pageBean.getRowCount(),pageSize));
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}


	}
	
	@Override
	public  List<TbProduct>  getProductListByName_ajax(String product_name,Long id){
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(" select * from tb_product  ");
		sbSql.append("  where tp_status = 1 and tp_name like '%" + product_name + "%' AND tp_type = 1 ");//2017-11-21 只获取产品1:产品 2:配件
		sbSql.append(" AND tp_company_id="+id);
		return tbProductDao.search(sbSql.toString(),null);
	}
	
	@Override
	public boolean findProductByNumberBoolean(String tpNumber, String tpId) {
		if(tpNumber.equals("")/*||tpNumber.length()!=12*/){//2016-7-13暂取消编号长度限制
			return false;
		}
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT * FROM tb_product p WHERE p.tp_number = ?");//查询条件商品货号
		sbSql.append(" AND p.tp_status <> 0 ");//查询状态不为0的商品
		List<Object> values = new ArrayList<Object>();
		values.add(tpNumber);
		if(!StringUtils.isBlank(tpId)){
			sbSql.append(" AND p.tp_id <> ?");
			values.add(tpId);
		}
		List<TbProduct> list = tbProductDao.search(sbSql.toString(), values);
		if(list.size()>0){
			return false;
		}
		return true;
	}
	@Override
	public TbProduct findProductByNumber(String tpNumber, String tpId) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT * FROM tb_product p WHERE p.tp_number = ?");//查询条件商品货号
		sbSql.append(" AND p.tp_status <> 0 ");//查询状态不为0的商品
		List<Object> values = new ArrayList<Object>();
		values.add(tpNumber);
		if(!StringUtils.isBlank(tpId)){
			sbSql.append(" AND p.tp_id <> ?");
			values.add(tpId);
		}
		List<TbProduct> list = tbProductDao.search(sbSql.toString(), values);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}
	
	@Override
	public List<TbProduct> find(TbProduct tbProduct) {
		return tbProductDao.search(tbProduct);
	}
}
