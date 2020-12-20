package com.globalwarming.co2sensors;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZonedDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.globalwarming.co2sensors.dto.MeasurementDTO;
import com.globalwarming.co2sensors.dto.SensorMetricsDTO;
import com.globalwarming.co2sensors.model.Alert;
import com.globalwarming.co2sensors.model.Sensor;
import com.globalwarming.co2sensors.model.Status;
import com.globalwarming.co2sensors.service.SensorService;

@SpringBootTest
class Co2SensorsTests {

	@Autowired
	SensorService sensorService;

	@Test
	public void postMeasurement() throws Exception {
		long uuId = 1;
		MeasurementDTO dto = new MeasurementDTO(2001, ZonedDateTime.now());
		sensorService.collectMesurement(uuId, dto);
		
		assertThat(sensorService.findSensor((long) uuId).getSensorStatus()).isEqualTo(Status.WARN);
	}

	@Test
	public void testSensorStatus() throws Exception {
		long uuId = 1;
		Sensor sensor = sensorService.findSensor(uuId);
		assertThat(sensor).isNotNull();
		assertThat(sensor.getName()).isEqualTo("sensor1");
	}
	
	@Test
	public void testAlert() throws Exception {
		long uuId = 1;
		MeasurementDTO dto = new MeasurementDTO(2001, ZonedDateTime.now());
		sensorService.collectMesurement(uuId, dto);
		
		dto = new MeasurementDTO(2002, ZonedDateTime.now());
		sensorService.collectMesurement(uuId, dto);
		
		dto = new MeasurementDTO(2003, ZonedDateTime.now());
		sensorService.collectMesurement(uuId, dto);
		
		assertThat(sensorService.findSensor((long) uuId).getSensorStatus()).isEqualTo(Status.ALERT);
	}
	
	@Test
	public void testOK() throws Exception {
		long uuId = 1;
		MeasurementDTO dto = new MeasurementDTO(450, ZonedDateTime.now());
		sensorService.collectMesurement(uuId, dto);
		
		dto = new MeasurementDTO(451, ZonedDateTime.now());
		sensorService.collectMesurement(uuId, dto);
		
		dto = new MeasurementDTO(451, ZonedDateTime.now());
		sensorService.collectMesurement(uuId, dto);
		
		assertThat(sensorService.findSensor((long) uuId).getSensorStatus()).isEqualTo(Status.OK);
	}
	
	@Test
	public void testMetrics() throws Exception {
		long uuId = 1;
		SensorMetricsDTO dto = sensorService.getSensorMetrics(uuId);
		assertThat(dto.getAvgLast30Days()).isEqualTo(1226.0);
		assertThat(dto.getMaxLast30Days()).isEqualTo(2003.0);
		
	}
	
	@Test
	public void testAlerts() throws Exception {
		long uuId = 1;
		List<Alert> alerts = sensorService.getSensorAlerts(uuId);
		assertThat(alerts.size()).isEqualTo(1);
		assertThat(alerts.get(0).getSensor().getName()).isEqualTo("sensor1");
	}
}
