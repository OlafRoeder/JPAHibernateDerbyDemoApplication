package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        URL resource = Main.class.getResource("/view/MainWindowView.fxml");

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(resource);
        loader.setControllerFactory(application.getFxmlControllerFactory());

        VBox mainWindow = loader.load();

        Scene scene = new Scene(mainWindow);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Demo");
        primaryStage.setResizable(false);
        primaryStage.getIcons().addAll(
                new Image(Main.class.getResourceAsStream("/img/info_16x16.png")),
                new Image(Main.class.getResourceAsStream("/img/info_24x24.png")),
                new Image(Main.class.getResourceAsStream("/img/info_32x32.png")),
                new Image(Main.class.getResourceAsStream("/img/info_40x40.png")),
                new Image(Main.class.getResourceAsStream("/img/info_48x48.png")),
                new Image(Main.class.getResourceAsStream("/img/info_64x64.png")),
                new Image(Main.class.getResourceAsStream("/img/info_128x128.png")),
                new Image(Main.class.getResourceAsStream("/img/info_256x256.png"))
                );

        primaryStage.show();
    }
}
