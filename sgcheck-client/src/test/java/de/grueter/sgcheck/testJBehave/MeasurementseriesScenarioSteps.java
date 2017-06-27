package de.grueter.sgcheck.testJBehave;

import static org.testng.Assert.assertTrue;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import de.grueter.sgcheck.client.gui.Measurand;
import de.grueter.sgcheck.client.model.MeasurementSeriesModel;

public class MeasurementseriesScenarioSteps {
	private MeasurementSeriesModel model = MeasurementSeriesModel.getInstance();
	private int measurementSeriesId;

	@Given("eine oder mehrere Messreihen befinden sich in der Liste")
	public void givenEineOderMehrereMessreihenBefindenSichInDerListe() {
		model.updateMeasurementSeriesList();
	}

	@When("eine neue Messreihe wird angelegt")
	public void whenEineNeueMessreiheWirdAngelegt() {
		try {
			measurementSeriesId = model.createMeasurementSeries("Lollipop", new Measurand(1, "Spannung", "V"), 1);
			model.updateMeasurementSeriesList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Then("sollte die Anzahl der Listeneintraege groesser 1 sein")
	public void thenSollteDieAnzahlDerListeneintraegeGroesser1Sein() {
		assertTrue(!model.getMeasurementSeriesList().isEmpty());
	}

	@Then("sollte die Messreihe mit der ID existieren")
	public void thenSollteDieMessreiheMitDerIDExistieren() {
		model.getMeasurementSeriesList().forEach(series -> {
			if (series.getId() == measurementSeriesId) {
				assertTrue(true);
				return;
			}
		});
		
		assertTrue(false);
	}
}
