package de.grueter.sgcheck.client.emu;
public enum MESSWERT {
	
	Seriennummer("96.1.0", new byte[]{1,82,49,2,57,54,46,49,46,48,40,41,3}, "Seriennummer"),
	Text("128.128", new byte[]{1,82,49,2,49,50,56,46,49,50,56,40,41,3}, "Text"),
	Spannung("96.1.0", new byte[]{1,82,49,2,49,50,46,55,46,48,40,41,3}, "Spannung");
  
	private byte[] request;
	private String obis, einheit;
	
	private MESSWERT(String obis, byte[] request, String einheit){
		this.obis=obis;
		this.request=request;
		this.einheit=einheit;
	}

	public byte[] getRequest() {
		return request;
	}

	public String getObis() {
		return obis;
	}

	public String getEinheit() {
		return einheit;
	}
}
