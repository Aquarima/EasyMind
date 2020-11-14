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

package com.easymind.core.managers;

import com.easymind.api.managers.WindowManager;
import com.easymind.api.views.Window;
import javafx.stage.Stage;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class WindowManagerImpl implements WindowManager {

    private final List<Window> windows;

    public WindowManagerImpl() {
        this.windows = new ArrayList<>();
    }

    @Override
    public void subscribe(@Nonnull Window window) {

        Stage stage = window.getStage();

        if (stage == null) {
            stage = window.load();
        }

        stage.setOnCloseRequest(event -> unsubscribe(window));

        window.open();
        windows.add(window);
    }

    @Override
    public void unsubscribe(@Nonnull Window window) {
        window.close();
        windows.remove(window);
    }

    @Nonnull
    @Override
    public List<Window> getWindows() {
        return windows;
    }
}
