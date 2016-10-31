package com.bitsetd4d.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SimpleViewController {

    public static SimpleViewController newInstance() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SimpleViewController.class.getResource("/simpleview.fxml"));
        try {
            AnchorPane ap = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return loader.getController();
    }

    @FXML
    private Slider tasksSlider;

    @FXML
    private Slider threadsSlider;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private ProgressIndicator identityProgressIndicator;

    @FXML
    private Label runningLabel;

    @FXML
    private TreeTableView resultsTreeTableView;

    @FXML
    private TreeTableColumn stateColumn;

    @FXML
    private TreeTableColumn timeColumn;

    @FXML
    private TreeTableColumn threadColumn;

    @FXML
    private TreeTableColumn commentColumn;

    public Slider getTasksSlider() {
        return tasksSlider;
    }

    public Slider getThreadsSlider() {
        return threadsSlider;
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getStopButton() {
        return stopButton;
    }

    public ProgressIndicator getIdentityProgressIndicator() {
        return identityProgressIndicator;
    }

    public Label getRunningLabel() {
        return runningLabel;
    }

    public TreeTableView getResultsTreeTableView() {
        return resultsTreeTableView;
    }

    public TreeTableColumn getStateColumn() {
        return stateColumn;
    }

    public TreeTableColumn getTimeColumn() {
        return timeColumn;
    }

    public TreeTableColumn getThreadColumn() {
        return threadColumn;
    }

    public TreeTableColumn getCommentColumn() {
        return commentColumn;
    }
}
