package de.grueter.sgcheck.server.rest.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.grueter.sgcheck.dto.MeasurementPointDTO;
import de.grueter.sgcheck.dto.MeasurementSeriesDTO;

public final class DBActions {
	private static DBActions instance; // pointer to singleton-instance
	private Connection connection; // sql connection

	// create instance (singleton)
	public static DBActions getInstance() {
		if (instance == null) {
			instance = new DBActions();
		}

		return instance;
	}

	// hidden constructor
	private DBActions() {
	}

	// open connection to sql server
	void connectSql() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");

		// connection =
		 DriverManager.getConnection("jdbc:mysql://localhost:3306/Grueter",
		 "phpmyadmin", "some_password");

		// connect to lab
//		connection = DriverManager.getConnection("jdbc:mysql://stud-server2008:3306/grueter", "Grueter", "123456789");
	}

	public List<MeasurementSeriesDTO> getMeasurementSeriesList() throws ClassNotFoundException, SQLException {
		List<MeasurementSeriesDTO> measurementSeriesList = new ArrayList<MeasurementSeriesDTO>();

		connectSql();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM messreihe");

		measurementSeriesList.clear();

		while (resultSet.next()) {
			MeasurementSeriesDTO messreihe = new MeasurementSeriesDTO();
			messreihe.setId(resultSet.getInt("id"));
			messreihe.setConsumer(resultSet.getString("verbraucher"));
			messreihe.setInterval(resultSet.getInt("interval"));
			measurementSeriesList.add(messreihe);
		}
		return measurementSeriesList;
	}

	public MeasurementSeriesDTO getMeasurementSeries(int id) throws SQLException, ClassNotFoundException {
		connectSql();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM messreihe WHERE id=" + id);

		while (resultSet.next()) {
			MeasurementSeriesDTO series = new MeasurementSeriesDTO();
			series.setId(resultSet.getInt("id"));
			series.setConsumer(resultSet.getString("verbraucher"));
			series.setInterval(resultSet.getInt("interval"));
			series.setStart(getMinMeasurementSeriesDate(series.getId()));
		}

		return null;
	}

	public Date getMinMeasurementSeriesDate(int messreihe_id) throws ClassNotFoundException, SQLException {
		connectSql();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement
				.executeQuery("SELECT MIN(zeit) AS minZeit FROM messung WHERE messreihe_id=" + messreihe_id);

		if (resultSet.next()) {
			return resultSet.getDate("minZeit");
		}

		return null;
	}

	public int addMeasurementSeries(MeasurementSeriesDTO series) throws SQLException, ClassNotFoundException {
		connectSql();

		Statement statement = connection.createStatement();
		String sql = "INSERT INTO " + "`messreihe` (`id`, `verbraucher`, `messgroesse_id`, `interval`) VALUES (NULL, '"
				+ series.getConsumer() + "','" + series.getId() + "','" + series.getInterval() + "'); ";

		statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
		ResultSet resultSet = statement.getGeneratedKeys();
		
		return resultSet.getInt("id");
	}

	public void removeMeasurementSeries(int messreihe_id) throws ClassNotFoundException, SQLException {
		connectSql();

		Statement statement = connection.createStatement();
		String sql = "DELETE FROM " + "`messreihe` WHERE id=" + messreihe_id;
		statement.executeUpdate(sql);

		sql = "DELETE FROM " + "`messung` WHERE messreihe_id=" + messreihe_id;
		statement.executeUpdate(sql);
	}

	public List<MeasurementPointDTO> getMeasurementPointList(int messreihe_id)
			throws ClassNotFoundException, SQLException {

		connectSql();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM messung WHERE messreihe_id=" + messreihe_id);

		List<MeasurementPointDTO> pointList = new ArrayList<MeasurementPointDTO>();

		while (resultSet.next()) {
			MeasurementPointDTO point = new MeasurementPointDTO();
			point.setId(resultSet.getInt("id"));
			point.setTimestemp(new Date(resultSet.getTimestamp("zeit").getTime()));
			point.setValue(resultSet.getDouble("messwert"));

			pointList.add(point);
		}

		return pointList;
	}

	public void addMeasurementPoint(int measurementSeriesId, MeasurementPointDTO point)
			throws SQLException, ClassNotFoundException {

		connectSql();

		Statement statement = connection.createStatement();
		statement.executeUpdate("INSERT INTO `messung` (`id`, `messreihe_id`, `zeit`, `messwert`) VALUES ('"
				+ point.getId() + "','" + measurementSeriesId + "','"
				+ new java.sql.Date(point.getTimestemp().getTime()) + "','" + point.getValue() + "')");
	}
}
