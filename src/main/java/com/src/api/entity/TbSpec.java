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
@Table(name="tb_spec")
public class TbSpec implements Serializable{
	
	private Long tsId;
	private Integer tsCategoryId;
	private String tsName;
	private String tsDesp;
	private Integer tsStatus;
	private Timestamp tsAddtime;
	private Long tsAdduser;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ts_id", unique = true, nullable = false)
	public Long getTsId() {
		return tsId;
	}
	public void setTsId(Long tsId) {
		this.tsId = tsId;
	}
	
	@Column(name = "ts_category_id")
	public Integer getTsCategoryId() {
		return tsCategoryId;
	}
	public void setTsCategoryId(Integer tsCategoryId) {
		this.tsCategoryId = tsCategoryId;
	}
	
	@Column(name = "ts_name")
	public String getTsName() {
		return tsName;
	}
	public void setTsName(String tsName) {
		this.tsName = tsName;
	}
	
	@Column(name = "ts_desp")
	public String getTsDesp() {
		return tsDesp;
	}
	public void setTsDesp(String tsDesp) {
		this.tsDesp = tsDesp;
	}
	
	@Column(name = "ts_status")
	public Integer getTsStatus() {
		return tsStatus;
	}
	public void setTsStatus(Integer tsStatus) {
		this.tsStatus = tsStatus;
	}
	
	@Column(name = "ts_addtime")
	public Timestamp getTsAddtime() {
		return tsAddtime;
	}
	public void setTsAddtime(Timestamp tsAddtime) {
		this.tsAddtime = tsAddtime;
	}
	
	@Column(name = "ts_adduser")
	public Long getTsAdduser() {
		return tsAdduser;
	}
	public void setTsAdduser(Long tsAdduser) {
		this.tsAdduser = tsAdduser;
	}
	public TbSpec() {
		super();
	}
	
	

}
