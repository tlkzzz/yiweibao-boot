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
@Table(name = "tb_service_fees")
public class TbServiceFees implements Serializable{
	
	private Long tsfId;
	private String tsfName;
	private Integer tsfMonth;
	private Double tsfInService;
	private Double tsfOutService;
	private Timestamp tsfAddDate;
	private Integer tsfStatus;
	private Long tsfCompanyId;
	private Long tsfAddPeoper;
	private String tsfDesp;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tsf_id", unique = true, nullable = false)
	public Long getTsfId() {
		return tsfId;
	}
	public void setTsfId(Long tsfId) {
		this.tsfId = tsfId;
	}
	
	@Column(name = "tsf_name")
	public String getTsfName() {
		return tsfName;
	}
	public void setTsfName(String tsfName) {
		this.tsfName = tsfName;
	}
	
	@Column(name = "tsf_month")
	public Integer getTsfMonth() {
		return tsfMonth;
	}
	public void setTsfMonth(Integer tsfMonth) {
		this.tsfMonth = tsfMonth;
	}
	
	@Column(name = "tsf_in_service")
	public Double getTsfInService() {
		return tsfInService;
	}
	public void setTsfInService(Double tsfInService) {
		this.tsfInService = tsfInService;
	}
	
	@Column(name = "tsf_out_service")
	public Double getTsfOutService() {
		return tsfOutService;
	}
	public void setTsfOutService(Double tsfOutService) {
		this.tsfOutService = tsfOutService;
	}
	
	@Column(name = "tsf_add_date")
	public Timestamp getTsfAddDate() {
		return tsfAddDate;
	}
	public void setTsfAddDate(Timestamp tsfAddDate) {
		this.tsfAddDate = tsfAddDate;
	}
	
	@Column(name = "tsf_status")
	public Integer getTsfStatus() {
		return tsfStatus;
	}
	public void setTsfStatus(Integer tsfStatus) {
		this.tsfStatus = tsfStatus;
	}
	
	@Column(name = "tsf_company_id")
	public Long getTsfCompanyId() {
		return tsfCompanyId;
	}
	public void setTsfCompanyId(Long tsfCompanyId) {
		this.tsfCompanyId = tsfCompanyId;
	}
	
	
	@Column(name = "tsf_add_peoper")
	public Long getTsfAddPeoper() {
		return tsfAddPeoper;
	}
	public void setTsfAddPeoper(Long tsfAddPeoper) {
		this.tsfAddPeoper = tsfAddPeoper;
	}
	
	@Column(name = "tsf_desp")
	public String getTsfDesp() {
		return tsfDesp;
	}
	public void setTsfDesp(String tsfDesp) {
		this.tsfDesp = tsfDesp;
	}
	public TbServiceFees() {
		super();
	}
	
	
	
	

}
