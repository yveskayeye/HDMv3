package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import preferences.Config;
import preferences.ConfigStore;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


import static java.lang.System.out;

public class HDM extends Application {



    public static void main(String[] args) {
        launch(args);
    }


    Double initX, initY;

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("home.fxml"));
        Parent root = loader.load();

        dragHandler(primaryStage, root);

        primaryStage.setTitle("HDM");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    @Override
    public void stop() throws Exception {
        ConfigStore store = new ConfigStore();
        if(Config.getProxyHost() != null && Config.getProxyPort() != null){
            store.setProxyHost(Config.getProxyHost());
            store.setProxyPort(Config.getProxyPort());
            store.setStatus(Config.isStatus());

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("resources/config.ser"));
            objectOutputStream.writeObject(store);
            objectOutputStream.close();
            out.println("configuration file saved successfully");
        }else  out.println("nothing to write to config file");

    }

    public void dragHandler(Stage mainStage, Parent mainGroup){
        mainGroup.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                initX = me.getScreenX() - mainStage.getX();
                initY = me.getScreenY() - mainStage.getY();
            }
        });

        mainGroup.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                mainStage.setX(me.getScreenX() - initX);
                mainStage.setY(me.getScreenY() - initY);
            }
        });
    }

}
