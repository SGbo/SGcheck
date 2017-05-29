package de.grueter.sgcheck.dto;

public class MeasurandDTO {
	private int id;
	private String name;
	private String unit;
	
	public MeasurandDTO() {
		
	}
	
	public MeasurandDTO(int id, String name, String unit) {
		this.id = id;
		this.name = name;
		this.unit = unit;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUnit() {
		return unit;
	}
}
