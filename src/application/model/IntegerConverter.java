package application.model;

import application.ui.model.AlertDialog;
import javafx.util.StringConverter;

public class IntegerConverter extends StringConverter<Integer> {

	@Override
	public String toString(Integer integer) {
		return String.valueOf(integer);
	}

	@Override
	public Integer fromString(String string) {
		Integer integer = -1;
		
		try {
			integer = Integer.parseInt(string);
			if (integer < 0) {
				throw new RuntimeException();
			}
		} catch (NumberFormatException nfe) {
			AlertDialog.display("Format error", 
					"Please input an integer number");
		} catch (RuntimeException re) {
			AlertDialog.display("Invalidate number", 
					"Please input a positive integer number");
		}
		
		return integer;
	}

}
