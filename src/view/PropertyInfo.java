package view;

import model.DataBaseToPropList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import model.RentalProperty;
import javafx.scene.layout.HBox;
import controller.RentButtonListener;
import controller.ReturnButtonListener;
import controller.MaintButtonListener;
import controller.CompMaintButtonListener;

public class PropertyInfo {

	private BorderPane pane;
	private RentalProperty property;
	
	public PropertyInfo(RentalProperty property, DataBaseToPropList dbConnect) {
		this.property = property;
		pane = new BorderPane();
		
		pane.setTop(new MainMenuBar(dbConnect));
		
		setLeft();
		setRight();
		setBottom();
		
		Scene scene = new Scene(pane, 550, 350);
		
		Stage propertyStage = new Stage();
		propertyStage.setTitle(property.getFormattedAddress()); // Set the stage title
		propertyStage.setScene(scene); // Place the scene in the stage
		propertyStage.show();
	}
	
	private void setRight() {
		GridPane gridPane = new GridPane();
		gridPane.setPrefHeight(350);
		gridPane.setPrefWidth(300);
		
		try {
			Image img = new Image("/images/"+property.getImgFile());
			ImageView iv = new ImageView(img);
			iv.setFitWidth(280);
			iv.setFitHeight(210);
			gridPane.add(iv, 0, 0);

		} catch(Exception e) {
			Image imgNoFile = new Image("/images/noimage.jpg");
			ImageView ivNoFile = new ImageView(imgNoFile);
			ivNoFile.setFitWidth(280);
			ivNoFile.setFitHeight(210);
			gridPane.add(ivNoFile, 0, 0);
		}
		Label textArea = new Label(property.getDescription());
		textArea.setPrefWidth(200);
		textArea.setWrapText(true);
		gridPane.add(textArea, 0, 1);
		pane.setRight(gridPane);
	}
	
	private void setBottom(){
		
		HBox hBox = new HBox(4);
		
		Button rentButton = new Button("Rent");
		rentButton.setOnAction(new RentButtonListener(rentButton, property));
		Button returnButton = new Button("Return");
		returnButton.setOnAction(new ReturnButtonListener(returnButton, property));
		Button maintButton = new Button("Maintenance");
		maintButton.setOnAction(new MaintButtonListener(maintButton, property));
		Button compMaintButton = new Button("Complete Maintenance");
		compMaintButton.setOnAction(new CompMaintButtonListener(compMaintButton, property));
		
		hBox.getChildren().addAll(rentButton, returnButton, maintButton, compMaintButton);
		hBox.setAlignment(Pos.CENTER);
		pane.setBottom(hBox);
	}
	
	private void setLeft() {
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setPrefWidth(250);
		scrollPane.setPrefHeight(350);
		
		Label textArea = new Label(property.getDetails());
		textArea.setWrapText(false);
		
		scrollPane.setContent(textArea);
		
		pane.setLeft(scrollPane);
	}
	

}
