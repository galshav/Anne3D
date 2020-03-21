package anne3D.utilities;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;  

final public class Logger {
	final static private DateTimeFormatter m_DateTimeFormatter = 
			DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	final static private String m_LogFormat = "Time: %s, Level: %s, Message: %s";
	
	public static void Log(final String message, final String logLevel) {
		LocalDateTime now = LocalDateTime.now();  
		System.out.println(String.format(m_LogFormat, m_DateTimeFormatter.format(now), logLevel, message));
	}
	
	public static void Error(final String message) {
		Logger.Log(message, "Error");
	}
	
	public static void Info(final String message) {
		Logger.Log(message, "Info");
	}
	
	public static void Debug(final String message) {
		Logger.Log(message, "Debug");
	}
}
