package com.zip.code.model;


public class CityResponseModel {

	private String currentWeather;
	private String forecastWeather;
	private String errorMessage;
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getCurrentWeather() {
		return currentWeather;
	}
	public void setCurrentWeather(String currentWeather) {
		this.currentWeather = currentWeather;
	}
	public String getForecastWeather() {
		return forecastWeather;
	}
	public void setForecastWeather(String forecastWeather) {
		this.forecastWeather = forecastWeather;
	}
}
