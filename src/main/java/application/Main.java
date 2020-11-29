package application;

import controller.MainWindowView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.AnimalApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class Main extends Application {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private application.Application application;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() {
        logger.debug("initialize application");
        application = ApplicationFactory.createApplication(new AnimalApplication());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        logger.debug("startup application");

        // Load root layout from fxml file.
        URL resource = Main.class.getResource("../view/MainWindowView.fxml");

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(resource);
        loader.setControllerFactory(application.getFxmlControllerFactory());

        VBox mainWindow = loader.load();

        Scene scene = new Scene(mainWindow);

        primaryStage.setScene(scene);

        //controller
        MainWindowView controller = loader.getController();

        primaryStage.show();
    }
}
