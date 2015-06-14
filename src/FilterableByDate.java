
public interface FilterableByDate {
	/**
	 * Filtruje przechowywane zdarzenia, daj¹c dostêp do wszystkich w podanym przedziale czasowym.
	 * @param date1 Minimalna granica filtrowanych zdarzeñ
	 * @param date2 Maksymalna granica filtrowanych zdarzeñ
	 */
	void filterByDate(java.util.Date date1, java.util.Date date2);
	
	/**
	 * Cofa filtrowanie
	 */
	void defilter();
	
	/**
	 * sprawdza, czy kontener jest po filtrowaniu.
	 * @return True - kontener jest po filtorowaniu. False - kontener jest w stanie standardowym.
	 */
	boolean getFiltered();
	
	/**
	 * Usuwa wszystkie obiekty przed podan¹ dat¹.
	 * @param date Data, przed któr¹ zostan¹ skasowane zdarzenia.
	 */
	public void deleteBefore(java.util.Date date);
}
