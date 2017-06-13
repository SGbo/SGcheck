package de.grueter.sgcheck.client.gui;

import java.net.URL;
import java.util.ResourceBundle;

import de.grueter.sgcheck.client.model.MeasurementSeriesModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewMeasurementSeriesController implements Initializable {
	ObservableList<Measurand> measurandList;

	@FXML
	private TextField consumerEdit;
	@FXML
	private ComboBox<Measurand> measurandCombo;
	@FXML
	private TextField intervalEdit;
	@FXML
	private Button saveButton;
	@FXML
	private Button cancelButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		measurandList = FXCollections.observableArrayList();
		measurandList.add(new Measurand(1, "Spannung", "V"));
		measurandCombo.setItems(measurandList);
		measurandCombo.getSelectionModel().select(0);
	}

	@FXML
	public void onSaveClicked() {
		if (consumerEdit.getText().isEmpty() || (intervalEdit.getText().isEmpty())) {
			return;
		}
		
		try {
			MeasurementSeriesModel.getInstance().createMeasurementSeries(consumerEdit.getText(), measurandCombo.getValue(), Integer.parseInt(intervalEdit.getText()));
		
			// update view
			MeasurementSeriesModel.getInstance().updateMeasurementSeriesList();

			// close
			Stage stage = (Stage) saveButton.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onCancelClicked() {
		// close
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
}
