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
@Table(name="tb_category")
public class TbCategory implements Serializable{
	
	private Long tcId;
	private Long tcParentId;
	private String tcName;
	private String tcDesp;
	private Timestamp tcAddtime;
	private Long tcAdduser;
	private Integer tcStatus;
	private Integer tcType;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tc_id", unique = true, nullable = false)
	public Long getTcId() {
		return tcId;
	}
	public void setTcId(Long tcId) {
		this.tcId = tcId;
	}
	
	@Column(name = "tc_parent_id")
	public Long getTcParentId() {
		return tcParentId;
	}
	public void setTcParentId(Long tcParentId) {
		this.tcParentId = tcParentId;
	}
	
	@Column(name = "tc_name")
	public String getTcName() {
		return tcName;
	}
	public void setTcName(String tcName) {
		this.tcName = tcName;
	}
	
	@Column(name = "tc_desp")
	public String getTcDesp() {
		return tcDesp;
	}
	public void setTcDesp(String tcDesp) {
		this.tcDesp = tcDesp;
	}
	
	@Column(name = "tc_addtime")
	public Timestamp getTcAddtime() {
		return tcAddtime;
	}
	public void setTcAddtime(Timestamp tcAddtime) {
		this.tcAddtime = tcAddtime;
	}
	
	@Column(name = "tc_adduser")
	public Long getTcAdduser() {
		return tcAdduser;
	}
	public void setTcAdduser(Long tcAdduser) {
		this.tcAdduser = tcAdduser;
	}
	
	@Column(name = "tc_status")
	public Integer getTcStatus() {
		return tcStatus;
	}
	public void setTcStatus(Integer tcStatus) {
		this.tcStatus = tcStatus;
	}
	
	@Column(name = "tc_type")
	public Integer getTcType() {
		return tcType;
	}
	public void setTcType(Integer tcType) {
		this.tcType = tcType;
	}
	public TbCategory() {
		super();
	}
	
	

}
