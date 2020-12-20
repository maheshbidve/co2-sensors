package com.globalwarming.co2sensors.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.globalwarming.co2sensors.dto.MeasurementDTO;
import com.globalwarming.co2sensors.dto.SensorMetricsDTO;
import com.globalwarming.co2sensors.exception.EntityNotFoundException;
import com.globalwarming.co2sensors.model.Alert;
import com.globalwarming.co2sensors.model.Sensor;
import com.globalwarming.co2sensors.service.SensorService;

/**
 * All operations with a Sensor will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/api/v1/sensors")
public class SensorController
{

    private final SensorService sensorService;


    /**
     * 
     * @param sensorService
     */
    @Autowired
    public SensorController(final SensorService sensorService)
    {
        this.sensorService = sensorService;
    }


    /**
     * getSensorStatus
     * @param uuid
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("/{uuid}")
    public Sensor getSensorStatus(@PathVariable long uuid) throws EntityNotFoundException
    {
        return sensorService.findSensor(uuid);
    }

    /**
     * collectMesurements
     * 
     * @param uuid
     * @param dTO
     * @return
     * @throws EntityNotFoundException
     */
    @PostMapping("/{uuid}/mesurements")
    public ResponseEntity<String> collectMesurements(@PathVariable long uuid, @RequestBody MeasurementDTO dTO) throws EntityNotFoundException
    {
		sensorService.collectMesurement(uuid, dTO);
		return new ResponseEntity<>("Mesurement saved successfully", HttpStatus.OK);
    }
  
    /**
     * getSensorMetrics
     * @param uuid
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("/{uuid}/metrics")
    public SensorMetricsDTO getSensorMetrics(@PathVariable long uuid) throws EntityNotFoundException
    {
        return sensorService.getSensorMetrics(uuid);
    }
  
    /**
     * getSensorAlerts
     * 
     * @param uuid
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("/{uuid}/alerts")
    public List<Alert> getSensorAlerts(@PathVariable long uuid) throws EntityNotFoundException
    {
        return sensorService.getSensorAlerts(uuid);
    }
}

