package de.grueter.sgcheck.client.emu;

import static de.grueter.sgcheck.client.emu.EmuCheckMessreihe.Chars.ETX;
import static de.grueter.sgcheck.client.emu.EmuCheckMessreihe.Chars.LF;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ftdichip.ftd2xx.FTD2xxException;

import de.grueter.sgcheck.client.gui.MainController;
import de.grueter.sgcheck.dto.MeasurementPointDTO;


public class EmuCheckMessreihe extends Thread {
	enum State {
		IdleState, ConnectRequestState, ConnectReceiveState, PModeRequestState, PModeReceiveState, ValueRequestState, ValueReceiveState, DisconnectState
	}

	static final class Chars {
		public static final int SOH = 0x01;
		public static final int STX = 0x02;
		public static final int ETX = 0x03;
		public static final int ACK = 0x04;
		public static final int LF = 0x0A;
		public static final int CR = 0x0D;
	}

	private String value;
	private List<Double> measurementValueList = new ArrayList<Double>();
	private MainController mainController;
	private int measurementSeriesId;
	private int interval;
	private int messungenId;
	private State state = State.IdleState;
	private EmuCheckConnection ecc;

	public EmuCheckMessreihe(MainController mainController, int messreiheId, int interval) throws FTD2xxException {
		System.out.println("start");
		this.mainController = mainController;
		this.measurementSeriesId = messreiheId;
		this.interval = interval;

		ecc = new EmuCheckConnection();
		state = State.ConnectRequestState;
	}

	public double parseErgebnis(String ergebnis) {
		if (ergebnis.contains("*")) {
			int a = ergebnis.indexOf("(");
			int m = ergebnis.indexOf("*");

			ergebnis = ergebnis.substring(a + 1, m);

			return Double.parseDouble(ergebnis);
		}

		return 0.0;
	}

	@Override
	public void run() {
		while (!isInterrupted()) {
			try {
				int data = 0;
				// System.out.println(state);
				switch (state) {
				case IdleState:
					break;
				case ConnectRequestState:
					System.out.println("ConnectRequestState");
					ecc.connect();
					state = State.ConnectReceiveState;
					break;
				case ConnectReceiveState:
					data = ecc.readData();
					if (data == LF) {
						state = State.PModeRequestState;
					}
					break;
				case PModeRequestState:
					ecc.sendProgrammingMode();
					state = State.PModeReceiveState;
					break;
				case PModeReceiveState:
					data = ecc.readData();
					if (data == ETX) {
						state = State.ValueRequestState;
					}
					break;
				case ValueRequestState:
					ecc.sendRequest(MESSWERT.Spannung);
					state = State.ValueReceiveState;
					break;
				case ValueReceiveState:
					data = ecc.readData();
					value += (char) data;
					if (data == ETX) {
						measurementValueList.add(parseErgebnis(value));
						mainController.saveMeasurement(measurementSeriesId,  new MeasurementPointDTO(++messungenId, new Date(), parseErgebnis(value)));
						System.out.println(messungenId + " " + parseErgebnis(value));
						value = "";
						state = State.ValueRequestState;
						Thread.sleep(interval * 1000);
					}
					break;
				case DisconnectState:
					ecc.disconnect();
					state = State.IdleState;
					break;
				default:
					System.out.println("State not handled!");
				}
				sleep(1);
			} catch (FTD2xxException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				try {
					ecc.disconnect();
				} catch (FTD2xxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				interrupt(); // needed to set the internal interrupt flag again
			}
		}
	}
}
