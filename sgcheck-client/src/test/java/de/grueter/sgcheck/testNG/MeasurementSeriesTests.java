package de.grueter.sgcheck.testNG;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import de.grueter.sgcheck.client.gui.Measurand;
import de.grueter.sgcheck.client.model.MeasurementSeriesModel;

public class MeasurementSeriesTests {
	@Test(expectedExceptions = { Exception.class })
	public void testCreateMeasurementSeries1() throws Exception {
		MeasurementSeriesModel.getInstance().createMeasurementSeries("Toaster", new Measurand(1, "Spannung", "V"), 0);
	}

	@Test(expectedExceptions = { Exception.class })
	public void testCreateMeasurementSeries2() throws Exception {
		MeasurementSeriesModel.getInstance().createMeasurementSeries("Toaster", new Measurand(1, "Spannung", "V"), -1);
	}

	@Test
	public void testCreateMeasurementSeries3() throws Exception {
		MeasurementSeriesModel.getInstance().createMeasurementSeries("Toaster", new Measurand(1, "Spannung", "V"), 1);
	}

	@Test(expectedExceptions = { Exception.class })
	public void testCreateMeasurementSeries4() throws Exception {
		MeasurementSeriesModel.getInstance().createMeasurementSeries("Toaster", new Measurand(1, "Spannung", "V"), -1);
	}

	@Test(expectedExceptions = { Exception.class })
	public void testCreateMeasurementSeries5() throws Exception {
		MeasurementSeriesModel.getInstance().createMeasurementSeries("Toaster", new Measurand(1, "Spannung", "V"),
				3601);
	}

	@AfterMethod
	public void cleanup() {
		MeasurementSeriesModel.getInstance().removeMeasurementSeries(0);
	}
}
