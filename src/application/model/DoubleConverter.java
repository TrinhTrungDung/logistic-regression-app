package application.model;

import application.ui.model.AlertDialog;
import javafx.util.StringConverter;

public class DoubleConverter extends StringConverter<Double> {

	@Override
	public Double fromString(String string) {
		Double number = new Double(-1);
		
		try {
			number = Double.parseDouble(string);
			if (number < 0 || number > 1) {
				throw new RuntimeException();
			}
		} catch (NumberFormatException nfe) {
			AlertDialog.display("Format error", 
					"Please input a number");
		} catch (RuntimeException re) {
			AlertDialog.display("Invalidate number", 
					"Please input a positive number between 0 and 1");
		}
		
		return number;
	}

	@Override
	public String toString(Double num) {
		return String.valueOf(num);
	}

}
