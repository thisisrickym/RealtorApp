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
import controller.FinalCompMaintButtonListener;

public class CompMaintPane {

	private BorderPane pane;
	private RentalProperty property;
	private FlowPane maintPane;
	private static Stage maintStage;
	
	private Label maintLabel = new Label("Please enter the date maintenance was completed.");
	
	private ComboBox<Integer> cboMaintMonth = new ComboBox<>();
	private ComboBox<Integer> cboMaintDay = new ComboBox<>();
	private ComboBox<Integer> cboMaintYear = new ComboBox<>();
	
	private Button finalCompMaintButton = new Button("Complete Maintenance");
	public CompMaintPane(RentalProperty property){
		this.property = property;
		pane = new BorderPane();
		

		
		
		setComboBoxs();
		setCenter();
		
		Scene scene = new Scene(pane, 400, 300);
		
		maintStage = new Stage();
		maintStage.setTitle("Complete maintenance on " + property.getFormattedAddress());
		maintStage.setScene(scene);
		maintStage.show();
	}
	
	private void setComboBoxs(){
		for(int i = 0; i < 31; i++) {
			if(i < 12) {
				cboMaintMonth.getItems().add(i+1);
			}
			cboMaintDay.getItems().add(i+1);
		}
		cboMaintYear.getItems().addAll(2018, 2019, 2020);
		cboMaintYear.setValue(2018);
		cboMaintDay.setValue(1);
		cboMaintMonth.setValue(1);
	}
	
	private void setCenter() {
		maintPane = new FlowPane(Orientation.VERTICAL, 5, 15);
		GridPane maintDate = new GridPane();
		maintDate.add(new Label("Day"), 0, 0);
		maintDate.add(cboMaintDay, 0, 1);
		maintDate.add(new Label("Month") , 1, 0);
		maintDate.add(cboMaintMonth, 1, 1);
		maintDate.add(new Label("Year"), 2, 0);
		maintDate.add(cboMaintYear, 2, 1);
		
		finalCompMaintButton.setOnAction(new FinalCompMaintButtonListener(cboMaintDay, cboMaintMonth, cboMaintYear, property));
		
		maintPane.getChildren().addAll(maintLabel, maintDate, finalCompMaintButton);
		pane.setCenter(maintPane);
	
	}
	
	public void febUpdate() {
		cboMaintDay.getItems().clear();
		for (int i = 0; i < 28; i++) {
			cboMaintDay.getItems().add(i+1);
		}
		cboMaintDay.setValue(1);
	}
	
	public void thirtyUpdate() {
		cboMaintDay.getItems().clear();
		for (int i = 0; i < 30; i++) {
			cboMaintDay.getItems().add(i+1);
		}
		cboMaintDay.setValue(1);
	}
	
	public void thirtyOneUpdate() {
		cboMaintDay.getItems().clear();
		for (int i = 0; i < 31; i++) {
			cboMaintDay.getItems().add(i+1);
		}
		cboMaintDay.setValue(1);
	}
	public static void close() {
		maintStage.close();
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
