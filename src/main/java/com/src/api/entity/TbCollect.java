package com.src.api.entity;
// Generated 2015-8-31 15:24:09 by Hibernate Tools 4.3.1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TbCollect generated by hbm2java
 */
@Entity
@Table(name = "tb_collect")
public class TbCollect implements java.io.Serializable {

	private Long tcId;
	private Long tcFittingId;
	private Long tcCustomerId;
	private Date tcAddDate;

	public TbCollect() {
	}

	public TbCollect(Long tcFittingId, Long tcCustomerId, Date tcAddDate) {
		this.tcFittingId = tcFittingId;
		this.tcCustomerId = tcCustomerId;
		this.tcAddDate = tcAddDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "tc_id", unique = true, nullable = false)
	public Long getTcId() {
		return this.tcId;
	}

	public void setTcId(Long tcId) {
		this.tcId = tcId;
	}

	@Column(name = "tc_fitting_id", nullable = false)
	public Long getTcFittingId() {
		return this.tcFittingId;
	}

	public void setTcFittingId(Long tcFittingId) {
		this.tcFittingId = tcFittingId;
	}

	@Column(name = "tc_customer_id", nullable = false)
	public Long getTcCustomerId() {
		return this.tcCustomerId;
	}

	public void setTcCustomerId(Long tcCustomerId) {
		this.tcCustomerId = tcCustomerId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tc_add_date", nullable = false, length = 19)
	public Date getTcAddDate() {
		return this.tcAddDate;
	}

	public void setTcAddDate(Date tcAddDate) {
		this.tcAddDate = tcAddDate;
	}

}