package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.example.demo.client.CityRequestModel;
import com.example.demo.client.SpringWeatherClient;

@SpringBootApplication
public class AssignmentClientApplication {
	
	@Autowired
	private SpringWeatherClient springWeatherClient;
	
	public static void main(String[] args) {
	//	SpringApplication.run(AssignmentClientApplication.class, args);
		System.out.println("args[0]:"+args[0]+"args[1]:"+args[1]);
		CityRequestModel cityRequestModel=new CityRequestModel(args[0],args[1]);
		
		SpringWeatherClient.weatherResult(cityRequestModel);
		
	}
}
