package com.example.demo.client;

import java.net.URL;
import java.util.List;
import java.util.concurrent.Exchanger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SpringWeatherClient {

	@Autowired
	private RestTemplate restTemplate;

	private static final String url="http://localhost:3000/weather";
	
	@Bean
	private static RestTemplate getRestTemplate() {
		 return new RestTemplate();
	}
	
	public static ResponseEntity<List<CityResponseModel>> weatherResult(CityRequestModel cityRequestModel){
		System.out.println("inside weatherResult method...");
		ParameterizedTypeReference<List<CityResponseModel>> typeRef=new ParameterizedTypeReference<List<CityResponseModel>>(){};
//		RequestEntity<CityRequestModel>=new Request
		ResponseEntity<List<CityResponseModel>> response = getRestTemplate().exchange(url,HttpMethod.POST,new HttpEntity<CityRequestModel>(cityRequestModel), typeRef);
		
		System.out.println("Response is:"+response);
		return response;

	}
}
