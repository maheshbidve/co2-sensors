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
@Table(name = "measurement")
public class Measurement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private ZonedDateTime dateCreated = ZonedDateTime.now();

	@Column(nullable = false)
	private Integer value;

	@Column(nullable = false)
	private Boolean isDeleted = false;
	

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public ZonedDateTime getDateCreated() {
		return dateCreated;
	}


	public void setDateCreated(ZonedDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}


	public Integer getValue() {
		return value;
	}


	public void setValue(Integer value) {
		this.value = value;
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
