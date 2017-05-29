package de.grueter.sgcheck.client.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Measurand {
	private final SimpleIntegerProperty id = new SimpleIntegerProperty();
	private final SimpleStringProperty name = new SimpleStringProperty();
	private final SimpleStringProperty unit = new SimpleStringProperty();
	
	Measurand(int id, String name, String unit) {
		this.id.set(id);
		this.name.set(name);
		this.unit.set(unit);
	}
	
	@Override
	public String toString() {
		return this.name.get() + " (" + this.unit.get() + ")";
	}
	
	public int getId() {
		return id.get();
	}
	
	public String getName() {
		return name.get();
	}
	
	public String getUnit() {
		return unit.get();
	}
}
