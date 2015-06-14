
public interface EventListWindow extends ListDisplayer {
	/**
	 * Wywo�uje przypomnienie o zdarzeniu w formie okna komunikatu oraz d�wi�ku.
	 * @param ev Przypominane zdarzenie
	 * @param time Czas do zdarzenia.
	 */
	void remindEventMessage(final Event ev, final int time);
}
