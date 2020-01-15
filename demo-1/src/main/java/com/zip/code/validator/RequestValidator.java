package com.zip.code.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.HandlerInterceptor;

import com.zip.code.model.CityRequestModel;
import com.zip.code.model.CityResponseModel;

public class RequestValidator {
	
	public static ResponseEntity<List<CityResponseModel>> validate(CityRequestModel city) {
		
		CityResponseModel cityResponseModel=new CityResponseModel();
		List<CityResponseModel> response=new ArrayList<>();
		if(city==null) {
			cityResponseModel.setErrorMessage("Request is empty");
			response.add(cityResponseModel);
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
		else if(("".equalsIgnoreCase(city.getCity()) || (city.getCity())==null) && (!"".equalsIgnoreCase(city.getState()) || (city.getState()!=null)))
		{
			cityResponseModel.setErrorMessage("City is Empty");
			 response.add(cityResponseModel);
			 return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
		else if(("".equalsIgnoreCase(city.getState()) || (city.getState())==null) && (!"".equalsIgnoreCase(city.getCity()) || (city.getCity()!=null)))
		{
			cityResponseModel.setErrorMessage("State is Empty");			
			 response.add(cityResponseModel);
			 return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
		else if(city.getState()!=null && city.getState().length()!=2)
		{
			cityResponseModel.setErrorMessage("state should be exactly 2 charaters in length");
			 response.add(cityResponseModel);
			 return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
		else if(city.getCity()!=null && city.getCity().length()>1024)
		{
			cityResponseModel.setErrorMessage("City length should be exactly 2 charaters in length");
			 response.add(cityResponseModel);
			 return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
		else if(!(Pattern.matches("[A-Za-z\\s]*", city.getCity()))) {
			cityResponseModel.setErrorMessage("City should be only characters");
			 response.add(cityResponseModel);
			 return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
		return null;
	}
	
}
