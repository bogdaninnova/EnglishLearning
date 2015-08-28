package controller;

import model.Model;
import view.View;

public class Controller {
	
	private static View view;
	private static Model model;
		
	public static void main(String[] some) {
		model = new Model();
		view = new View();
	}
	
	public static View getView() {
		return view;
	}
	
	public static Model getModel() {
		return model;
	}
}