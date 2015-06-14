
public interface EventListWindow extends ListDisplayer {
	/**
	 * Wywo³uje przypomnienie o zdarzeniu w formie okna komunikatu oraz dŸwiêku.
	 * @param ev Przypominane zdarzenie
	 * @param time Czas do zdarzenia.
	 */
	void remindEventMessage(final Event ev, final int time);
}
