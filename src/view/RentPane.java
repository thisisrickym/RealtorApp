package view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.ComboBox;
import model.RentalProperty;
import controller.FinalRentButtonListener;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RentPane {

	private BorderPane pane;
	private RentalProperty property;
	private FlowPane rentPane;
	private static Stage rentStage;
	TextField custID;
	
	Label rentLabel = new Label("Select Check-In Date");
	
	ComboBox<Integer> cboRentMonth = new ComboBox<>();
	ComboBox<Integer> cboRentDay = new ComboBox<>();
	ComboBox<Integer> cboRentYear = new ComboBox<>();
	
	Label returnLabel = new Label("Select Check-Out Date");
	ComboBox<Integer> cboReturnMonth = new ComboBox<>();
	ComboBox<Integer> cboReturnDay = new ComboBox<>();
	ComboBox<Integer> cboReturnYear = new ComboBox<>();
	
	Button finalRentButton = new Button("RENT");
	
	public RentPane(RentalProperty property) {
		this.property = property;
		pane = new BorderPane();
		
		
		
		setComboBoxs();
		setCenter();
		
		Scene scene = new Scene(pane, 400, 300);
		
		rentStage = new Stage();
		rentStage.setTitle("Rent " + property.getFormattedAddress());
		rentStage.setScene(scene);
		rentStage.show();
	}
	private void setComboBoxs() {
		for(int i = 0; i < 31; i++) {
			if(i < 12) {
				cboRentMonth.getItems().add(i+1);
				cboReturnMonth.getItems().add(i+1);
			}
			cboRentDay.getItems().add(i+1);
			cboReturnDay.getItems().add(i+1);
		}
		cboRentYear.getItems().addAll(2018, 2019);
		cboReturnYear.getItems().addAll(2018, 2019);
		cboRentDay.setValue(1);
		cboRentMonth.setValue(1);
		cboRentYear.setValue(2018);
		cboReturnDay.setValue(1);
		cboReturnMonth.setValue(1);
		cboReturnYear.setValue(2018);
		
		cboRentMonth.setOnAction(new monthBoxListener(cboRentMonth, "Rent"));
		cboReturnMonth.setOnAction(new monthBoxListener(cboReturnMonth, "Return"));
	}
	private void setCenter() {
		
		rentPane = new FlowPane(Orientation.VERTICAL, 5, 15);
		GridPane custPane = new GridPane();
		custPane.add(new Label("Customer ID"), 0, 0);
		custID = new TextField();
		custPane.add(custID, 1, 0);
		GridPane rentDate = new GridPane();
		rentDate.add(new Label("Day"), 0, 0);
		rentDate.add(cboRentDay, 0, 1);
		rentDate.add(new Label("Month") , 1, 0);
		rentDate.add(cboRentMonth, 1, 1);
		rentDate.add(new Label("Year"), 2, 0);
		rentDate.add(cboRentYear, 2, 1);
		
		
		GridPane returnDate = new GridPane();
		returnDate.add(new Label("Day"), 0, 0);
		returnDate.add(cboReturnDay, 0, 1);
		returnDate.add(new Label("Month") , 1, 0);
		returnDate.add(cboReturnMonth, 1, 1);
		returnDate.add(new Label("Year"), 2, 0);
		returnDate.add(cboReturnYear, 2, 1);
		
		finalRentButton.setOnAction(new FinalRentButtonListener(cboRentDay, cboRentMonth, cboRentYear, cboReturnDay,
				cboReturnMonth, cboReturnYear, custID, property));
		rentPane.getChildren().addAll(custPane, rentLabel, rentDate, returnLabel, returnDate, finalRentButton);
		
		pane.setCenter(rentPane);
		
	}
	public static void close() {
		rentStage.close();
	}
	public void febUpdate(String rentOrReturn) {
		switch(rentOrReturn) {
		case "Rent":
			cboRentDay.getItems().clear();
			for (int i = 0; i < 28; i++) {
				cboRentDay.getItems().add(i+1);
			}
			cboRentDay.setValue(1);
			break;
		case "Return":
			cboReturnDay.getItems().clear();
			for (int i = 0; i < 28; i++) {
				cboReturnDay.getItems().add(i+1);
			}
			cboReturnDay.setValue(1);
			break;
		default:
			break;
		}
	}
	
	public void thirtyUpdate(String rentOrReturn) {
		switch(rentOrReturn) {
		case "Rent":
			cboRentDay.getItems().clear();
			for (int i = 0; i < 30; i++) {
				cboRentDay.getItems().add(i+1);
			}
			cboRentDay.setValue(1);
			break;
		case "Return":
			cboReturnDay.getItems().clear();
			for (int i = 0; i < 30; i++) {
				cboReturnDay.getItems().add(i+1);
			}
			cboReturnDay.setValue(1);
			break;
		default:
			break;
		}
	}
	
	public void thirtyOneUpdate(String rentOrReturn) {
		switch(rentOrReturn) {
		case "Rent":
			cboRentDay.getItems().clear();
			for (int i = 0; i < 31; i++) {
				cboRentDay.getItems().add(i+1);
			}
			cboRentDay.setValue(1);
			break;
		case "Return":
			cboReturnDay.getItems().clear();
			for (int i = 0; i < 31; i++) {
				cboReturnDay.getItems().add(i+1);
			}
			cboReturnDay.setValue(1);
			break;
		default:
			break;
		}
	}
	private class monthBoxListener implements EventHandler<ActionEvent> {
		
		private ComboBox<Integer> monthComboBox;
		private String rentOrReturn;
		
		public monthBoxListener(ComboBox<Integer> monthComboBox, String rentOrReturn) {
			this.monthComboBox = monthComboBox;
			this.rentOrReturn = rentOrReturn;
		}
		
		public void handle(ActionEvent event) {
			int month = monthComboBox.getValue();
			switch(month) {
			case 2:
				febUpdate(rentOrReturn);
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				thirtyUpdate(rentOrReturn);
				break;
			default:
				thirtyOneUpdate(rentOrReturn);
				break;
			}
		}
	}
}
