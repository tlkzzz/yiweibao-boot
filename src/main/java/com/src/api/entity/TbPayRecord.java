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
@Table(name="tb_pay_record")
public class TbPayRecord implements Serializable{

	private Long tprId;
	private String tprNumber;
	private Integer tprType;
	private Long tprTarget;
	private Double tprAmount;
	private Timestamp tprAddtime;
	private Timestamp tprPaytime;
	private Integer tprWay;
	private Integer tprStatus;
	
	public TbPayRecord() {}
	
	public TbPayRecord(Long tprId, String tprNumber, Integer tprType, Long tprTarget, Double tprAmount,
			Timestamp tprAddtime, Timestamp tprPaytime, Integer tprWay, Integer tprStatus) {
		super();
		this.tprId = tprId;
		this.tprNumber = tprNumber;
		this.tprType = tprType;
		this.tprTarget = tprTarget;
		this.tprAmount = tprAmount;
		this.tprAddtime = tprAddtime;
		this.tprPaytime = tprPaytime;
		this.tprWay = tprWay;
		this.tprStatus = tprStatus;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tpr_id", unique = true, nullable = false)
	public Long getTprId() {
		return tprId;
	}

	public void setTprId(Long tprId) {
		this.tprId = tprId;
	}

	@Column(name = "tpr_number")
	public String getTprNumber() {
		return tprNumber;
	}

	public void setTprNumber(String tprNumber) {
		this.tprNumber = tprNumber;
	}

	@Column(name = "tpr_type")
	public Integer getTprType() {
		return tprType;
	}

	public void setTprType(Integer tprType) {
		this.tprType = tprType;
	}

	@Column(name = "tpr_target")
	public Long getTprTarget() {
		return tprTarget;
	}

	public void setTprTarget(Long tprTarget) {
		this.tprTarget = tprTarget;
	}

	@Column(name = "tpr_amount")
	public Double getTprAmount() {
		return tprAmount;
	}

	public void setTprAmount(Double tprAmount) {
		this.tprAmount = tprAmount;
	}

	@Column(name = "tpr_addtime")
	public Timestamp getTprAddtime() {
		return tprAddtime;
	}

	public void setTprAddtime(Timestamp tprAddtime) {
		this.tprAddtime = tprAddtime;
	}

	@Column(name = "tpr_paytime")
	public Timestamp getTprPaytime() {
		return tprPaytime;
	}

	public void setTprPaytime(Timestamp tprPaytime) {
		this.tprPaytime = tprPaytime;
	}

	@Column(name = "tpr_way")
	public Integer getTprWay() {
		return tprWay;
	}

	public void setTprWay(Integer tprWay) {
		this.tprWay = tprWay;
	}

	@Column(name = "tpr_status")
	public Integer getTprStatus() {
		return tprStatus;
	}

	public void setTprStatus(Integer tprStatus) {
		this.tprStatus = tprStatus;
	}
	
	
}
