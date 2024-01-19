package application;
	
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.web.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.stream.Collectors;
import java.io.UnsupportedEncodingException;

public class Main extends Application {
	private String GOOGLE_MAPS_URL = "https://www.google.com/maps/dir/";
    String filename = "cerrik.csv";
    String filenam = "cerrik_list.csv";
    private Map<String, Node> streetNameMap = new HashMap<>();

    public static String formatAddressForGoogleMaps(String streetName) {
        try {
            // Replace spaces with '+'
            String formattedStreetName = streetName.replace(" ", "+");

            // Append ",+Tokyo,+Japan" to the address
            String formattedAddress = formattedStreetName + ",+Cërrik,+Shqipëria/";

            // URL encode the entire address
           // return URLEncoder.encode(formattedAddress, "UTF-8");
            return formattedAddress;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
	public void openGoogleMaps(Stage primaryStage, TextField startTextField, TextField endTextField) {
        primaryStage.setTitle("Google Maps Viewer");

        // Replace with your source and destination coordinates

        
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

            	//actiontarget.setText("Shortest Path: " + String.join(" -> ", path));
            } else {
            	//actiontarget.setText("No path found.");
            }
        } else {
        	//actiontarget.setText("Start or end node not found.");
        }
        // Fetch directions from Google Maps Directions API
        //waypoints = Arrays.asList("Tomorr Sinani");
        //waypoints = Arrays.asList("San Jose, CA", "Fresno, CA");

        
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.load(GOOGLE_MAPS_URL);
        
        //String directions = getDirections(source, destination, waypoints);
        //System.out.println(directions);
        //Label directionsLabel = new Label(directions);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> start(primaryStage));
        backButton.setStyle("-fx-font-size: 14;");
        backButton.setMinSize(80, 40);

        BorderPane root = new BorderPane();
        root.setCenter(webView);
        //root.setBottom(directionsLabel);

        BorderPane.setMargin(backButton, new Insets(10));
        BorderPane.setAlignment(backButton, javafx.geometry.Pos.BOTTOM_RIGHT);        
        root.setBottom(backButton);

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
        webView.getEngine().reload();

	}
    
    
	public void stageTwo(Stage primaryStage, TextField startTextField, TextField endTextField) {
        //ktu kalojm te window tjeter
    	Pane pani = new Pane();
    	Scene newScene = new Scene(pani,800,600);
		final Text actiontarget = new Text();
    	actiontarget.setFill(Color.FIREBRICK);

        Map<String, Node> nodes = Node.loadNodesFromCSV(filename, filenam, streetNameMap);

        // Example usage
        Node startNode = nodes.get(startTextField.getText());
        Node endNode = nodes.get(endTextField.getText());

        if (startNode != null && endNode != null) {
            List<String> path = Graph.aStar(startNode, endNode);

            if (path != null) {
                actiontarget.setText("Shortest Path: " + String.join(" -> ", path));
            } else {
            	actiontarget.setText("No path found.");
            }
        } else {
        	actiontarget.setText("Start or end node not found.");
        }
		Button bt2n = new Button("Kthehu");
        VBox vbox = new VBox(10);
        vbox.getChildren().add(actiontarget);
        vbox.getChildren().add(bt2n);
        pani.getChildren().add(vbox);
        vbox.setAlignment(Pos.CENTER);
        bt2n.setOnAction(e -> start(primaryStage));
		primaryStage.setScene(newScene);
		primaryStage.show();
		
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Gugëll Mapz");
			
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
			
			Scene scene = new Scene(grid,800,600);
			Text scenetitle = new Text("Mirësevjen!");
			scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			grid.add(scenetitle, 0, 0, 2, 1);
			
			Label userName = new Label("Nisja:");
			grid.add(userName, 0, 1);
			
			TextField startTextField = new TextField();
			grid.add(startTextField, 1, 1);
			
			Label pw = new Label("Mbërritja:");
			grid.add(pw, 0, 2);

			TextField endTextField = new TextField();
			grid.add(endTextField, 1, 2);
	        
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
