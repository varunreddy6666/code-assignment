package com.zip.code;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.zip.code.model.CityRequestModel;
import com.zip.code.model.CityResponseModel;
import com.zip.code.service.ZipService;
import com.zip.code.validator.RequestValidator;

@RestController
public class ZipController {

	@Autowired
	private ZipService zipService;

	@RequestMapping("/weather")
	public ResponseEntity<List<CityResponseModel>> getListOfZipCodes(@RequestBody CityRequestModel request) {
		System.out.println("Inside getListOfZipCodes method...");
		List<CityResponseModel> response=new ArrayList<>();
		
		ResponseEntity<List<CityResponseModel>> validate = RequestValidator.validate(request);
		if(validate==null) {
			try {
				System.out.println("City:" + request.getCity() + "\n State:" + request.getState());
				response = zipService.getZipCodes(request.getCity(), request.getState());

			} catch (Exception ex) {
				ex.printStackTrace();
				return new ResponseEntity(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		return new ResponseEntity(response,HttpStatus.OK);
	}
	else
		return validate;
}

}
