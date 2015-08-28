package model;

import java.util.Timer;
import java.util.TimerTask;

import view.secondary.Observer;
import controller.Controller;

public class Clock implements Observer {

	private Timer timer = new Timer();
	private boolean isItFirst = true;

	private class RemindTask extends TimerTask {
		public void run() {
			if (Controller.getModel().isRunning() && !Controller.getModel().isQuestedNow()) {
	        	Controller.getView().getQuestion().open(
	        			Controller.getModel().getWordList().getRandomWord(
	        					Controller.getModel().getTestingList(),
	        					Controller.getModel().getSettings().isPrioritetConsidered()));
			}
		}
	}

	@Override
	public void update() {
		if (!Controller.getModel().isQuestedNow()) {
			if (isItFirst) {
				timer.schedule(new RemindTask(), 0);
				isItFirst = false;
			}
			else
				timer.schedule(new RemindTask(), Controller.getModel().getSettings().getDelay());
		}
	}

	public void setFirst() {
		isItFirst = true;
	}

}