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
@Table(name="tb_product_category")
public class TbProductCategory implements Serializable{

	private Long tpcId;
	private Long tpcCompanyId; 
	private Long tpcParentId;
	private String tpcName;
	private String tpcMobileName;
	private String tpcDesp;
	private Timestamp tpcAddtime;
	private Long tpcAdduser;
	private Integer tpcStatus;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tpc_id", unique = true, nullable = false)
	public Long getTpcId() {
		return tpcId;
	}
	public void setTpcId(Long tpcId) {
		this.tpcId = tpcId;
	}
	
	@Column(name = "tpc_parent_id")
	public Long getTpcParentId() {
		return tpcParentId;
	}
	public void setTpcParentId(Long tpcParentId) {
		this.tpcParentId = tpcParentId;
	}
	
	@Column(name = "tpc_name")
	public String getTpcName() {
		return tpcName;
	}
	public void setTpcName(String tpcName) {
		this.tpcName = tpcName;
	}
	
	
	@Column(name = "tpc_desp")
	public String getTpcDesp() {
		return tpcDesp;
	}
	public void setTpcDesp(String tpcDesp) {
		this.tpcDesp = tpcDesp;
	}
	
	@Column(name = "tpc_addtime")
	public Timestamp getTpcAddtime() {
		return tpcAddtime;
	}
	public void setTpcAddtime(Timestamp tpcAddtime) {
		this.tpcAddtime = tpcAddtime;
	}
	
	@Column(name = "tpc_adduser")
	public Long getTpcAdduser() {
		return tpcAdduser;
	}
	public void setTpcAdduser(Long tpcAdduser) {
		this.tpcAdduser = tpcAdduser;
	}
	
	@Column(name = "tpc_status")
	public Integer getTpcStatus() {
		return tpcStatus;
	}
	public void setTpcStatus(Integer tpcStatus) {
		this.tpcStatus = tpcStatus;
	}
	
	@Column(name = "tpc_company_id")
	public Long getTpcCompanyId() {
		return tpcCompanyId;
	}
	
	@Column(name = "tpc_mobile_name")
	public String getTpcMobileName() {
		return tpcMobileName;
	}
	public void setTpcMobileName(String tpcMobileName) {
		this.tpcMobileName = tpcMobileName;
	}
	public void setTpcCompanyId(Long tpcCompanyId) {
		this.tpcCompanyId = tpcCompanyId;
	}
	
	
	public TbProductCategory() {
		super();
	}
	
	
	
}
