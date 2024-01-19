package application;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.web.*;


public class Main extends Application {
	
	private String GOOGLE_MAPS_URL = "https://www.google.com/maps/dir/";
    String filename = "cerrik.csv";
    String filenam = "cerrik_list.csv";
    private Map<String, Node> streetNameMap = new HashMap<>();

    public static String formatAddressForGoogleMaps(String streetName) {
        try {
            String formattedStreetName = streetName.replace(" ", "+");
            String formattedAddress = formattedStreetName + ",+Cërrik,+Shqipëria/";
            return formattedAddress;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
	public void openGoogleMaps(Stage primaryStage, TextField startTextField, TextField endTextField) {
        primaryStage.setTitle("Google Maps Viewer");
        
        Map<String, Node> nodes = Node.loadNodesFromCSV(filename, filenam, streetNameMap);

        // Example usage
        Node startNode = streetNameMap.get(startTextField.getText());
        Node endNode = streetNameMap.get(endTextField.getText());
        
        List<String> waypoints = null;
        if (startNode != null && endNode != null) {
            waypoints = Graph.aStar(startNode, endNode);
            System.out.println("Start: " + startTextField.getText());
            System.out.println("End: " + endTextField.getText());
            
            if (waypoints != null) {
            	GOOGLE_MAPS_URL = "https://www.google.com/maps/dir/";
            	System.out.println("Shortest Path: " + String.join(" -> ", waypoints));
                for(String street: waypoints) {
                	GOOGLE_MAPS_URL += formatAddressForGoogleMaps(street);
                }
                System.out.println("Constructed URL: " + GOOGLE_MAPS_URL);
            } else {
            	//actiontarget.setText("No path found.");
            }
        } else {
        	//actiontarget.setText("Start or end node not found.");
        }
        
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.load(GOOGLE_MAPS_URL);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> start(primaryStage));
        backButton.setStyle("-fx-font-size: 14;");
        backButton.setMinSize(80, 40);

        BorderPane root = new BorderPane();
        root.setCenter(webView);

        BorderPane.setMargin(backButton, new Insets(10));
        BorderPane.setAlignment(backButton, javafx.geometry.Pos.BOTTOM_RIGHT);        
        root.setBottom(backButton);

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
        webView.getEngine().reload();

	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Gugëll Mapz");
			
			GridPane grid = new GridPane();
			grid.setId("root"); 
			
			FileInputStream inputstream = new FileInputStream("geoguessr1.png"); 
			Image image = new Image(inputstream); 
			ImageView imageView = new ImageView(image);

	        imageView.setFitHeight(210); // Set the height of the image
	        imageView.setPreserveRatio(true);
	        
	        grid.add(imageView, 0, 0, 2, 1);
	        Text scenetitle = new Text("Mirësevjen!");
	        scenetitle.setId("title"); // Apply the title style
			//scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        grid.add(scenetitle, 0, 1, 2, 1);
	        
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
			
			Scene scene = new Scene(grid,400,400);
			scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm()); // Link the CSS file
			

			Label userName = new Label("Nisja:");
			grid.add(userName, 0, 2);
			
			TextField startTextField = new TextField();
			grid.add(startTextField, 1, 2);
			
			Label pw = new Label("Mbërritja:");
			grid.add(pw, 0, 3);

			TextField endTextField = new TextField();
			grid.add(endTextField, 1, 3);
	        
			Button btn = new Button("Navigo");
			HBox hbBtn = new HBox(10);
			hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
			hbBtn.getChildren().add(btn);
			grid.add(hbBtn, 1, 4);
			
			btn.setOnAction(e ->openGoogleMaps(primaryStage, startTextField, endTextField));			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
