package com.src.api.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 区域详情
 * @author Administrator
 *
 */

@Entity
@Table(name="tb_region_detail")
public class TbRegionDetail implements Serializable{
	
	private Long trdId;
	private Long trdRegionId;
	private Integer trdProviceId;
	private Integer trdCityId;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "trd_id", unique = true, nullable = false)
	public Long getTrdId() {
		return trdId;
	}
	public void setTrdId(Long trdId) {
		this.trdId = trdId;
	}
	
	@Column(name = "trd_region_id")
	public Long getTrdRegionId() {
		return trdRegionId;
	}
	public void setTrdRegionId(Long trdRegionId) {
		this.trdRegionId = trdRegionId;
	}
	
	
	@Column(name = "trd_provice_id")
	public Integer getTrdProviceId() {
		return trdProviceId;
	}
	public void setTrdProviceId(Integer trdProviceId) {
		this.trdProviceId = trdProviceId;
	}
	
	@Column(name = "trd_city_id")
	public Integer getTrdCityId() {
		return trdCityId;
	}
	public void setTrdCityId(Integer trdCityId) {
		this.trdCityId = trdCityId;
	}
	public TbRegionDetail() {
		super();
	}
	
	
	
	
	

}
