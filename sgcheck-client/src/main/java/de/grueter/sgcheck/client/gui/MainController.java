package de.grueter.sgcheck.client.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import com.ftdichip.ftd2xx.FTD2xxException;

import de.grueter.sgcheck.client.emu.EmuCheckMessreihe;
import de.grueter.sgcheck.client.model.MeasurementSeriesModel;
import de.grueter.sgcheck.dto.MeasurementPointDTO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController implements Initializable {
	EmuCheckMessreihe eccm;

	@FXML
	private TextField messreiheIdEdit;
	@FXML
	private TextField verbraucherEdit;
	@FXML
	private TextField intervalEdit;
	@FXML
	private TextField messreihegroesseEdit;

	@FXML
	private TableView<MeasurementSeries> measurementSeriesTable;

	@FXML
	private TableColumn<MeasurementSeries, Integer> messreiheIdColumn;
	@FXML
	private TableColumn<MeasurementSeries, String> messreiheStartColumn;
	@FXML
	private TableColumn<MeasurementSeries, String> messreiheStopColumn;
	@FXML
	private TableColumn<MeasurementSeries, Integer> messreiheIntervalColumn;
	@FXML
	private TableColumn<MeasurementSeries, String> messreiheMessgroesseColumn;
	@FXML
	private TableColumn<MeasurementSeries, String> messreiheVerbraucherColumn;

	// use initialize() instead of constructor!!!
	public MainController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		MeasurementSeriesModel.getInstance().updateMeasurementSeriesList();

		// init columns of table
		messreiheIdColumn.setCellValueFactory(new PropertyValueFactory<MeasurementSeries, Integer>("id"));
		messreiheStartColumn.setCellValueFactory(new PropertyValueFactory<MeasurementSeries, String>("start"));
		messreiheStopColumn.setCellValueFactory(new PropertyValueFactory<MeasurementSeries, String>("stop"));
		messreiheIntervalColumn.setCellValueFactory(new PropertyValueFactory<MeasurementSeries, Integer>("interval"));
		messreiheVerbraucherColumn
				.setCellValueFactory(new PropertyValueFactory<MeasurementSeries, String>("verbraucher"));
		messreiheMessgroesseColumn
				.setCellValueFactory(new PropertyValueFactory<MeasurementSeries, String>("messgroesse"));

		// set data to table
		measurementSeriesTable.setItems(MeasurementSeriesModel.getInstance().getMeasurementSeriesList());
		measurementSeriesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// add context-menu to table
		final MenuItem showLineChartitem = new MenuItem("Zeige Liniendiagramm");
		showLineChartitem.setOnAction(ae -> {
			showChart("LineChartView.fxml", "Liniendiagramm");
		});

		final MenuItem showBarChartItem = new MenuItem("Zeige Balkendiagramm");
		showBarChartItem.setOnAction(ae -> {
			showChart("BarChartView.fxml", "Balkendiagramm");
		});

		final ContextMenu contextMenu = new ContextMenu();
		contextMenu.getItems().addAll(showLineChartitem, showBarChartItem);

		measurementSeriesTable.setContextMenu(contextMenu);
	}

	private void showChart(String res, String title) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(res));

		try {
			Stage stage = new Stage();
			stage.setTitle(title);
			stage.setScene(new Scene((AnchorPane) loader.load()));

			ChartController lineChartController = loader.getController();
			lineChartController.addSeries(createSeriesCollectionOfSelection());

			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void deleteMeasurementSeries() {
		if (!measurementSeriesTable.getSelectionModel().isEmpty()) {
			MeasurementSeriesModel.getInstance()
					.removeMeasurementSeries(measurementSeriesTable.getSelectionModel().getSelectedItem().getId());

			// update view
			MeasurementSeriesModel.getInstance().updateMeasurementSeriesList();
		}
	}

	private Collection<XYChart.Series<String, Number>> createSeriesCollectionOfSelection() {
		Collection<XYChart.Series<String, Number>> chartSeriesCollection = new ArrayList<Series<String, Number>>();

		ObservableList<MeasurementSeries> measurementSeriesList = measurementSeriesTable.getSelectionModel()
				.getSelectedItems();

		for (MeasurementSeries series : measurementSeriesList) {
			Series<String, Number> chartSeries = MeasurementSeriesModel.getInstance()
					.getMeasurementPointList(series.getId());
			chartSeries.setName(series.getVerbraucher() + "(id: " + series.getId() + ")");
			chartSeriesCollection.add(chartSeries);
		}

		return chartSeriesCollection;
	}

	@FXML
	void showNewMeasurementSeriesView() {
		try {
			GridPane rootPane = FXMLLoader.load(getClass().getResource("NewMeasurementSeriesView.fxml"));
			Scene scene = new Scene(rootPane);
			Stage stage = new Stage();
			stage.setTitle("Neue Messreihe");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void startMeasurement() {
		try {
			System.out.println("create emucheck");
			MeasurementSeries series = measurementSeriesTable.getSelectionModel().getSelectedItem();
			eccm = new EmuCheckMessreihe(this, series.getId(), series.getInterval());
			System.out.println("start emucheck");
			eccm.start();
		} catch (FTD2xxException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void stopMeasurement() {
		if (eccm != null) {
			eccm.interrupt();
		}
	}

	public void saveMeasurement(int measurementSeriesId, MeasurementPointDTO measurementPoint) {
		try {
			MeasurementSeriesModel.getInstance().saveMeasurementPoint(measurementSeriesId, measurementPoint);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
