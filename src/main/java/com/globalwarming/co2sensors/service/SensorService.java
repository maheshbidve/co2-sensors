package com.globalwarming.co2sensors.service;

import java.util.List;

import com.globalwarming.co2sensors.dto.MeasurementDTO;
import com.globalwarming.co2sensors.dto.SensorMetricsDTO;
import com.globalwarming.co2sensors.exception.EntityNotFoundException;
import com.globalwarming.co2sensors.model.Alert;
import com.globalwarming.co2sensors.model.Sensor;

public interface SensorService {
	Sensor findSensor(Long uuId) throws EntityNotFoundException;

	void collectMesurement(Long uuid, MeasurementDTO dto) throws EntityNotFoundException;

	SensorMetricsDTO getSensorMetrics(Long uuId) throws EntityNotFoundException;

	List<Alert> getSensorAlerts(Long uuId) throws EntityNotFoundException;
}
