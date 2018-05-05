package com.src.api.entity;

import javax.persistence.*;

import java.sql.*;
   /**
    * tb_sms_recharge_record 实体类
    * Fri Jan 05 15:03:34 CST 2018 lcx
    */ 


@Entity
@Table(name = "tb_sms_recharge_record")
public class TbSmsRechargeRecord implements java.io.Serializable {
	private Long tsrrId;
	private String tsrrNumber;
	private String tsrrOrderNumber;
	private Integer tsrrStartCount;
	private Integer tsrrCount;
	private Integer tsrrEndCount;
	private Double tsrrAmount;
	private Integer tsrrStatus;
	private Timestamp tsrrAddtime;
	private Long tsrrAccountId;
	private Integer tsrrAccountType;
	private Integer tsrrResource;
	private Integer tsrrPayType;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tsrr_id", unique = true, nullable = false)
	public Long getTsrrId(){
		return tsrrId;
	}
	public void setTsrrId(Long tsrrId){
		this.tsrrId=tsrrId;
	}
	@Column(name = "tsrr_number")
	public String getTsrrNumber(){
		return tsrrNumber;
	}
	public void setTsrrNumber(String tsrrNumber){
		this.tsrrNumber=tsrrNumber;
	}
	@Column(name = "tsrr_order_number")
	public String getTsrrOrderNumber(){
		return tsrrOrderNumber;
	}
	public void setTsrrOrderNumber(String tsrrOrderNumber){
		this.tsrrOrderNumber=tsrrOrderNumber;
	}
	@Column(name = "tsrr_start_count")
	public Integer getTsrrStartCount(){
		return tsrrStartCount;
	}
	public void setTsrrStartCount(Integer tsrrStartCount){
		this.tsrrStartCount=tsrrStartCount;
	}
	@Column(name = "tsrr_count")
	public Integer getTsrrCount(){
		return tsrrCount;
	}
	public void setTsrrCount(Integer tsrrCount){
		this.tsrrCount=tsrrCount;
	}
	@Column(name = "tsrr_end_count")
	public Integer getTsrrEndCount(){
		return tsrrEndCount;
	}
	public void setTsrrEndCount(Integer tsrrEndCount){
		this.tsrrEndCount=tsrrEndCount;
	}
	@Column(name = "tsrr_amount")
	public Double getTsrrAmount(){
		return tsrrAmount;
	}
	public void setTsrrAmount(Double tsrrAmount){
		this.tsrrAmount=tsrrAmount;
	}
	@Column(name = "tsrr_status")
	public Integer getTsrrStatus(){
		return tsrrStatus;
	}
	public void setTsrrStatus(Integer tsrrStatus){
		this.tsrrStatus=tsrrStatus;
	}
	@Column(name = "tsrr_addtime")
	public Timestamp getTsrrAddtime(){
		return tsrrAddtime;
	}
	public void setTsrrAddtime(Timestamp tsrrAddtime){
		this.tsrrAddtime=tsrrAddtime;
	}
	@Column(name = "tsrr_account_id")
	public Long getTsrrAccountId(){
		return tsrrAccountId;
	}
	public void setTsrrAccountId(Long tsrrAccountId){
		this.tsrrAccountId=tsrrAccountId;
	}
	@Column(name = "tsrr_account_type")
	public Integer getTsrrAccountType(){
		return tsrrAccountType;
	}
	public void setTsrrAccountType(Integer tsrrAccountType){
		this.tsrrAccountType=tsrrAccountType;
	}
	@Column(name = "tsrr_resource")
	public Integer getTsrrResource(){
		return tsrrResource;
	}
	public void setTsrrResource(Integer tsrrResource){
		this.tsrrResource=tsrrResource;
	}
	@Column(name = "tsrr_pay_type")
	public Integer getTsrrPayType(){
		return tsrrPayType;
	}
	public void setTsrrPayType(Integer tsrrPayType){
		this.tsrrPayType=tsrrPayType;
	}
	public TbSmsRechargeRecord(Long tsrrId, String tsrrNumber,
			String tsrrOrderNumber, Integer tsrrStartCount, Integer tsrrCount,
			Integer tsrrEndCount, Double tsrrAmount, Integer tsrrStatus,
			Timestamp tsrrAddtime, Long tsrrAccountId, Integer tsrrAccountType,
			Integer tsrrResource, Integer tsrrPayType) {
		super();
		this.tsrrId = tsrrId;
		this.tsrrNumber = tsrrNumber;
		this.tsrrOrderNumber = tsrrOrderNumber;
		this.tsrrStartCount = tsrrStartCount;
		this.tsrrCount = tsrrCount;
		this.tsrrEndCount = tsrrEndCount;
		this.tsrrAmount = tsrrAmount;
		this.tsrrStatus = tsrrStatus;
		this.tsrrAddtime = tsrrAddtime;
		this.tsrrAccountId = tsrrAccountId;
		this.tsrrAccountType = tsrrAccountType;
		this.tsrrResource = tsrrResource;
		this.tsrrPayType = tsrrPayType;
	}
	public TbSmsRechargeRecord() {
		super();
	}
	
}

