package com.src.api.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="tb_product")
public class TbProduct implements Serializable{
	
	private Long tpId;
	private Long tpCompanyId;
	private String tpName;
	private String tpSimpleName;
	private Long tpPcId;
	private Long tpCategoryId;
	private Long tpBrandId;
	private Double tpPrice;
	private String tpDesp;
	private String tpLogo;
	private String tpPics;
	private Integer tpStatus;
	private Timestamp tpAddtime;
	private Integer tpWeight;
	private Integer tpType;
	private Integer tpSalesCount;
	private Integer tpWarranty;
	private Integer tpStore;
	private String  tpNumber;
	private Integer tpFreightType;
	private Double tpFreight;
	private String tpFeesIds;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tp_id", unique = true, nullable = false)
	public Long getTpId() {
		return tpId;
	}
	public void setTpId(Long tpId) {
		this.tpId = tpId;
	}
	
	@Column(name = "tp_company_id")
	public Long getTpCompanyId() {
		return tpCompanyId;
	}
	public void setTpCompanyId(Long tpCompanyId) {
		this.tpCompanyId = tpCompanyId;
	}
	
	@Column(name = "tp_name")
	public String getTpName() {
		return tpName;
	}
	public void setTpName(String tpName) {
		this.tpName = tpName;
	}
	
	@Column(name = "tp_simple_name")
	public String getTpSimpleName() {
		return tpSimpleName;
	}
	public void setTpSimpleName(String tpSimpleName) {
		this.tpSimpleName = tpSimpleName;
	}
	
	@Column(name = "tp_category_id")
	public Long getTpCategoryId() {
		return tpCategoryId;
	}
	public void setTpCategoryId(Long tpCategoryId) {
		this.tpCategoryId = tpCategoryId;
	}
	
	@Column(name = "tp_brand_id")
	public Long getTpBrandId() {
		return tpBrandId;
	}
	public void setTpBrandId(Long tpBrandId) {
		this.tpBrandId = tpBrandId;
	}
	
	@Column(name = "tp_price")
	public Double getTpPrice() {
		return tpPrice;
	}
	public void setTpPrice(Double tpPrice) {
		this.tpPrice = tpPrice;
	}
	
	@Column(name = "tp_desp")
	public String getTpDesp() {
		return tpDesp;
	}
	public void setTpDesp(String tpDesp) {
		this.tpDesp = tpDesp;
	}
	
	@Column(name = "tp_logo")
	public String getTpLogo() {
		return tpLogo;
	}
	public void setTpLogo(String tpLogo) {
		this.tpLogo = tpLogo;
	}
	
	@Column(name = "tp_pics")
	public String getTpPics() {
		return tpPics;
	}
	public void setTpPics(String tpPics) {
		this.tpPics = tpPics;
	}
	
	@Column(name = "tp_status")
	public Integer getTpStatus() {
		return tpStatus;
	}
	public void setTpStatus(Integer tpStatus) {
		this.tpStatus = tpStatus;
	}
	
	@Column(name = "tp_addtime")
	public Timestamp getTpAddtime() {
		return tpAddtime;
	}
	public void setTpAddtime(Timestamp tpAddtime) {
		this.tpAddtime = tpAddtime;
	}
	
	@Column(name = "tp_weight")
	public Integer getTpWeight() {
		return tpWeight;
	}
	public void setTpWeight(Integer tpWeight) {
		this.tpWeight = tpWeight;
	}
	
	@Column(name = "tp_type")
	public Integer getTpType() {
		return tpType;
	}
	public void setTpType(Integer tpType) {
		this.tpType = tpType;
	}
	
	
	@Column(name = "tp_sales_count")
	public Integer getTpSalesCount() {
		return tpSalesCount;
	}
	public void setTpSalesCount(Integer tpSalesCount) {
		this.tpSalesCount = tpSalesCount;
	}
	
	@Column(name = "tp_warranty")
	public Integer getTpWarranty() {
		return tpWarranty;
	}
	public void setTpWarranty(Integer tpWarranty) {
		this.tpWarranty = tpWarranty;
	}
	
	
	@Column(name = "tp_store")
	public Integer getTpStore() {
		return tpStore;
	}
	public void setTpStore(Integer tpStore) {
		this.tpStore = tpStore;
	}
	
	@Column(name = "tp_number")
	public String getTpNumber() {
		return tpNumber;
	}
	public void setTpNumber(String tpNumber) {
		this.tpNumber = tpNumber;
	}
	
	@Column(name = "tp_pc_id")
	public Long getTpPcId() {
		return tpPcId;
	}
	public void setTpPcId(Long tpPcId) {
		this.tpPcId = tpPcId;
	}
	
	
	@Column(name = "tp_freight_type")
	public Integer getTpFreightType() {
		return tpFreightType;
	}
	public void setTpFreightType(Integer tpFreightType) {
		this.tpFreightType = tpFreightType;
	}
	
	@Column(name = "tp_freight")
	public Double getTpFreight() {
		return tpFreight;
	}
	public void setTpFreight(Double tpFreight) {
		this.tpFreight = tpFreight;
	}
	
	@Column(name = "tp_fees_ids")
	public String getTpFeesIds() {
		return tpFeesIds;
	}
	public void setTpFeesIds(String tpFeesIds) {
		this.tpFeesIds = tpFeesIds;
	}
	public TbProduct() {
		super();
	}
	
	
	
	
	

}
