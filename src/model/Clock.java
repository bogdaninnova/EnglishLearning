package model;

import controller.Controller;
import view.secondary.Observer;

import java.util.Timer;
import java.util.TimerTask;

public class Clock implements Observer {

	private Timer timer = new Timer();
	private boolean isItFirst = true;

	private class RemindTask extends TimerTask {
		public void run() {
			if (Model.isRunning() && !Model.isQuestedNow()) {
	        	Controller.getView().getQuestion().open(
	        			Controller.getModel().getWordList().getRandomWord(
	        					Controller.getModel().getTestingList(),
	        					Controller.getModel().getSettings().isPrioritetConsidered()));
			}
		}
	}

	@Override
	public void update() {
		if (!Model.isQuestedNow()) {
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