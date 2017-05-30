package de.grueter.sgcheck.client.emu;

import com.ftdichip.ftd2xx.DataBits;
import com.ftdichip.ftd2xx.Device;
import com.ftdichip.ftd2xx.FTD2xxException;
import com.ftdichip.ftd2xx.Parity;
import com.ftdichip.ftd2xx.Port;
import com.ftdichip.ftd2xx.Service;
import com.ftdichip.ftd2xx.StopBits;

public class EmuCheckConnection {
	private Device device = null;
	private Port port = null;
	private Device[] deviceArray;

	public EmuCheckConnection() throws FTD2xxException {
		deviceArray = Service.listDevices();
		System.out.println("Anzahl der gefundenen Gerï¿½te: " + deviceArray.length);

		device = getDeviceByProductId(49632);

		System.out.println("Gerät gefunden: " + device.getDeviceDescriptor().getProductDescription());

		// open device
		device.open();

		port = device.getPort();
		// 7 data bits, 1 stop bit, even parity
		port.setDataCharacteristics(DataBits.DATA_BITS_7, StopBits.STOP_BITS_1, Parity.EVEN);

		// baudrate
		port.setBaudRate(300);
	}

	private Device getDeviceByProductId(int productId) throws FTD2xxException {
		for (Device dev : deviceArray) {
			if (dev.getDeviceDescriptor().getProductID() == productId) {
				return dev;
			}
		}

		return null;
	}

	public void connect() throws FTD2xxException {
		byte[] start = new byte[] { 0x2F, 0x3F, 0x21, 0x0D, 0x0A };
		device.write(start);
		System.out.println("Einleitung Kommunikation USBCheck");
	}

	public void disconnect() throws FTD2xxException {
		byte[] ende = new byte[] { 0x01, 0x42, 0x30, 0x03 };
		device.write(ende);
		device.close();
		System.out.println("Ende Kommunikation USBCheck");
	}

	public void close() throws FTD2xxException {
		if (device != null) {
			device.close();
		}
	}

	public void sendProgrammingMode() throws FTD2xxException {
		byte[] nachricht = new byte[] { 0x06, 0x30, 0x30, 0x31, 0x0D, 0x0A };
		device.write(nachricht);
		System.out.println("Programming Mode");
	}

	public void sendRequest(MESSWERT m) throws FTD2xxException {
		device.write(m.getRequest());
		System.out.println("Request " + m.getObis() + " " + m.toString());
	}

	int readData() throws FTD2xxException {
		return device.read();
	}
}
