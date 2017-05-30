package de.grueter.sgcheck.client.model;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.logging.LoggingFeature;

import de.grueter.sgcheck.client.gui.Measurand;
import de.grueter.sgcheck.client.gui.MeasurementSeries;
import de.grueter.sgcheck.dto.MeasurandDTO;
import de.grueter.sgcheck.dto.MeasurementPointDTO;
import de.grueter.sgcheck.dto.MeasurementSeriesDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public final class MeasurementSeriesModel {
	private static MeasurementSeriesModel instance;
	private final String baseUri = "http://localhost:8080/sgcheck/measurementseries";
	private final ObservableList<MeasurementSeries> measurementSeriesList = FXCollections.observableArrayList();

	// create instance (singleton)
	public static MeasurementSeriesModel getInstance() {
		if (instance == null) {
			instance = new MeasurementSeriesModel();
		}

		return instance;
	}

	// hidden constructor
	private MeasurementSeriesModel() {
	}

	public ObservableList<MeasurementSeries> getMeasurementSeriesList() {
		return measurementSeriesList;
	}

	public void updateMeasurementSeriesList() {
		Client client = ClientBuilder.newClient();

		WebTarget webTarget = client.target(baseUri);
		Response response = webTarget.request(MediaType.APPLICATION_JSON).get();

		System.out.println(response.getStatus());

		if (response.getStatus() == 200) {
			List<MeasurementSeriesDTO> seriesList = response.readEntity(new GenericType<List<MeasurementSeriesDTO>>() {
			});

			measurementSeriesList.clear();

			for (MeasurementSeriesDTO seriesDTO : seriesList) {
				System.out.println(seriesDTO.getConsumer());
				MeasurementSeries series = new MeasurementSeries();
				series.setId(seriesDTO.getId());
				series.setInterval(seriesDTO.getInterval());
				if (seriesDTO.getMeasurand() != null) {
					series.setMessgroesse(seriesDTO.getMeasurand().getUnit());
				}
				if (seriesDTO.getStart() != null) {
					series.setStart(seriesDTO.getStart().toString());
				}
				if (seriesDTO.getStop() != null) {
					series.setStop(seriesDTO.getStop().toString());
				}
				series.setVerbraucher(seriesDTO.getConsumer());

				measurementSeriesList.add(series);
			}
		} else {
			System.out.println(response.getStatusInfo().toString());
		}
	}

	public Series<String, Number> getMeasurementPointList(int measureSeriesId) {
		Client client = ClientBuilder.newClient();

		WebTarget webTarget = client.target(baseUri + "/" + measureSeriesId + "/points");
		Response response = webTarget.request(MediaType.APPLICATION_JSON).get();

		System.out.println(response.getStatus());
		List<MeasurementPointDTO> pointList = response.readEntity(new GenericType<List<MeasurementPointDTO>>() {
		});

		Series<String, Number> series = new Series<String, Number>();

		for (MeasurementPointDTO point : pointList) {
			series.getData().add(new XYChart.Data<String, Number>(String.valueOf(point.getId()), point.getValue()));
		}

		return series;
	}

	public void createMeasurementSeries(String consumer, Measurand measurand, int interval) {

		Logger logger = Logger.getLogger(getClass().getName());

		Feature feature = new LoggingFeature(logger, Level.INFO, null, null);

		Client client = ClientBuilder.newClient();
		client.register(feature);

		MeasurementSeriesDTO seriesDTO = new MeasurementSeriesDTO();
		seriesDTO.setConsumer(consumer);
		seriesDTO.setInterval(interval);
		seriesDTO.setMeasurand(new MeasurandDTO(measurand.getId(), measurand.getName(), measurand.getUnit()));

		WebTarget webTarget = client.target(baseUri);

		@SuppressWarnings("unused")
		Response response = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(seriesDTO));
	}

	public void saveMeasurementPoint(int measurementSeriesId, MeasurementPointDTO measurementPoint) {
		Logger logger = Logger.getLogger(getClass().getName());

		Feature feature = new LoggingFeature(logger, Level.INFO, null, null);

		Client client = ClientBuilder.newClient();
		client.register(feature);

		WebTarget webTarget = client.target(baseUri + "/" + measurementSeriesId + "/points");

		@SuppressWarnings("unused")
		Response response = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(measurementPoint));
	}
}
