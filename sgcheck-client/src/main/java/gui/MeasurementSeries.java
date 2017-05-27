package gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MeasurementSeries {
	private final SimpleIntegerProperty id = new SimpleIntegerProperty();
	private final SimpleStringProperty start = new SimpleStringProperty();
	private final SimpleStringProperty stop = new SimpleStringProperty();
	private final SimpleIntegerProperty interval = new SimpleIntegerProperty();
	private final SimpleStringProperty verbraucher = new SimpleStringProperty();
	private final SimpleStringProperty messgroesse = new SimpleStringProperty();
	
	public int getId() {
		return id.get();
	}
	public void setId(int id) {
		this.id.set(id);
	}
	public String getStart() {
		return start.get();
	}
	public void setStart(String startDatum) {
		this.start.set(startDatum);
	}
	public String getStop() {
		return stop.get();
	}
	public void setStop(String stopDatum) {
		this.stop.set(stopDatum);
	}
	public int getInterval() {
		return interval.get();
	}
	public void setInterval(int interval) {
		this.interval.set(interval);
	}
	public String getVerbraucher() {
		return verbraucher.get();
	}
	public void setVerbraucher(String verbraucher) {
		this.verbraucher.set(verbraucher);
	}
	public String getMessgroesse() {
		return messgroesse.get();
	}
	public void setMessgroesse(String messgroesse) {
		this.messgroesse.set(messgroesse);
	}
}
