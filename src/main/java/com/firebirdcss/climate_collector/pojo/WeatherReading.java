/**
 * All rights reserved, Copyright 2018 by Scott Griffis
 */
package com.firebirdcss.climate_collector.pojo;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * This is a POJO for storing and transporting weather reading related data.
 * 
 * @author Scott Griffis
 *
 */
public class WeatherReading {
	private String city;
	private String state;
	private String country;
	private String postalCode;
	private Float latitude;
	private Float longitude;
	private Integer elevationFt;
	private String observer;
	private String stationId;
	private Date observationTime;
	private String weatherStr;
	private Float tempF;
	private Integer relativeHumidityPercent;
	private String windStr;
	private String windDirStr;
	private Integer windDeg;
	private Float windMph;
	private Float windGustMph;
	private Integer pressureMb;
	private Float pressureIn;
	private String pressureTrend;
	private Integer dewpointDegF;
	private Integer windChillDegF;
	private Integer heatIndexDegF;
	private Integer feelsLikeDegF;
	private Float visibilityMi;
	private Integer solarRad;
	private Float uvRad;
	private Float percipOneHourIn;
	private Float percipDayIn;
	private String iconStr;
	
	/**
	 * @return Returns the city as String
	 */
	public String getCity() {
		
		return city;
	}
	
	/**
	 * @param city - The city to set as String
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * @return Returns the state as String
	 */
	public String getState() {
		
		return state;
	}
	
	/**
	 * @param state - The state to set as String
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * @return Returns the country as String
	 */
	public String getCountry() {
		
		return country;
	}
	
	/**
	 * @param country - The country to set as String
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * @return Returns the postalCode as {@link String}
	 */
	public String getPostalCode() {
		
		return postalCode;
	}
	
	/**
	 * @param postalCode - The postalCode to set as String
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	/**
	 * @return Returns the latitude as Float
	 */
	public Float getLatitude() {
		
		return latitude;
	}
	
	/**
	 * @param latitude - The latitude to set as Float
	 */
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * @return Returns the longitude as Float
	 */
	public Float getLongitude() {
		
		return longitude;
	}
	
	/**
	 * @param longitude - The longitude to set as Float
	 */
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * @return Returns the elevationFt as Integer
	 */
	public Integer getElevationFt() {
		
		return elevationFt;
	}
	
	/**
	 * @param elevationFt - The elevationFt to set as Integer
	 */
	public void setElevationFt(Integer elevationFt) {
		this.elevationFt = elevationFt;
	}
	
	/**
	 * @return Returns the observer as String
	 */
	public String getObserver() {
		
		return observer;
	}
	
	/**
	 * @param observer - The observer to set as String
	 */
	public void setObserver(String observer) {
		this.observer = observer;
	}
	
	/**
	 * @return Returns the stationId as String
	 */
	public String getStationId() {
		
		return stationId;
	}
	
	/**
	 * @param stationId - The stationId to set as String
	 */
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	
	/**
	 * @return Returns the observationTime as Date
	 */
	public Date getObservationTime() {
		
		return observationTime;
	}
	
	/**
	 * @param observationTime - The observationTime to set as Date
	 */
	public void setObservationTime(Date observationTime) {
		this.observationTime = observationTime;
	}
	
	/**
	 * @return Returns the weatherStr as String
	 */
	public String getWeatherStr() {
		
		return weatherStr;
	}
	
	/**
	 * @param weatherStr - The weatherStr to set as String
	 */
	public void setWeatherStr(String weatherStr) {
		this.weatherStr = weatherStr;
	}
	
	/**
	 * @return Returns the tempF as Float
	 */
	public Float getTempF() {
		
		return tempF;
	}
	
	/**
	 * @param tempF - The tempF to set as Float
	 */
	public void setTempF(Float tempF) {
		this.tempF = tempF;
	}
	
	/**
	 * @return Returns the relativeHumidityPercent as Integer
	 */
	public Integer getRelativeHumidityPercent() {
		
		return relativeHumidityPercent;
	}
	
	/**
	 * @param relativeHumidityPercent - The relativeHumidityPercent to set as Integer
	 */
	public void setRelativeHumidityPercent(Integer relativeHumidityPercent) {
		this.relativeHumidityPercent = relativeHumidityPercent;
	}
	
	/**
	 * @return Returns the windStr as String
	 */
	public String getWindStr() {
		
		return windStr;
	}
	
	/**
	 * @param windStr - The windStr to set as String
	 */
	public void setWindStr(String windStr) {
		this.windStr = windStr;
	}
	
	/**
	 * @return Returns the windDirStr as String
	 */
	public String getWindDirStr() {
		
		return windDirStr;
	}
	
	/**
	 * @param windDirStr - The windDirStr to set as String
	 */
	public void setWindDirStr(String windDirStr) {
		this.windDirStr = windDirStr;
	}
	
	/**
	 * @return Returns the windDeg as Integer
	 */
	public Integer getWindDeg() {
		
		return windDeg;
	}
	
	/**
	 * @param windDeg - The windDeg to set as Integer
	 */
	public void setWindDeg(Integer windDeg) {
		this.windDeg = windDeg;
	}
	
	/**
	 * @return Returns the windMph as Float
	 */
	public Float getWindMph() {
		
		return windMph;
	}
	
