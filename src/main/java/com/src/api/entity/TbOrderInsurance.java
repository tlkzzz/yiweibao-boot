package com.src.api.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "tb_order_insurance")
public class TbOrderInsurance implements Serializable{
	private Long toiId;
	private Long toiOrderId;
	private Long toiCuetomerId;
	private Long toiCompanyId;
	private Long toiProductId;
	private Long toiFeesId;
	private Timestamp toiOperDate;
	private Integer toiMonths;
	private Date toiStartDate;
	private Date toiEndDate;
	private Long toiAddPerson;
	private Integer toiStatus;
	private Double toiInsurancePeice;
	private Date toiAddtime;
	private String toiNumber;
	private Long toiDetailId; // 订单明细id
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "toi_id", unique = true, nullable = false)
	public Long getToiId() {
		return toiId;
	}
	public void setToiId(Long toiId) {
		this.toiId = toiId;
	}
	@Column(name = "toi_detail_id")
	public Long getToiDetailId() {
		return toiDetailId;
	}
	public void setToiDetailId(Long toiDetailId) {
		this.toiDetailId = toiDetailId;
	}
	@Column(name = "toi_order_id")
	public Long getToiOrderId() {
		return toiOrderId;
	}
	public void setToiOrderId(Long toiOrderId) {
		this.toiOrderId = toiOrderId;
	}
	
	@Column(name = "toi_customer_id")
	public Long getToiCuetomerId() {
		return toiCuetomerId;
	}
	public void setToiCuetomerId(Long toiCuetomerId) {
		this.toiCuetomerId = toiCuetomerId;
	}
	
	
	@Column(name = "toi_company_id")
	public Long getToiCompanyId() {
		return toiCompanyId;
	}
	public void setToiCompanyId(Long toiCompanyId) {
		this.toiCompanyId = toiCompanyId;
	}
	
	@Column(name = "toi_product_id")
	public Long getToiProductId() {
		return toiProductId;
	}
	public void setToiProductId(Long toiProductId) {
		this.toiProductId = toiProductId;
	}
	
	@Column(name = "toi_fees_id")
	public Long getToiFeesId() {
		return toiFeesId;
	}
	public void setToiFeesId(Long toiFeesId) {
		this.toiFeesId = toiFeesId;
	}
	
	@Column(name = "toi_oper_date")
	public Timestamp getToiOperDate() {
		return toiOperDate;
	}
	public void setToiOperDate(Timestamp toiOperDate) {
		this.toiOperDate = toiOperDate;
	}
	
	@Column(name = "toi_months")
	public Integer getToiMonths() {
		return toiMonths;
	}
	public void setToiMonths(Integer toiMonths) {
		this.toiMonths = toiMonths;
	}
	
	@Column(name = "toi_start_date")
	public Date getToiStartDate() {
		return toiStartDate;
	}
	public void setToiStartDate(Date toiStartDate) {
		this.toiStartDate = toiStartDate;
	}
	
	@Column(name = "toi_end_date")
	public Date getToiEndDate() {
		return toiEndDate;
	}
	public void setToiEndDate(Date toiEndDate) {
		this.toiEndDate = toiEndDate;
	}
	
	@Column(name = "toi_add_person")
	public Long getToiAddPerson() {
		return toiAddPerson;
	}
	public void setToiAddPerson(Long toiAddPerson) {
		this.toiAddPerson = toiAddPerson;
	}
	
	
	@Column(name = "toi_status")
	public Integer getToiStatus() {
		return toiStatus;
	}
	public void setToiStatus(Integer toiStatus) {
		this.toiStatus = toiStatus;
	}
	
	@Column(name = "toi_insurance_price")
	public Double getToiInsurancePeice() {
		return toiInsurancePeice;
	}
	public void setToiInsurancePeice(Double toiInsurancePeice) {
		this.toiInsurancePeice = toiInsurancePeice;
	}
	
	@Column(name = "toi_addtime")
	public Date getToiAddtime() {
		return toiAddtime;
	}
	public void setToiAddtime(Date toiAddtime) {
		this.toiAddtime = toiAddtime;
	}
	
	@Column(name = "toi_number")
	public String getToiNumber() {
		return toiNumber;
	}
	public void setToiNumber(String toiNumber) {
		this.toiNumber = toiNumber;
	}
	
	
	public TbOrderInsurance() {
		super();
	}
	
	
	
	
}
