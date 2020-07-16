package view;

import java.util.HashMap;
import java.util.List;
import controller.CloseButtonListener;
import controller.InfoButtonListener;
import controller.RentButtonListener;
import controller.AddPropButtonListener;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.DataBaseToPropList;
import model.RentalProperty;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.FlowPane;
import javafx.event.*;

public class MainMenu extends Application {

	final DataBaseToPropList dbConnect = new DataBaseToPropList("FlexiRentDB");
	private BorderPane pane;
	private FlowPane searchPane;
	private ScrollPane scrollPane = new ScrollPane();
	private GridPane propertyList;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public void start(Stage primaryStage) {
		
		pane = new BorderPane();
		// Create MainMenuBar
		MainMenuBar mainMenuBar = new MainMenuBar(dbConnect);
		// Place nodes in the pane
		pane.setTop(mainMenuBar);
		
		setSearchPane();
		
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		setPropertyList(dbConnect.returnPropList());
		//PropertyList propertyList = new PropertyList(dbConnect.returnPropList());
		scrollPane.setContent(propertyList);
		pane.setCenter(scrollPane);
		
		Button addProp = new Button("Add Property");
		addProp.setOnAction(new AddPropButtonListener(dbConnect));
		pane.setBottom(addProp);
		
		Scene scene = new Scene(pane, 600, 400);
		primaryStage.setTitle("Flexi Rent Main Menu");// Set the stage title
		primaryStage.setOnCloseRequest(new CloseButtonListener(dbConnect));
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show();
		
	}
	
	public void setSearchPane() {
		searchPane = new FlowPane(Orientation.VERTICAL, 5, 15);
		// hGap=5,vGap = 5
		// FlowPane pane=new FlowPane(Orientation.HORIZONTAL,5,5);//hGap=5,vGap = 5
		searchPane.setPadding(new Insets(11, 12, 13, 14));
		// pane.setPadding(new Insets(30, 30, 30, 30));
		// Place nodes in the pane
		ComboBox<String> cboType = new ComboBox<>();
		cboType.getItems().addAll("Property Type", "Apartment", "Premium Suite");
		cboType.setValue("Property Type");

		ComboBox<String> cboBedRooms = new ComboBox<>();
		String[] selections = new String[3];
		for (int i = 0; i < selections.length; i++) {
			selections[i] = (i + 1) + " Bed Rooms";
		}
		cboBedRooms.getItems().addAll("Number of Bed Rooms", "1 Bed Room", "2 Bed Rooms", "3 Bed Rooms");
		cboBedRooms.setValue("Number of Bed Rooms");

		ComboBox<String> cboStatus = new ComboBox<>();
		cboStatus.getItems().addAll("Property Status", "Available", "Rented", "Under Maintenance");
		cboStatus.setValue("Property Status");

		ComboBox<String> cboSuburb = new ComboBox<>();
		List<String> suburbList = dbConnect.getSuburbList();
		cboSuburb.getItems().addAll(suburbList);
		cboSuburb.setValue("Suburb");

		Button searchButton = new Button("Search");
		searchButton.setOnAction(
				new SearchButtonListener(searchButton, cboSuburb, cboStatus, cboType, cboBedRooms));
		
		Button refreshButton = new Button ("Refresh");
		refreshButton.setOnAction(new refreshButtonHandler(refreshButton));

		searchPane.getChildren().addAll(cboType, cboBedRooms, cboStatus, cboSuburb, searchButton, refreshButton);

		pane.setLeft(searchPane);

	}
	
	public class SearchButtonListener implements EventHandler<ActionEvent> {
		
		private Button button;
		private ComboBox<String> suburb;
		private ComboBox<String> status;
		private ComboBox<String> propType;
		private ComboBox<String> bedRooms;
		private String suburbValue, statusValue, propTypeValue;
		private int bedRoomsValue;
		
		public SearchButtonListener(Button button, ComboBox<String> suburb, ComboBox<String> status,
				ComboBox<String> propType, ComboBox<String> bedRooms) {

			this.button = button;
			this.suburb = suburb;
			this.status = status;
			this.propType = propType;
			this.bedRooms = bedRooms;
		}

		@Override
		public void handle(ActionEvent event) {
			
		suburbValue = suburb.getValue(); 
		statusValue = status.getValue();
		propTypeValue =  propType.getValue();
		String bedRoomsString = bedRooms.getValue();
		try {
			bedRoomsValue = Integer.parseInt(bedRoomsString.split(" ")[0]);
		} catch (Exception e) {
			bedRoomsValue = 0;
		}
		
		setPropertyList(dbConnect.updatePropList(propTypeValue, bedRoomsValue, statusValue, suburbValue));
		scrollPane.setContent(propertyList);
		pane.setCenter(scrollPane);
		}
	}
	
	public class refreshButtonHandler implements EventHandler<ActionEvent>{
		private Button button;
		refreshButtonHandler(Button button){
			this.button = button;
		}
		@Override
		public void handle(ActionEvent event) {
			setSearchPane();
			setPropertyList(dbConnect.returnPropList());
			scrollPane.setContent(propertyList);
			pane.setCenter(scrollPane);
		}
	}
	
	public void setPropertyList(HashMap<String, RentalProperty>properties){
		propertyList = new GridPane();
		propertyList.setAlignment(Pos.CENTER);
		propertyList.setGridLinesVisible(false);
		propertyList.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		propertyList.setHgap(5.5);
		propertyList.setVgap(10.5);
		setPropList(properties);
		// Need to create NODE with contains certain number of nodes (DEPENDENT ON NUM OF PROPERTIES) and have
		// that node be the content of the scrollbar
	}
	public void setPropList(HashMap<String, RentalProperty> properties) {
		int i = 0;
		
		for (RentalProperty property: properties.values()) {
			BorderPane pane = new BorderPane();
			try {
				Image img = new Image("/images/"+property.getImgFile());
				ImageView iv = new ImageView(img);
				iv.setFitWidth(320);
				iv.setFitHeight(240);
				pane.setTop(iv);
	
//				Button button2 = new Button(property.getDetails());
//				add(button2, 1, i);
			} catch(Exception e) {
				Image imgNoFile = new Image("/images/noimage.jpg");
				ImageView ivNoFile = new ImageView(imgNoFile);
				ivNoFile.setFitWidth(320);
				ivNoFile.setFitHeight(240);
				pane.setTop(ivNoFile);
			}
			setButtonGrid(property, pane);
			setPropInfoBox(property, pane);
			propertyList.add(pane, 0, i);
			i++;
		}
	}
	
	public void setPropInfoBox(RentalProperty property, BorderPane pane) {
		GridPane grid = new GridPane();
		String address = property.getFormattedAddress();
		String description = property.getDescription().substring(0,53) + "...";
		Label addressLabel = new Label(address);
		Label descriptionLabel = new Label(description);
		grid.add(addressLabel, 0, 0);
		grid.add(descriptionLabel, 0, 1);
		pane.setCenter(grid);
		
	}
	public void setButtonGrid(RentalProperty property, BorderPane pane) {
		GridPane grid = new GridPane();
		Button info = new Button("Info");
		info.setOnAction(new InfoButtonListener(info, property, dbConnect));
		Button rent = new Button("Rent");
		rent.setOnAction(new RentButtonListener(rent, property));
		grid.add(info, 0, 0);
		grid.add(rent, 0, 1);
		pane.setRight(grid);
	}
	

}
