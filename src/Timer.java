
public class Timer extends Thread {
	
	private boolean active = true;
	private Tickable ticker;
	private int time = 1000;
	
	public Timer(Tickable t, int timeInMilisecs){
		ticker = t;
		time = timeInMilisecs;
	}
	
	@Override
	public void run() {
		while(active){
			ticker.timeTick();
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("ClockStop");
	}
	
	public void setActive(boolean b){
		active = b;
	}
}
