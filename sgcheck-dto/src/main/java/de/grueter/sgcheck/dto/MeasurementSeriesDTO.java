package de.grueter.sgcheck.dto;

import java.util.Date;

public class MeasurementSeriesDTO {
	private int id;
	private Date start;
	private Date stop;
	private int interval;
	private String consumer;
	private MeasurandDTO measurand;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getStop() {
		return stop;
	}
	public void setStop(Date stop) {
		this.stop = stop;
	}
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	public String getConsumer() {
		return consumer;
	}
	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}
	public MeasurandDTO getMeasurand() {
		return measurand;
	}
	public void setMeasurand(MeasurandDTO measurand) {
		this.measurand = measurand;
	}
}