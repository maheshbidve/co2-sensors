package com.globalwarming.co2sensors.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.globalwarming.co2sensors.model.Measurement;
import com.globalwarming.co2sensors.model.Sensor;

/**
 * Database Access Object for Measurement table.
 * <p/>
 */
public interface MesurementRepository extends CrudRepository<Measurement, Long> {
	List<Measurement> findTop3BySensor(Sensor sensor, Sort sort);

	@Query(value = "SELECT avg(value) FROM measurement WHERE sensor_id = ?1 AND DATEDIFF(day,date_created,Now()) between 0 and 30", nativeQuery = true)
	Double findAvgVaueIn30Days(Long uuId);

	@Query(value = "SELECT max(value) FROM measurement WHERE sensor_id = ?1 AND DATEDIFF(day,date_created,Now()) between 0 and 30", nativeQuery = true)
	Double findMaxVaueIn30Days(Long uuId);

}
