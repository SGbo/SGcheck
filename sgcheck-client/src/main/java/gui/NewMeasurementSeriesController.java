package gui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import business.SqlModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewMeasurementSeriesController implements Initializable {
	DBModel sqlModel;
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
		sqlModel = DBModel.getInstance();
		
		measurandList = FXCollections.observableArrayList();
		measurandList.add(new Measurand(1, "Spannung", "V"));
		measurandCombo.setItems(measurandList);
		measurandCombo.getSelectionModel().select(0);
	}

	@FXML
	public void onSaveClicked() {
		try {
			if (consumerEdit.getText().isEmpty() || (intervalEdit.getText().isEmpty())) {
				return;
			}
			
			// insert entry into database
			sqlModel.createMeasurementSeries(consumerEdit.getText(), measurandCombo.getValue(),
					Integer.parseInt(intervalEdit.getText()));
			
			// update view
			sqlModel.updateMeasurementSeriesList();
			
			// close
			Stage stage = (Stage) saveButton.getScene().getWindow();
			stage.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
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
