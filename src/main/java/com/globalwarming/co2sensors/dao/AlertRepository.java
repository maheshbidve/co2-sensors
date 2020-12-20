package com.globalwarming.co2sensors.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.globalwarming.co2sensors.model.Alert;
import com.globalwarming.co2sensors.model.Sensor;

/**
 * Database Access Object for Alert table.
 * <p/>
 */
public interface AlertRepository extends CrudRepository<Alert, Long> {
	List<Alert> findTop1BySensor(Sensor sensor, Sort sort);

	List<Alert> findBySensor(Sensor sensor);
}
