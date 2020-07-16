package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.RentalProperty;
import controller.FinalReturnButtonListener;

public class ReturnPane {
	
	private BorderPane pane;
	private RentalProperty property;
	private FlowPane returnPane;
	private static Stage returnStage;
	
	private Label returnLabel = new Label("Please enter the date returned");
	
	private ComboBox<Integer> cboReturnMonth = new ComboBox<>();
	private ComboBox<Integer> cboReturnDay = new ComboBox<>();
	private ComboBox<Integer> cboReturnYear = new ComboBox<>();
	
	private Button finalReturnButton = new Button("Return Property");
	
	public ReturnPane(RentalProperty property){
		this.property = property;
		pane = new BorderPane();
			
		setComboBoxs();
		setCenter();
		
		Scene scene = new Scene(pane, 400, 300);
		
		returnStage = new Stage();
		returnStage.setTitle("Return " + property.getFormattedAddress());
		returnStage.setScene(scene);
		returnStage.show();
	}
	
	private void setComboBoxs(){
		for(int i = 0; i < 31; i++) {
			if(i < 12) {
				cboReturnMonth.getItems().add(i+1);
			}
			cboReturnDay.getItems().add(i+1);
		}
		cboReturnYear.getItems().addAll(2018, 2019, 2020);
		cboReturnYear.setValue(2018);
		cboReturnDay.setValue(1);
		cboReturnMonth.setValue(1);
	}
	
	private void setCenter() {
		returnPane = new FlowPane(Orientation.VERTICAL, 5, 15);
		GridPane returnDate = new GridPane();
		returnDate.add(new Label("Day"), 0, 0);
		returnDate.add(cboReturnDay, 0, 1);
		returnDate.add(new Label("Month") , 1, 0);
		returnDate.add(cboReturnMonth, 1, 1);
		returnDate.add(new Label("Year"), 2, 0);
		returnDate.add(cboReturnYear, 2, 1);
		
		finalReturnButton.setOnAction(new FinalReturnButtonListener(cboReturnDay, cboReturnMonth, cboReturnYear, property));
		
		returnPane.getChildren().addAll(returnLabel, returnDate, finalReturnButton);
		pane.setCenter(returnPane);
	
	}
	
	public void febUpdate() {
		cboReturnDay.getItems().clear();
		for (int i = 0; i < 28; i++) {
			cboReturnDay.getItems().add(i+1);
		}
		cboReturnDay.setValue(1);
	}
	
	public void thirtyUpdate() {
		cboReturnDay.getItems().clear();
		for (int i = 0; i < 30; i++) {
			cboReturnDay.getItems().add(i+1);
		}
		cboReturnDay.setValue(1);
	}
	
	public void thirtyOneUpdate() {
		cboReturnDay.getItems().clear();
		for (int i = 0; i < 31; i++) {
			cboReturnDay.getItems().add(i+1);
		}
		cboReturnDay.setValue(1);
	}
	public static void close() {
		returnStage.close();
	}
	
	private class monthBoxListener implements EventHandler<ActionEvent> {
		
		private ComboBox<Integer> monthComboBox;
		
		public monthBoxListener(ComboBox<Integer> monthComboBox) {
			this.monthComboBox = monthComboBox;
		}
		
		public void handle(ActionEvent event) {
			int month = monthComboBox.getValue();
			switch(month) {
			case 2:
				febUpdate();
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				thirtyUpdate();
				break;
			default:
				thirtyOneUpdate();
				break;
			}
		}
	}

}
