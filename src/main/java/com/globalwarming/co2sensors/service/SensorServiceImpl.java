package com.globalwarming.co2sensors.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.globalwarming.co2sensors.dao.AlertRepository;
import com.globalwarming.co2sensors.dao.MesurementRepository;
import com.globalwarming.co2sensors.dao.SensorRepository;
import com.globalwarming.co2sensors.dto.MeasurementDTO;
import com.globalwarming.co2sensors.dto.SensorMetricsDTO;
import com.globalwarming.co2sensors.exception.EntityNotFoundException;
import com.globalwarming.co2sensors.model.Alert;
import com.globalwarming.co2sensors.model.Measurement;
import com.globalwarming.co2sensors.model.Sensor;
import com.globalwarming.co2sensors.model.Status;

@Service
public class SensorServiceImpl implements SensorService {
	private static final Logger LOG = LoggerFactory.getLogger(SensorServiceImpl.class);

	private final SensorRepository sensorRepository;
	private final MesurementRepository mesurementRepository;
	private final AlertRepository alertRepository;

	public SensorServiceImpl(final SensorRepository sensorRepository, final MesurementRepository mesurementRepository,
			final AlertRepository alertRepository) {
		this.sensorRepository = sensorRepository;
		this.mesurementRepository = mesurementRepository;
		this.alertRepository = alertRepository;
	}

	/**
	 * Selects a Sensor by id.
	 *
	 * @param uuid
	 * @return found Sensor
	 * @throws EntityNotFoundException if no Sensor with the given id was found.
	 */
	@Override
	public Sensor findSensor(Long uuid) throws EntityNotFoundException {
		return findSensorChecked(uuid);
	}

	private Sensor findSensorChecked(Long uuid) throws EntityNotFoundException {
		return sensorRepository.findById(uuid)
				.orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + uuid));
	}

	@Override
	@Transactional
	public void collectMesurement(Long uuid, MeasurementDTO dto) throws EntityNotFoundException {
		Sensor sensor = findSensor(uuid);

		Measurement entity = new Measurement();
		entity.setDateCreated(dto.getTime());
		entity.setIsDeleted(false);
		entity.setValue(dto.getCo2());
		entity.setSensor(sensor);
		mesurementRepository.save(entity);

		if (sensor.getSensorStatus() == Status.OK && dto.getCo2() >= 2000) {
			sensor.setSensorStatus(Status.WARN);
			sensorRepository.save(sensor);
		}

		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		List<Measurement> list = mesurementRepository.findTop3BySensor(sensor, sort);

		if (!list.isEmpty()) {
			if (list.stream().allMatch(s -> s.getValue() >= 2000) && sensor.getSensorStatus() != Status.ALERT) {
				sensor.setSensorStatus(Status.ALERT);
				sensorRepository.save(sensor);

				Alert alert = new Alert();
				alert.setSensor(sensor);
				alert.setStartTime(list.get(0).getDateCreated());
				alert.setMesurement1(list.get(0).getValue());
				alert.setMesurement2(list.get(1).getValue());
				alert.setMesurement3(list.get(2).getValue());
				alert.setIsDeleted(false);

				alertRepository.save(alert);
			}

			if (list.stream().allMatch(s -> s.getValue() < 2000) && sensor.getSensorStatus() == Status.ALERT) {
				sensor.setSensorStatus(Status.OK);
				sensorRepository.save(sensor);

				List<Alert> alerts = alertRepository.findTop1BySensor(sensor, Sort.by(Sort.Direction.DESC, "id"));
				if (!alerts.isEmpty()) {
					Alert alert = alerts.get(0);
					alert.setEndTime(dto.getTime());
					alertRepository.save(alert);
				}
			}
		}

	}

	/**
	 * getSensorMetrics
	 */
	@Override
	public SensorMetricsDTO getSensorMetrics(Long uuId) throws EntityNotFoundException {
		SensorMetricsDTO dto = new SensorMetricsDTO();
		dto.setAvgLast30Days(mesurementRepository.findAvgVaueIn30Days(uuId));
		dto.setMaxLast30Days(mesurementRepository.findMaxVaueIn30Days(uuId));
		return dto;
	}

	/**
	 * getSensorAlerts
	 */
	@Override
	public List<Alert> getSensorAlerts(Long uuId) throws EntityNotFoundException {
		return alertRepository.findBySensor(findSensor(uuId));
	}

}
