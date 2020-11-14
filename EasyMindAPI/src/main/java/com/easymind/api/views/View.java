package com.easymind.api.views;

import com.easymind.api.EasyMind;
import com.easymind.api.behaviors.Language;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import javax.annotation.Nonnull;

public interface View extends Initializable {

    /**
     * Updates the {@code View} style.
     *
     * @see Parent#getStylesheets()
     *
     * @param url
     *        The stylesheet url.
     */

    void updateStyle(@Nonnull String url);

    /**
     * Updates the {@code View} {@link com.easymind.api.behaviors.Language Language}.
     *
     * @throws java.lang.NullPointerException
     *         If language is null.
     *
     * @param language
     *        The language to set.
     *
     */

    void updateLanguage(@Nonnull Language language);

    /**
     * Gets the {@link com.easymind.api.EasyMind EasyMind} instance used by this {@code View}.
     *
     * @return the view EasyMind instance.
     */

    EasyMind getEasyMind();
}
