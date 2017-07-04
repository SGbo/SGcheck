package de.grueter.sgcheck.testJBehave;

import static org.testng.Assert.assertTrue;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.testng.annotations.AfterMethod;

import de.grueter.sgcheck.client.gui.Measurand;
import de.grueter.sgcheck.client.model.MeasurementSeriesModel;

public class MeasurementseriesScenarioSteps {
    private MeasurementSeriesModel model = MeasurementSeriesModel.getInstance();
    private int measurementSeriesId;
    private boolean exceptionCaught = false;

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

    @Given("eine Messreihe mit der ID $id befindet sich in der Liste")
    public void step21(int id) {
        try {
            measurementSeriesId = model.createMeasurementSeries("Best Toaster EU", new Measurand(1, "Spannung", "V"), 1,
                    id);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @When("eine Messreihe mt der gleichen ID $id angelegt wird")
    public void step22(int id) {
        try {
            model.createMeasurementSeries("Best Toaster EU", new Measurand(1, "Spannung", "V"), 1, id);
        } catch (Exception e) {
            exceptionCaught = true;
            e.printStackTrace();
        }
    }

    @Then("soll eine Fehlermeldung ausgegeben werden")
    public void step23() {
        assertTrue(exceptionCaught);
    }

    @AfterScenario
    public void cleanup() {
        System.out.println(measurementSeriesId);
        MeasurementSeriesModel.getInstance().removeMeasurementSeries(measurementSeriesId);
    }
}
