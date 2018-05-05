package com.src.api.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="tb_product_sku")
public class TbProductSku implements Serializable{
	private Long tpsId;
	private Long tpsProductId;
	private Double tpsPrice;
	private Integer tpsStore;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tps_id", unique = true, nullable = false)
	public Long getTpsId() {
		return tpsId;
	}
	public void setTpsId(Long tpsId) {
		this.tpsId = tpsId;
	}
	
	@Column(name = "tps_product_id")
	public Long getTpsProductId() {
		return tpsProductId;
	}
	public void setTpsProductId(Long tpsProductId) {
		this.tpsProductId = tpsProductId;
	}
	
	@Column(name = "tps_price")
	public Double getTpsPrice() {
		return tpsPrice;
	}
	public void setTpsPrice(Double tpsPrice) {
		this.tpsPrice = tpsPrice;
	}
	
	@Column(name = "tps_store")
	public Integer getTpsStore() {
		return tpsStore;
	}
	public void setTpsStore(Integer tpsStore) {
		this.tpsStore = tpsStore;
	}
	public TbProductSku() {
		super();
	}
	
	

}
