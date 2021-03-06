package de.grueter.sgcheck.server.rest;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
            e.printStackTrace();
        }

        return null;
    }

    @DELETE
    @Path("/{id}")
    public void removeMeasurementSeries(@PathParam("id") int id) {
        try {
            DBActions.getInstance().removeMeasurementSeries(id);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public MeasurementSeriesDTO addMeasurementSeries(MeasurementSeriesDTO series) {

        try {
            if (series.getInterval() > 0) {
                if (DBActions.getInstance().getMeasurementSeries(series.getId()) == null) {
                    int id = DBActions.getInstance().addMeasurementSeries(series);
                    return DBActions.getInstance().getMeasurementSeries(id);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
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
    public void addMeasurementPoint(@PathParam("measurementSeriesId") int measurementSeriesId,
            MeasurementPointDTO point) {

        try {
            DBActions.getInstance().addMeasurementPoint(measurementSeriesId, point);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}