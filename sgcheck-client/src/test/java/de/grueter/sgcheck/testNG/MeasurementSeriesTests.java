package de.grueter.sgcheck.testNG;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import de.grueter.sgcheck.client.gui.Measurand;
import de.grueter.sgcheck.client.model.MeasurementSeriesModel;

public class MeasurementSeriesTests {
    private int lastMeasurementSeriesId = -1;

    @Test(expectedExceptions = { Exception.class })
    public void testCreateMeasurementSeries1() throws Exception {
        lastMeasurementSeriesId = MeasurementSeriesModel.getInstance().createMeasurementSeries("Toaster",
                new Measurand(1, "Spannung", "V"), 0);
    }

    @Test(expectedExceptions = { Exception.class })
    public void testCreateMeasurementSeries2() throws Exception {
        lastMeasurementSeriesId = MeasurementSeriesModel.getInstance().createMeasurementSeries("Toaster",
                new Measurand(1, "Spannung", "V"), -1);
    }

    @Test
    public void testCreateMeasurementSeries3() throws Exception {
        lastMeasurementSeriesId = MeasurementSeriesModel.getInstance().createMeasurementSeries("Toaster",
                new Measurand(1, "Spannung", "V"), 1);
    }

    @AfterMethod
    public void cleanup() {
        System.out.println(lastMeasurementSeriesId);
        MeasurementSeriesModel.getInstance().removeMeasurementSeries(lastMeasurementSeriesId);
    }
}
