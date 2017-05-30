package de.grueter.sgcheck.dto;

import java.util.Date;

public class MeasurementPointDTO {
	private int id;
	private Date timestemp;
	private double value;
	
	public MeasurementPointDTO() {
		
	}
	
	public MeasurementPointDTO(int id, Date timeStemp, double value) {
		this.id = id;
		this.timestemp = timeStemp;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
