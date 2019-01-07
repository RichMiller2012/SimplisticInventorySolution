package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CalculatorController implements Initializable {

	@FXML 
	private Button one;
	
	@FXML 
	private Button two;
	
	@FXML 
	private Button three;
	
	@FXML 
	private Button four;
	
	@FXML 
	private Button five;
	
	@FXML 
	private Button six;
	
	@FXML 
	private Button seven;
	
	@FXML 
	private Button eight;
	
	@FXML 
	private Button nine;
	
	@FXML 
	private Button zero;
	
	@FXML 
	private Button clear;
	
	@FXML 
	private Button back;
	
	@FXML 
	private Label input;
	
	@FXML 
	private Label charge;
	
	@FXML 
	private Label change;
	
	//private Double chargeAmount;
	
	//private Double changeAmount;
	
	//private Double inputAmount;
	
	private MainController mainController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		input.setText("0");
		charge.setText("0");
		change.setText("0");
		
	}
	
	public void init(MainController mainController) {
		this.mainController = mainController;
	}
	
	public void setChargeAmount(Double chargeAmount) {
		this.charge.setText(chargeAmount.toString());
		calculateChange();
	}
	
	public void appendInput(Event ae) {
		
		Button buttonText = (Button)ae.getSource();
		String number = buttonText.getText();
		
		if(number.equals("C")) {
			input.setText("0");
		} else if(number.equals("X")) {
			String text = input.getText();
			if(!text.equals("0")) {
				if(text.length() == 1) {
					input.setText("0");
				} else {
					input.setText(text.substring(0, text.length() - 1));
				}
			}
		} else {
			if(input.getText().equals("0")) {
				input.setText(number);
			} else {
				input.setText(input.getText() + number);
			}
		}
	
		
		/*
		if(one.isPressed()) {
			input.setText(input.getText() + "1");
		} else if(two.isP) {
			input.setText(input.getText() + "2");
		} else if(three.isPressed()) {
			input.setText(input.getText() + "3");
		}else if(four.isPressed()) {
			input.setText(input.getText() + "4");
		}else if(five.isPressed()) {
			input.setText(input.getText() + "5");
		}else if(six.isPressed()) {
			input.setText(input.getText() + "6");
		}else if(seven.isPressed()) {
			input.setText(input.getText() + "7");
		}else if(eight.isPressed()) {
			input.setText(input.getText() + "8");
		}else if(nine.isPressed()) {
			input.setText(input.getText() + "9");
		}else if(zero.isPressed()) {
			input.setText(input.getText() + "0");
		} else if(clear.isPressed()) {
			input.setText("0");
		} else if(back.isPressed()) {
			String text = input.getText();
			if(!text.equals("0")) {
				if(text.length() == 1) {
					input.setText("0");
				} else {
					input.setText(text.substring(0, text.length() - 2));
				}
			}
		}
		*/
		
		calculateChange();
	}
	
	private void calculateChange() {
		Double _input = Double.parseDouble(input.getText());
		Double _charge = Double.parseDouble(charge.getText());

		Double _change = _input - _charge;
		String _changeText = Double.toString(_change);
		
		
		if(_change > 0) {
			change.setText(_changeText);

		} else {
			change.setText("0");
		}
		
	}
	
	
	
	
}
 