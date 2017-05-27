package de.grueter.sgcheck.dto;

import java.util.Date;

public class MeasurementPointDTO {
	private int id;
	private int measurementSeriesId;
	private Date timestemp;
	private double value;
	
	public MeasurementPointDTO() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMeasurementSeriesId() {
		return measurementSeriesId;
	}

	public void setMeasurementSeriesId(int measurementSeriesId) {
		this.measurementSeriesId = measurementSeriesId;
	}

	public Date getTimestemp() {
		return timestemp;
	}

	public void setTimestemp(Date timestemp) {
		this.timestemp = timestemp;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
