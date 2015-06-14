import java.util.Date;


public interface Chronologic {
	static final int i = 3;
	/**
	 * @return Data zdarzenia w formacie "dd/MM/yyyy HH:mm"
	 */
	String getDateHour();
	Date getDate();
	
	/**
	 * Ustawia dat� w formacie czasu uniksowego.
	 * @param dataa liczby milisekund od 01.01.1970.
	 */
	void setDate(long dataa);
	
	/**
	 * @return Data jako liczba milisekund od 01.01.1970
	 */
	public long getMiliseconds();

	/**
	 * Ustawia dat� zdarzenia.
	 * @param dat Data jako obiekty klasy Date.
	 */
	void setDate (Date dat);
	
	/**
	 * Ustawia dat� zdarzenia.
	 * @param dat Data w formacie "dd/MM/yyyy"
	 * @throws DateFormatException Mo�liwy z�y format daty.
	 */
	void setDate (String dat) throws DateFormatException;
	
	/**
	 * Ustawia dat� zdarzenia.
	 * @param dat Data w formacie "dd/MM/yyyy HH:mm"
	 * @throws DateFormatException Podana data mo�e mie� b��dny format.
	 */
	void setDateHour(String dat) throws DateFormatException;
	
	/**
	 * @return Data w formacie "dd/MM/yyyy"
	 */
	String getDay();
	
	/**
	 * @return Godzina zdarzenia w formacie "HH:mm"
	 */
	String getHour();
}
