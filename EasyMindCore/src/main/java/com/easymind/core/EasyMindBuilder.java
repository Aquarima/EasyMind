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

package com.easymind.core;

import com.easymind.api.EasyMind;
import com.easymind.api.entities.AbstractBuilder;
import com.easymind.api.entities.Settings;
import com.easymind.api.hook.EventHandler;
import com.easymind.api.managers.WindowManager;
import com.easymind.core.entities.SettingsImpl;
import com.easymind.core.hook.EventHandlerImpl;
import com.easymind.core.managers.WindowManagerImpl;
import com.easymind.core.utils.cache.ProjectCacheStoreImpl;
import com.easymind.core.data.ProjectAccessObject;

import javax.annotation.Nonnull;
import java.sql.Connection;

public class EasyMindBuilder extends AbstractBuilder<EasyMind> {

    private Settings settings;
    private WindowManager windowManager;
    private Connection connection;
    private EventHandler eventHandler;

    public EasyMindBuilder setSettings(@Nonnull Settings settings) {
        this.settings = settings;
        return this;
    }

    public EasyMindBuilder setWindowManager(@Nonnull WindowManager windowManager) {
        this.windowManager = windowManager;
        return this;
    }

    public EasyMindBuilder setConnection(@Nonnull Connection database) {
        this.connection = database;
        return this;
    }

    public EasyMindBuilder setEventHandler(@Nonnull EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        return this;
    }

    @Override
    public EasyMind build() {

        final EasyMind easyMind = new EasyMindImpl();

        if (windowManager == null) {
            windowManager = new WindowManagerImpl();
        }

        if (settings == null) {
            settings = new SettingsImpl();
        }

        if (eventHandler == null) {
            eventHandler = new EventHandlerImpl();
        }

        ProjectAccessObject pao = new ProjectAccessObject(connection);

        easyMind.setSettings(settings);
        easyMind.setWindowManager(windowManager);
        easyMind.setConnection(connection);
        easyMind.setProjectCacheStore(new ProjectCacheStoreImpl(pao));
        easyMind.setEventHandler(eventHandler);

        return easyMind;
    }
}
