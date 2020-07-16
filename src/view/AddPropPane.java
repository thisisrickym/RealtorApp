package view;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.DataBaseToPropList;
import controller.FinalAddPropButtonListener;

public class AddPropPane {

	private BorderPane pane;
	private DataBaseToPropList dbConnect;
	private static Stage propertyStage;
	
	public AddPropPane(DataBaseToPropList dbConnect){
		this.dbConnect = dbConnect;
		
		pane = new BorderPane();
		
		pane.setTop(new MainMenuBar(dbConnect));
		
		setInfoGather();
		
		Scene scene = new Scene(pane, 550, 350);
		
		propertyStage = new Stage();
		propertyStage.setTitle("Add Property"); // Set the stage title
		propertyStage.setScene(scene); // Place the scene in the stage
		propertyStage.show();
	}
	
	private void setInfoGather() {
		GridPane grid = new GridPane();
		
		grid.add(new Label("ENTER PROPERTY INFO BELOW"), 0, 0);
		grid.add(new Label("Property Type:"), 0, 1);
		ComboBox<String> cboPropType = new ComboBox<String>();
		cboPropType.getItems().addAll("Apartment", "Premium Suite");
		cboPropType.setValue("Apartment");
		grid.add(cboPropType, 1, 1);
		grid.add(new Label("Street Number:"), 0, 2);
		TextField streetNumber = new TextField();
		grid.add(streetNumber, 1, 2);
		grid.add(new Label("Street Name:"), 0, 3);
		TextField streetName = new TextField();
		grid.add(streetName, 1, 3);
		grid.add(new Label("Suburb:"), 0, 4);
		TextField suburb = new TextField();
		grid.add(suburb, 1, 4);
		grid.add(new Label("Number of Bed Rooms:"), 0, 5);
		ComboBox<Integer> bedRooms = new ComboBox<Integer>();
		bedRooms.getItems().addAll( 1, 2, 3);
		bedRooms.setValue(1);
		grid.add(bedRooms, 1, 5);
		grid.add(new Label("Image File Name:"), 0, 6);
		TextField imgFile = new TextField();
		grid.add(imgFile, 1, 6);
		
		FlowPane descriptionPane = new FlowPane(Orientation.VERTICAL, 5, 15);
		descriptionPane.setPadding(new Insets(11, 12, 13, 14));
		TextArea description = new TextArea();
		description.setWrapText(true);
		description.setPrefHeight(200);
		description.setPrefWidth(200);
		descriptionPane.getChildren().addAll(new Label("Description (80-100 Characters)"), description);
		
		pane.setLeft(grid);
		pane.setRight(descriptionPane);
		
		Button finalAddPropButton = new Button("Add Property");
		finalAddPropButton.setOnAction(new FinalAddPropButtonListener(cboPropType, streetNumber, streetName, suburb,
																	bedRooms, imgFile, description, dbConnect));
		
		pane.setBottom(finalAddPropButton);
	}
	
	public static void close() {
		propertyStage.close();
	}
	
}
