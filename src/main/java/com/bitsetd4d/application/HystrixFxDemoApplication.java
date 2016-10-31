package com.bitsetd4d.application;

import com.bitsetd4d.view.SimpleViewController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HystrixFxDemoApplication extends Application {

    private static double WIDTH = 500;
    private static double HEIGHT = 500;

    private Scene mainScene;
    private SimpleViewController controller;

    public static void main(String[] args) {
        Application.launch(HystrixFxDemoApplication.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Simple Hystrix Demo");
        mainScene = new Scene(createContent(), WIDTH, HEIGHT);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public Parent createContent() throws IOException {
        controller = SimpleViewController.newInstance();
        return controller.getParent();
    }

}