	/**
	 * @param windMph - The windMph to set as Float
	 */
	public void setWindMph(Float windMph) {
		this.windMph = windMph;
	}
	
	/**
	 * @return Returns the windGustMph as Float
	 */
	public Float getWindGustMph() {
		
		return windGustMph;
	}
	
	/**
	 * @param windGustMph - The windGustMph to set as Float
	 */
	public void setWindGustMph(Float windGustMph) {
		this.windGustMph = windGustMph;
	}
	
	/**
	 * @return Returns the pressureMb as Integer
	 */
	public Integer getPressureMb() {
		
		return pressureMb;
	}
	
	/**
	 * @param pressureMb - The pressureMb to set as Integer
	 */
	public void setPressureMb(Integer pressureMb) {
		this.pressureMb = pressureMb;
	}
	
	/**
	 * @return Returns the pressureIn as Float
	 */
	public Float getPressureIn() {
		
		return pressureIn;
	}
	
	/**
	 * @param pressureIn - The pressureIn to set as Float
	 */
	public void setPressureIn(Float pressureIn) {
		this.pressureIn = pressureIn;
	}
	
	/**
	 * @return Returns the pressureTrend as String
	 */
	public String getPressureTrend() {
		
		return pressureTrend;
	}
	
	/**
	 * @param pressureTrend - The pressureTrend to set as String
	 */
	public void setPressureTrend(String pressureTrend) {
		this.pressureTrend = pressureTrend;
	}
	
	/**
	 * @return Returns the dewpointDegF as Integer
	 */
	public Integer getDewpointDegF() {
		
		return dewpointDegF;
	}
	
	/**
	 * @param dewpointDegF - The dewpointDegF to set as Integer
	 */
	public void setDewpointDegF(Integer dewpointDegF) {
		this.dewpointDegF = dewpointDegF;
	}
	
	/**
	 * @return Returns the windChillDegF as Integer
	 */
	public Integer getWindChillDegF() {
		
		return windChillDegF;
	}
	
	/**
	 * @param windChillDegF - The windChillDegF to set as Integer
	 */
	public void setWindChillDegF(Integer windChillDegF) {
		this.windChillDegF = windChillDegF;
	}
	
	/**
	 * @return Returns the feelsLikeDegF as Integer
	 */
	public Integer getFeelsLikeDegF() {
		
		return feelsLikeDegF;
	}
	
	/**
	 * @param feelsLikeDegF - The feelsLikeDegF to set as Integer
	 */
	public void setFeelsLikeDegF(Integer feelsLikeDegF) {
		this.feelsLikeDegF = feelsLikeDegF;
	}
	
	/**
	 * @return Returns the visibilityMi as Float
	 */
	public Float getVisibilityMi() {
		
		return visibilityMi;
	}
	
	/**
	 * @param visibilityMi - The visibilityMi to set as Float
	 */
	public void setVisibilityMi(Float visibilityMi) {
		this.visibilityMi = visibilityMi;
	}
	
	/**
	 * @return Returns the solarRad as Integer
	 */
	public Integer getSolarRad() {
		
		return solarRad;
	}
	
	/**
	 * @param solarRad - The solarRad to set as Integer
	 */
	public void setSolarRad(Integer solarRad) {
		this.solarRad = solarRad;
	}
	
	/**
	 * @return Returns the uvRad as Float
	 */
	public Float getUvRad() {
		
		return uvRad;
	}
	
	/**
	 * @param uvRad - The uvRad to set as Float
	 */
	public void setUvRad(Float uvRad) {
		this.uvRad = uvRad;
	}
	
	/**
	 * @return Returns the percipOneHourIn as Float
	 */
	public Float getPercipOneHourIn() {
		
		return percipOneHourIn;
	}
	
	/**
	 * @param percipOneHourIn - The percipOneHourIn to set as Float
	 */
	public void setPercipOneHourIn(Float percipOneHourIn) {
		this.percipOneHourIn = percipOneHourIn;
	}
	
	/**
	 * @return Returns the percipDayIn as Float
	 */
	public Float getPercipDayIn() {
		
		return percipDayIn;
	}
	
	/**
	 * @param percipDayIn - The percipDayIn to set as Float
	 */
	public void setPercipDayIn(Float percipDayIn) {
		this.percipDayIn = percipDayIn;
	}
	
	/**
	 * @return Returns the iconStr as String
	 */
	public String getIconStr() {
		
		return iconStr;
	}
	
	/**
	 * @param iconStr - The iconStr to set as String
	 */
	public void setIconStr(String iconStr) {
		this.iconStr = iconStr;
	}

	/**
	 * @return Returns the heatIndexDegF as Integer
	 */
	public Integer getHeatIndexDegF() {
		
		return heatIndexDegF;
	}

	/**
	 * @param heatIndexDegF - The heatIndexDegF to set as Integer
	 */
	public void setHeatIndexDegF(Integer heatIndexDegF) {
		this.heatIndexDegF = heatIndexDegF;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (Method method : this.getClass().getDeclaredMethods()) {
			if (method.getName().startsWith("get")) {
				if (result.length() > 0) {
					result.append('^');
				}
				Object obj = null;
				try {
					obj = method.invoke(this);
				} catch (Exception fallThroughAsNull) {}
				
				result.append(obj);
			}
		}
		
		return result.toString();
	}
}
