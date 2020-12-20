package com.globalwarming.co2sensors.model;

import java.time.ZonedDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "alert")
public class Alert {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private ZonedDateTime startTime = ZonedDateTime.now();
	
	@Column(nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private ZonedDateTime endTime = ZonedDateTime.now();

	@Column(nullable = false)
	private Boolean isDeleted = false;
	
	@Column(nullable = false)
	private Integer mesurement1;
	
	@Column(nullable = false)
	private Integer mesurement2;

	@Column(nullable = false)
	private Integer mesurement3;
	
	public ZonedDateTime getEndTime() {
		return endTime;
	}


	public void setEndTime(ZonedDateTime endTime) {
		this.endTime = endTime;
	}


	public Integer getMesurement1() {
		return mesurement1;
	}


	public void setMesurement1(Integer mesurement1) {
		this.mesurement1 = mesurement1;
	}


	public Integer getMesurement2() {
		return mesurement2;
	}


	public void setMesurement2(Integer mesurement2) {
		this.mesurement2 = mesurement2;
	}


	public Integer getMesurement3() {
		return mesurement3;
	}


	public void setMesurement3(Integer mesurement3) {
		this.mesurement3 = mesurement3;
	}
	
    public Long getId() {
		return id;
	}

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;

	public void setId(Long id) {
		this.id = id;
	}


	public ZonedDateTime getStartTime() {
		return startTime;
	}


	public void setStartTime(ZonedDateTime startTime) {
		this.startTime = startTime;
	}


	public Boolean getIsDeleted() {
		return isDeleted;
	}


	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}


	public Sensor getSensor() {
		return sensor;
	}


	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
}
