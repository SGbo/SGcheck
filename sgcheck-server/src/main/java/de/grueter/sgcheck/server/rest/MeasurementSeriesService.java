package de.grueter.sgcheck.server.rest;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.grueter.sgcheck.dto.MeasurementPointDTO;
import de.grueter.sgcheck.dto.MeasurementSeriesDTO;
import de.grueter.sgcheck.server.rest.db.DBActions;

@Path("/measurementseries")
public class MeasurementSeriesService {	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<MeasurementSeriesDTO> getMeasurementSeries() {
		try {
			return DBActions.getInstance().getMeasurementSeriesList();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON) 
	public MeasurementSeriesDTO getMeasurementSeries(@PathParam("id") int id) {
		try {
			return DBActions.getInstance().getMeasurementSeries(id);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addMeasurementSeries(MeasurementSeriesDTO series) {
		System.out.println("ADD MEASUREMENT SERIES");
		System.out.println(series.getConsumer());
		try {
			DBActions.getInstance().addMeasurementSeries(series);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	@GET
	@Path("/{measurementSeriesId}/points")
	@Produces(MediaType.APPLICATION_JSON)
	public List<MeasurementPointDTO> getMeasurementPoints(@PathParam("measurementSeriesId") int measurementSeriesId) {
		try {
			return DBActions.getInstance().getMeasurementPointList(measurementSeriesId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@POST
	@Path("/{measurementSeriesId}/points")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addMeasurementPoint(@PathParam("measurementSeriesId") int measurementSeriesId, MeasurementPointDTO point) {
		System.out.println(point.getId() + " " + point.getTimestemp() + " " + point.getValue());
		try {
			DBActions.getInstance().addMeasurementPoint(measurementSeriesId, point);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}