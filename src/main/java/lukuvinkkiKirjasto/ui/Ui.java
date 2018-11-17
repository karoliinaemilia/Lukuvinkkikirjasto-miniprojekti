
package lukuvinkkiKirjasto.ui;

import lukuvinkkiKirjasto.database.Database;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lukuvinkkiKirjasto.dao.KirjaDao;

public class Ui extends Application {
    
    private Scene mainScene;
    
    @Override
    public void init() throws Exception {
        Database database = new Database("jdbc:sqlite:LukuvinkkiKirjasto.db");
        
        KirjaDao kirjaDao = new KirjaDao(database);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox mainPane = new VBox(10);
        HBox inputPane = new HBox(10);
        
        mainScene = new Scene(mainPane, 300, 250);  
        
        primaryStage.setTitle("Lukuvinkkikirjasto");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
