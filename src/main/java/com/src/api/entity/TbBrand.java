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
@Table(name="tb_brand")
public class TbBrand implements Serializable{

	private Long tbId;
	private Long tbCategoryId;
	private String tbName;
	private String tbDesp;
	private Timestamp tbAddtime;
	private Long tbAdduser;
	private Integer tbStatus;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tb_id", unique = true, nullable = false)
	public Long getTbId() {
		return tbId;
	}
	public void setTbId(Long tbId) {
		this.tbId = tbId;
	}
	
	@Column(name = "tb_category_id")
	public Long getTbCategoryId() {
		return tbCategoryId;
	}
	public void setTbCategoryId(Long tbCategoryId) {
		this.tbCategoryId = tbCategoryId;
	}
	
	@Column(name = "tb_name")
	public String getTbName() {
		return tbName;
	}
	public void setTbName(String tbName) {
		this.tbName = tbName;
	}
	
	@Column(name = "tb_desp")
	public String getTbDesp() {
		return tbDesp;
	}
	public void setTbDesp(String tbDesp) {
		this.tbDesp = tbDesp;
	}
	
	@Column(name = "tb_addtime")
	public Timestamp getTbAddtime() {
		return tbAddtime;
	}
	public void setTbAddtime(Timestamp tbAddtime) {
		this.tbAddtime = tbAddtime;
	}
	
	@Column(name = "tb_adduser")
	public Long getTbAdduser() {
		return tbAdduser;
	}
	public void setTbAdduser(Long tbAdduser) {
		this.tbAdduser = tbAdduser;
	}
	
	@Column(name = "tb_status")
	public Integer getTbStatus() {
		return tbStatus;
	}
	public void setTbStatus(Integer tbStatus) {
		this.tbStatus = tbStatus;
	}
	public TbBrand() {
		super();
	}
	
	
	
}
