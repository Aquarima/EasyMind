package com.easymind.api.views;

import com.easymind.api.EasyMind;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.annotation.Nonnull;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class Window implements View {

    protected final EasyMind api;

    protected Stage stage;

    @FXML
    protected Pane main;

    public Window(@Nonnull EasyMind api) {
        this.api = api;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.updateLanguage(api.getSettings().getLanguage());
    }

    /**
     * Loads this view and its {@link javafx.stage.Stage Stage} and registers this controller to the current
     * {@link com.easymind.api.managers.WindowManager WindowManager}.
     *
     * @return This application window stage.
     */

    public abstract Stage load();

    /**
     * Opens this window.
     */

    public void open() {
        stage.show();
    }

    /**
     * Closes this window.
     */

    public void close() {
        stage.close();
    }

    @Override
    public void updateStyle(@Nonnull String url) {
        main.getStylesheets()
                .add(getClass().getResource(url).toExternalForm());
    }

    @Override
    public EasyMind getEasyMind() {
        return api;
    }

    /**
     * Gets the this window {@link javafx.stage.Stage Stage}.
     *
     * @return This window stage.
     */

    public Stage getStage() {
        return stage;
    }
}
