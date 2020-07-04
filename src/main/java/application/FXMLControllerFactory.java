package application;

import controller.MainWindowController;
import javafx.util.Callback;

class FXMLControllerFactory implements Callback<Class<?>, Object> {

    private final Application application;

    FXMLControllerFactory(Application application) {
        /*package restricted constructor*/
        this.application = application;
    }

    @Override
    public Object call(Class<?> type) {

        if (type.isAssignableFrom(MainWindowController.class))
            return new MainWindowController(application);

        throw new IllegalStateException("No controller class found for " + type);
    }
}