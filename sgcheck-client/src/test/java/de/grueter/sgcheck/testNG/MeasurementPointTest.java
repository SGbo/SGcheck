package de.grueter.sgcheck.testNG;

import java.util.Date;

import org.testng.annotations.Test;

import de.grueter.sgcheck.client.model.MeasurementSeriesModel;
import de.grueter.sgcheck.dto.MeasurementPointDTO;

public class MeasurementPointTest {

	@Test(expectedExceptions= {Exception.class})
	public void testAddMeasurementPoint1() throws Exception {
		long t1 = System.currentTimeMillis();
			
		MeasurementSeriesModel.getInstance().saveMeasurementPoint(4, new MeasurementPointDTO(1, new Date(t1), 12.0));
		MeasurementSeriesModel.getInstance().saveMeasurementPoint(4, new MeasurementPointDTO(1, new Date(t1), 12.0));	
	}
	
	@Test
	public void testAddMeasurementPoint2() throws Exception {
		long t1 = System.currentTimeMillis() + 2000;
		long t2 = t1 + 4000;
			
		MeasurementSeriesModel.getInstance().saveMeasurementPoint(4, new MeasurementPointDTO(1, new Date(t1), 12.0));
		MeasurementSeriesModel.getInstance().saveMeasurementPoint(4, new MeasurementPointDTO(1, new Date(t2), 12.0));	
	}
}
