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
@Table(name="tb_order_detail")
public class TbOrderDetail implements Serializable{
	private Long todId;
	private Long todHeadId;
	private Long todProductId;
	private Integer todCount;
	private Double todPrice;
	private Timestamp todAddDate;
	private Timestamp todEndTime;
	private Integer todType;
	private Long todCustomerId;
	private Long todSkuId;
	
	private String todSpecJson;//
	
	private String todProductCode; //产品编码
	private String todCustomerCode;//客户对该机器的编码 备注
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tod_id", unique = true, nullable = false)
	public Long getTodId() {
		return todId;
	}
	public void setTodId(Long todId) {
		this.todId = todId;
	}
	
	@Column(name = "tod_head_id")
	public Long getTodHeadId() {
		return todHeadId;
	}
	public void setTodHeadId(Long todHeadId) {
		this.todHeadId = todHeadId;
	}
	
	@Column(name = "tod_product_id")
	public Long getTodProductId() {
		return todProductId;
	}
	public void setTodProductId(Long todProductId) {
		this.todProductId = todProductId;
	}
	
	@Column(name = "tod_count")
	public Integer getTodCount() {
		return todCount;
	}
	public void setTodCount(Integer todCount) {
		this.todCount = todCount;
	}
	
	@Column(name = "tod_price")
	public Double getTodPrice() {
		return todPrice;
	}
	public void setTodPrice(Double todPrice) {
		this.todPrice = todPrice;
	}
	
	@Column(name = "tod_add_date")
	public Timestamp getTodAddDate() {
		return todAddDate;
	}
	public void setTodAddDate(Timestamp todAddDate) {
		this.todAddDate = todAddDate;
	}
	
	@Column(name = "tod_end_time")
	public Timestamp getTodEndTime() {
		return todEndTime;
	}
	public void setTodEndTime(Timestamp todEndTime) {
		this.todEndTime = todEndTime;
	}
	
	@Column(name = "tod_type")
	public Integer getTodType() {
		return todType;
	}
	public void setTodType(Integer todType) {
		this.todType = todType;
	}
	
	@Column(name = "tod_customer_id")
	public Long getTodCustomerId() {
		return todCustomerId;
	}
	public void setTodCustomerId(Long todCustomerId) {
		this.todCustomerId = todCustomerId;
	}
	
	@Column(name = "tod_sku_id")
	public Long getTodSkuId() {
		return todSkuId;
	}
	public void setTodSkuId(Long todSkuId) {
		this.todSkuId = todSkuId;
	}
	public TbOrderDetail() {
		super();
	}
	
	
	@Column(name = "tod_spec_json")
	public String getTodSpecJson() {
		return todSpecJson;
	}
	public void setTodSpecJson(String todSpecJson) {
		this.todSpecJson = todSpecJson;
	}
	
	
	@Column(name = "tod_product_code")
	public String getTodProductCode() {
		return todProductCode;
	}
	public void setTodProductCode(String todProductCode) {
		this.todProductCode = todProductCode;
	}
	
	
	@Column(name = "tod_customer_code")
	public String getTodCustomerCode() {
		return todCustomerCode;
	}
	public void setTodCustomerCode(String todCustomerCode) {
		this.todCustomerCode = todCustomerCode;
	}
	
	

}
