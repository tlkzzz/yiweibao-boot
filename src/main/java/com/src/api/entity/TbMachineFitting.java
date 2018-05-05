package com.src.api.entity;
// Generated 2015-8-31 15:24:09 by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbMachineFitting generated by hbm2java
 */
@Entity
@Table(name = "tb_machine_fitting")
public class TbMachineFitting implements java.io.Serializable {

	private Long tmfId;
	private Long tmfMachineId;
	private String tmfFittingId;

	public TbMachineFitting() {
	}

	public TbMachineFitting(Long tmfMachineId, String tmfFittingId) {
		this.tmfMachineId = tmfMachineId;
		this.tmfFittingId = tmfFittingId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "tmf_id", unique = true, nullable = false)
	public Long getTmfId() {
		return this.tmfId;
	}

	public void setTmfId(Long tmfId) {
		this.tmfId = tmfId;
	}

	@Column(name = "tmf_machine_id", nullable = false)
	public Long getTmfMachineId() {
		return this.tmfMachineId;
	}

	public void setTmfMachineId(Long tmfMachineId) {
		this.tmfMachineId = tmfMachineId;
	}

	@Column(name = "tmf_fitting_id", nullable = false, length = 100)
	public String getTmfFittingId() {
		return this.tmfFittingId;
	}

	public void setTmfFittingId(String tmfFittingId) {
		this.tmfFittingId = tmfFittingId;
	}

}
