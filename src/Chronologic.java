import java.util.Date;


public interface Chronologic {
	static final int i = 3;
	String getDateHour();
	void setDate(long dataa);
	void setDate (Date dat);
	void setDate (String dat) throws DateFormatException;
	void setDateHour(String dat) throws DateFormatException;
}
