package com.src.api.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="tb_product_spec")
public class TbProductSpec implements Serializable{
	
	private Long tpsId;
	private Long tpsProductId;
	private Long tpsSkuId;
	private Long tpsSpecId;
	private String tpsSpecValue;
	private String tpsPic;
	private Integer tpsType;
	
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
	
	@Column(name = "tps_sku_id")
	public Long getTpsSkuId() {
		return tpsSkuId;
	}
	public void setTpsSkuId(Long tpsSkuId) {
		this.tpsSkuId = tpsSkuId;
	}
	
	@Column(name = "tps_spec_value")
	public String getTpsSpecValue() {
		return tpsSpecValue;
	}
	public void setTpsSpecValue(String tpsSpecValue) {
		this.tpsSpecValue = tpsSpecValue;
	}
	
	@Column(name = "tps_pic")
	public String getTpsPic() {
		return tpsPic;
	}
	public void setTpsPic(String tpsPic) {
		this.tpsPic = tpsPic;
	}
	
	@Column(name = "tps_type")
	public Integer getTpsType() {
		return tpsType;
	}
	public void setTpsType(Integer tpsType) {
		this.tpsType = tpsType;
	}
	
	@Column(name = "tps_spec_id")
	public Long getTpsSpecId() {
		return tpsSpecId;
	}
	public void setTpsSpecId(Long tpsSpecId) {
		this.tpsSpecId = tpsSpecId;
	}
	public TbProductSpec() {
		super();
	}
	
	

}
