package com.globalwarming.co2sensors.dto;

public class SensorMetricsDTO {
	private Double maxLast30Days;
	private Double avgLast30Days;

	public Double getMaxLast30Days() {
		return maxLast30Days;
	}

	public void setMaxLast30Days(Double maxLast30Days) {
		this.maxLast30Days = maxLast30Days;
	}

	public Double getAvgLast30Days() {
		return avgLast30Days;
	}

	public void setAvgLast30Days(Double avgLast30Days) {
		this.avgLast30Days = avgLast30Days;
	}

}
