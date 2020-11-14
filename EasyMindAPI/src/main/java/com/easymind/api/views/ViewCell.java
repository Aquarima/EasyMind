package com.easymind.api.views;

import com.easymind.api.EasyMind;
import com.easymind.api.utils.Resources;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @param <I>
 *         The item type.
 */

public abstract class ViewCell<I> implements View {

    protected final EasyMind api;

    protected I item;

    @FXML
    protected Pane main;

    public ViewCell(@Nonnull EasyMind api) {
        this.api = api;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.updateLanguage(api.getSettings().getLanguage());
    }

    @Override
    public void updateStyle(@Nonnull String url) {
        main.getStylesheets()
                .add(Resources.load(url).toExternalForm());
    }

    /**
     * Updates the current cell with the provided item.
     *
     * @param item
     *        The item to update to this cell.
     */

    public abstract void updateCell(@Nonnull I item);

    @Override
    public EasyMind getEasyMind() {
        return api;
    }

    /**
     * Returns the cell current item.
     *
     * @return Possibly-null cell current item.
     */

    @Nullable
    public I getItem() {
        return item;
    }
}
