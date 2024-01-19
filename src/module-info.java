module google_maps_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web; // Add this line for WebView
	requires javafx.graphics;

	
	opens application to javafx.graphics, javafx.fxml;
}
