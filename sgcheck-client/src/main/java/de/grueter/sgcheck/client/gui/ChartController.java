package de.grueter.sgcheck.client.gui;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.XYChart;

public class ChartController implements Initializable {
	@FXML
	private XYChart<String, Number> chart;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	
	public void addSeries(Collection< XYChart.Series<String, Number> > seriesCollection) {
		chart.getData().addAll(seriesCollection);
	}
}
