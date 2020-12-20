package com.globalwarming.co2sensors;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.globalwarming.co2sensors.controller.SensorController;

@SpringBootTest
class Co2SensorsApplicationTests {

	@Autowired
	private SensorController controller;

    @Test
    public void contextLoads()
    {
    	assertThat(controller).isNotNull();
    }

}
