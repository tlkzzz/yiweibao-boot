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
@Table(name="tb_company_customer")
public class TbCompanyCustomer implements Serializable{
	
	private Long tccId;
	private Long tccCompanyId;
	private Long tccCustomerId;
	private Timestamp tccAddtime;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tcc_id", unique = true, nullable = false)
	public Long getTccId() {
		return tccId;
	}
	public void setTccId(Long tccId) {
		this.tccId = tccId;
	}
	
	@Column(name = "tcc_company_id")
	public Long getTccCompanyId() {
		return tccCompanyId;
	}
	public void setTccCompanyId(Long tccCompanyId) {
		this.tccCompanyId = tccCompanyId;
	}
	
	@Column(name = "tcc_customer_id")
	public Long getTccCustomerId() {
		return tccCustomerId;
	}
	public void setTccCustomerId(Long tccCustomerId) {
		this.tccCustomerId = tccCustomerId;
	}
	
	@Column(name = "tcc_addtime")
	public Timestamp getTccAddtime() {
		return tccAddtime;
	}
	public void setTccAddtime(Timestamp tccAddtime) {
		this.tccAddtime = tccAddtime;
	}
	public TbCompanyCustomer() {
		super();
	}
	
	
	

}
