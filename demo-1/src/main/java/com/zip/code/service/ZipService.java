package com.zip.code.service;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zip.code.model.CityResponseModel;

@Service
public class ZipService {

	@Value("${zip.code.url}")
	private String url;

	@Value("${zip.app.key}")
	private String key;

	@Value("${zip.code.format}")
	private String format;

	@Value("${weather.data.url}")
	private String weather;
	
	@Value("${weather.app.key}")
	private String weatherKey;
	
	@Value("${forecast.data.url}")
	private String forecastUrl;
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	private CityResponseModel cityResponseModel;

	public List<CityResponseModel> getZipCodes(String city, String state) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("Inside getZipcodes methods resttemplate...");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	//	cityResponseModel=new CityResponseModel();
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		String newUrl = url.replaceAll("<api_key>", key).replaceAll("<format>", format).replaceAll("<city>", city)
				.replaceAll("<state>", state);
		System.out.println("The newUrl is:" + newUrl);
		ResponseEntity<Map> resZipcodes =getRestTemplate().exchange(newUrl, HttpMethod.POST, entity, Map.class);
		
		List<String> zips=(List<String>)resZipcodes.getBody().get("zip_codes");
		
		System.out.println("Zipcodes:" + zips);

		System.out.println(zips.get(0));
		 
		Iterator<String> iterator = zips.iterator();
		Map <String,String> weatherMap=new HashMap<String,String>();
		 List <CityResponseModel> response=new ArrayList<>();		 
		while(iterator.hasNext()){
			String zip = iterator.next();
			cityResponseModel=getCityResponseModel();
			cityResponseModel.setCurrentWeather(getWeatherData(zip));
			cityResponseModel.setForecastWeather(getForecastData(zip));
			response.add(cityResponseModel);
		}
		return response;
	}

	public String getWeatherData(String zipCode) {
	//	System.out.println("Inside getWeatherData methods ..*************"+zipCode);
		 String body=null;
		try {
		  HttpHeaders headers = new HttpHeaders();
		  headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON)); HttpEntity
		  <String> entity = new HttpEntity<String>(headers);
		  String newWeatherUrl=weather.replaceAll("<zip>",zipCode).replaceAll("<appid>",weatherKey);
		//  System.out.println("Weater Url:"+newWeatherUrl);
		  ResponseEntity<String> exchange = getRestTemplate().exchange(newWeatherUrl,HttpMethod.GET,entity,String.class);
		  body = exchange.getBody();
		  System.out.println("Weather Data:"+exchange.getBody());
		
		}
		catch(Exception ex) {
			String message = ex.getMessage();
			System.out.println("forecast:"+message);
			body=message.substring(message.indexOf("{"),message.indexOf("}")+1);
			System.out.println("inside weather exception..");
			System.out.println("Weather Exception:"+ex.getMessage());
		}
		return body;
	}
	
	public String getForecastData(String zipCode) {
		System.out.println("Inside getForecastData methods ..*************"+zipCode);
		
		String body=null;
		try {
		  HttpHeaders headers = new HttpHeaders();
		  headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		  HttpEntity <String> entity = new HttpEntity<String>(headers);
		  String newForeCastUrl=forecastUrl.replaceAll("<zip>",zipCode).replaceAll("<appid>",weatherKey);
		  System.out.println("forecast Url:"+newForeCastUrl);
		  ResponseEntity<String> exchange = getRestTemplate().exchange(newForeCastUrl,HttpMethod.GET,entity,String.class);
		  System.out.println("forecast Data:"+exchange.getBody());
		  body = exchange.getBody();
		  System.out.println("forecast Data:"+body);
		}
		catch(Exception ex) {
			String message = ex.getMessage();
			System.out.println("forecast:"+message);
			body=message.substring(message.indexOf("{"),message.indexOf("}")+1);
			//	ex.printStackTrace();
		}
		  return body;
}

	@Bean
	public CityResponseModel getCityResponseModel() {
		return new CityResponseModel();
	}
	
}