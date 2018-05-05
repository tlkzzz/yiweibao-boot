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
@Table(name="tb_logistics")
public class TbLogistics implements Serializable{
	private Long tlId;
	private Long tlOrderId;
	private Timestamp tlAddtime;
	private String tlCompany;
	private String tlNumber;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tl_id", unique = true, nullable = false)
	public Long getTlId() {
		return tlId;
	}
	public void setTlId(Long tlId) {
		this.tlId = tlId;
	}
	
	@Column(name = "tl_order_id")
	public Long getTlOrderId() {
		return tlOrderId;
	}
	public void setTlOrderId(Long tlOrderId) {
		this.tlOrderId = tlOrderId;
	}
	
	@Column(name = "tl_addtime")
	public Timestamp getTlAddtime() {
		return tlAddtime;
	}
	public void setTlAddtime(Timestamp tlAddtime) {
		this.tlAddtime = tlAddtime;
	}
	
	@Column(name = "tl_company")
	public String getTlCompany() {
		return tlCompany;
	}
	public void setTlCompany(String tlCompany) {
		this.tlCompany = tlCompany;
	}
	
	@Column(name = "tl_number")
	public String getTlNumber() {
		return tlNumber;
	}
	public void setTlNumber(String tlNumber) {
		this.tlNumber = tlNumber;
	}
	public TbLogistics() {
		super();
	}
	
	

}
