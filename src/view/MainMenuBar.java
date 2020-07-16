package view;

import javafx.scene.control.*;
import javafx.stage.*;
import javafx.event.*;
import controller.ExportListener;
import controller.ImportListener;
import controller.QuitButtonListener;
import model.DataBaseToPropList;

public class MainMenuBar extends MenuBar {
	MainMenuBar(DataBaseToPropList dbConnect){
		Stage fileStage = new Stage();
		Menu fileMenu = new Menu("File");
		
		MenuItem importMenuItem = new MenuItem("Import");
		importMenuItem.setOnAction(new ImportListener(fileStage, dbConnect));
		
		MenuItem exportMenuItem = new MenuItem("Export");
		exportMenuItem.setOnAction(new ExportListener(fileStage, dbConnect));
		
		MenuItem quitMenuItem = new MenuItem("Quit");
		quitMenuItem.setOnAction(new QuitButtonListener(dbConnect, quitMenuItem));
		
		fileMenu.getItems().addAll(importMenuItem, exportMenuItem, quitMenuItem);
		
		this.getMenus().add(fileMenu);
	}
}

class MenuItemListener implements EventHandler<ActionEvent> {

	private MenuItem menuItem;
	
	public MenuItemListener(MenuItem menuItem) {
		this.menuItem = menuItem;
	}
	
	@Override
	public void handle(ActionEvent event) {
		System.out.println("You have selected the " + menuItem.getText() + " menu item");
	}
	
}

