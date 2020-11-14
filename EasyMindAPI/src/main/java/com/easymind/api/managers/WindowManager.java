/*
 * Copyright 2020 Exalow (Discord: Exalow#6074)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.easymind.api.managers;

import com.easymind.api.views.Window;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * A manager that manage all created {@link com.easymind.api.views.Window Window} instances which have an
 * active stage for the current session.
 */

public interface WindowManager {

    /**
     * Registers the provided {@link com.easymind.api.views.Window Window} that will be stored until getting closed.
     * <p><br>
     * <b>Info: </b>That window is automatically removed when the associated {@link javafx.stage.Stage Stage} gets closed.
     *
     * @throws java.lang.NullPointerException
     *         If window is null.
     *
     * @param window
     *        The window to add.
     */

    void subscribe(@Nonnull Window window);

    /**
     * Removes the provided {@link com.easymind.api.views.Window Window} from the current session.
     *
     * @see Window#load()
     *
     * @see Window#getStage()
     *
     * @throws java.lang.NullPointerException
     *         If window is null.
     *
     * @param window
     *        The window to remove.
     */

    void unsubscribe(@Nonnull Window window);

    /**
     * Returns a immutable list of active {@link com.easymind.api.views.Window Window}.
     *
     * @return Possibly-empty immutable list of active window.
     */

    @Nonnull
    List<Window> getWindows();
}
