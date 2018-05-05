package com.src.api.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_spec_value")
public class TbSpecValue implements Serializable{
	
	private Long tsvId;
	private Long tsvSpecId;
	private String tsvValue;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tsv_id", unique = true, nullable = false)
	public Long getTsvId() {
		return tsvId;
	}
	public void setTsvId(Long tsvId) {
		this.tsvId = tsvId;
	}
	
	@Column(name = "tsv_spec_id")
	public Long getTsvSpecId() {
		return tsvSpecId;
	}
	public void setTsvSpecId(Long tsvSpecId) {
		this.tsvSpecId = tsvSpecId;
	}
	
	@Column(name = "tsv_value")
	public String getTsvValue() {
		return tsvValue;
	}
	public void setTsvValue(String tsvValue) {
		this.tsvValue = tsvValue;
	}
	public TbSpecValue() {
		super();
	}
	

	
	
}
