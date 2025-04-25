package service;

public class ServiceMessage {
	
	private static ServiceMessage instance;
	
	public static ServiceMessage getInstance() {
		if (instance == null) {
			instance = new ServiceMessage();
		}
		return instance;
	}
	
	private ServiceMessage() {}

	public String createMessage (String message, String... datas) {
		String request = "{\"message\":\"" + message +"\",\"data\":{";
		for (int i = 0; i < datas.length; i++) {
			request += datas[i];
			if (i < datas.length-1) {
				request += ",";
			}
		}
		
		return request + "}}";
	}
	
	public String createObjectJson(String objectName, String value) {
		return "\"" + objectName + "\":" + value;
	}
}
