//package db;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.StringJoiner;
//
//import gui.Measurand;
//import gui.MeasurementSeries;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.scene.chart.XYChart;
//import javafx.scene.chart.XYChart.Series;
//
//public final class SqlModel {
//	private static SqlModel instance; // pointer to singleton-instance
//	private Connection connection; // sql connection
//	final ObservableList<MeasurementSeries> measurementSeriesList = FXCollections.observableArrayList();
//
//	// create instance (singleton)
//	public static SqlModel getInstance() {
//		if (instance == null) {
//			instance = new SqlModel();
//		}
//
//		return instance;
//	}
//
//	// hidden constructor
//	private SqlModel() {
//	}
//
//	// open connection to sql server
//	void connectSql() throws SQLException, ClassNotFoundException {
//		Class.forName("com.mysql.jdbc.Driver");
//
//		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Grueter", "phpmyadmin", "some_password");
//
//		// connect to lab
//		// connection =
//		// DriverManager.getConnection("jdbc:mysql://stud-server2008:3306/grueter",
//		// "Grueter",
//		// "123456789");
//	}
//
//	public ObservableList<MeasurementSeries> getMeasurementSeriesList() {
//		return measurementSeriesList;
//	}
//
//	public void updateMeasurementSeriesList() throws ClassNotFoundException, SQLException {
//		Statement statement;
//		ResultSet resultSet = null;
//
//		// connect to localhost
//		connectSql();
//
//		statement = connection.createStatement();
//		resultSet = statement.executeQuery("SELECT * FROM messreihe");
//
//		measurementSeriesList.clear();
//
//		while (resultSet.next()) {
//			MeasurementSeries messreihe = new MeasurementSeries();
//			messreihe.setId(resultSet.getInt("id"));
//			messreihe.setVerbraucher(resultSet.getString("verbraucher"));
//			messreihe.setInterval(resultSet.getInt("interval"));
//			measurementSeriesList.add(messreihe);
//		}
//	}
//	
//	public Series<String, Number> getMeasurementSeriesData(int messreihe_id) throws ClassNotFoundException, SQLException {
//		Statement statement;
//		ResultSet resultSet = null;
//
//		// connect to localhost
//		connectSql();
//
//		statement = connection.createStatement();
//		resultSet = statement.executeQuery("SELECT * FROM messung WHERE messreihe_id=" + messreihe_id);
//
//		Series<String, Number> series = new Series<String, Number>();
//
//		while (resultSet.next()) {
//			series.getData().add(new XYChart.Data<String, Number>(resultSet.getString("id"), resultSet.getDouble("messwert")));
//		}
//		
//		return series;
//	}
//
//	public String getMeasurementSeries(int messreihe_id) throws ClassNotFoundException, SQLException {
//		Statement statement;
//		ResultSet resultSet = null;
//		StringJoiner measurementSeries = new StringJoiner(" / ");
//
//		// connect to localhost
//		connectSql();
//
//		statement = connection.createStatement();
//		resultSet = statement.executeQuery("SELECT * FROM messung WHERE messreihe_id=" + messreihe_id);
//
//		while (resultSet.next()) {
//			measurementSeries.add(Double.toString(resultSet.getDouble("messwert")));
//		}
//
//		return measurementSeries.toString();
//	}
//
//	public void createMeasurementSeries(String consumer, Measurand measurand, int interval)
//			throws SQLException, ClassNotFoundException {
//
//		Statement statement;
//
//		// connect to localhost
//		connectSql();
//
//		statement = connection.createStatement();
//		String sql = "INSERT INTO " + "`messreihe` (`id`, `verbraucher`, `messgroesse_id`, `interval`) VALUES (NULL, '"
//				+ consumer + "','" + measurand.getId() + "','" + interval + "');";
//
//		statement.executeUpdate(sql);
//	}
//}
