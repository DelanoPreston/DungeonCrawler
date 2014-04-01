package Settings;

public class Log {

	public static void logMessage(int messageType, String message) {
		if (messageType == Key.logSystemManagement) {
			savePrintMesage(message);
		} else if (messageType == Key.logEntityManagement) {
			savePrintMesage(message);
		} else if (messageType == Key.logComponentManagement) {
			savePrintMesage(message);
		}
	}

	public static void savePrintMesage(String message) {
		// save or not
		System.out.println(message);
	}
}
