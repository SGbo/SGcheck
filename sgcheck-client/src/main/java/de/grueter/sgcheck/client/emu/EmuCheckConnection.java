package de.grueter.sgcheck.client.emu;

import com.ftdichip.ftd2xx.DataBits;
import com.ftdichip.ftd2xx.Device;
import com.ftdichip.ftd2xx.DeviceDescriptor;
import com.ftdichip.ftd2xx.FTD2xxException;
import com.ftdichip.ftd2xx.Parity;
import com.ftdichip.ftd2xx.Port;
import com.ftdichip.ftd2xx.Service;
import com.ftdichip.ftd2xx.StopBits;

/**
 * This is a class
 */
public class EmuCheckConnection {
	/**
	 * this is a device
	 */
	private final transient Device device;
	/*
	 * this is a port
	 */
	private final transient Device[] deviceArray;

	public EmuCheckConnection() throws FTD2xxException {
		Port port;
		deviceArray = Service.listDevices();

		device = getDeviceByProductId(49632);

		// open device
		device.open();

		port = device.getPort();
		// 7 data bits, 1 stop bit, even parity
		port.setDataCharacteristics(DataBits.DATA_BITS_7, StopBits.STOP_BITS_1, Parity.EVEN);

		// baudrate
		port.setBaudRate(300);
	}

	private Device getDeviceByProductId(final int productId) throws FTD2xxException {
		Device res = null;
		for (final Device dev : deviceArray) {
			final DeviceDescriptor descriptor = dev.getDeviceDescriptor();
			if (descriptor.getProductID() == productId) {
				res = dev;
				break;
			}
		}

		return res;
	}

	public void connect() throws FTD2xxException {
		final byte[] start = new byte[] { 0x2F, 0x3F, 0x21, 0x0D, 0x0A };
		device.write(start);
	}

	public void disconnect() throws FTD2xxException {
		final byte[] ende = new byte[] { 0x01, 0x42, 0x30, 0x03 };
		device.write(ende);
		device.close();
	}

	public void close() throws FTD2xxException {
		if (device != null) {
			device.close();
		}
	}

	public void sendProgrammingMode() throws FTD2xxException {
		final byte[] nachricht = new byte[] { 0x06, 0x30, 0x30, 0x31, 0x0D, 0x0A };
		device.write(nachricht);
	}

	public void sendRequest(final MESSWERT messwert) throws FTD2xxException {
		device.write(messwert.getRequest());
	}

	public int readData() throws FTD2xxException {
		return device.read();
	}
}
