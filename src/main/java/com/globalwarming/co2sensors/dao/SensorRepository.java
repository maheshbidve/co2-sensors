package com.globalwarming.co2sensors.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.globalwarming.co2sensors.model.Sensor;
import com.globalwarming.co2sensors.model.Status;

/**
 * Database Access Object for Sensor table.
 * <p/>
 */
public interface SensorRepository extends CrudRepository<Sensor, Long>
{
    List<Sensor> findByStatus(Status status);
}
