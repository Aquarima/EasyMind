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
import com.easymind.api.entities.Project;
import com.easymind.api.entities.Settings;
import com.easymind.api.events.Event;
import com.easymind.api.hook.EventHandler;
import com.easymind.api.managers.WindowManager;
import com.easymind.api.utils.cache.ProjectCacheStore;
import com.easymind.api.views.Window;
import com.easymind.core.entities.ProjectBuilder;
import com.easymind.core.utils.AppUtils;
import com.easymind.core.utils.IOUtils;
import com.easymind.core.utils.JsonUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.file.Path;
import java.sql.Connection;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

public class EasyMindImpl implements EasyMind {

    private ProjectCacheStore projectCacheStore;
    private WindowManager windowManager;
    private Connection database;
    private Settings settings;
    private EventHandler eventHandler;

    @Nonnull
    @Override
    public Settings getSettings() {
        return settings;
    }

    @Override
    public void setSettings(@Nonnull Settings settings) {
        this.settings = Objects.requireNonNull(settings);
    }

    @Override
    public void saveSettings() {
        IOUtils.write(JsonUtils.toJson(settings), Path.of(AppUtils.DIRECTORY + "/settings.json"));
    }

    @Override
    public void open(@Nonnull Window window) {
        windowManager.subscribe(window);
    }

    @Override
    public void close(@Nonnull Window window) {
        windowManager.unsubscribe(window);
    }

    @Nonnull
    @Override
    public WindowManager getWindowManager() {
        return windowManager;
    }

    @Override
    public void setWindowManager(@Nonnull WindowManager windowManager) {
        this.windowManager = Objects.requireNonNull(windowManager);
    }

    @Nonnull
    @Override
    public Connection getConnection() {
        return database;
    }

    @Override
    public void setConnection(@Nonnull Connection connection) {
        this.database = Objects.requireNonNull(connection);
    }

    @Nonnull
    @Override
    public ProjectCacheStore getProjectCacheStore() {
        return projectCacheStore;
    }

    @Override
    public void setProjectCacheStore(@Nonnull ProjectCacheStore projectCacheStore) {
        this.projectCacheStore = Objects.requireNonNull(projectCacheStore);
    }

    @Nonnull
    @Override
    public Project createProject(@Nonnull String name, @Nonnull String description) {

        final Project project = new ProjectBuilder(this)
                .setName(name)
                .setDescription(description)
                .build();

        projectCacheStore.writeThrough(project);

        return project;
    }

    @Override
    public boolean saveProject(@Nonnull Project project) {
        return projectCacheStore.writeThrough(project);
    }

    @Override
    public boolean saveProjectById(@Nonnull String id) {

        final Project project = getProjectById(id);

        if (project == null) {
            throw new IllegalArgumentException("Could not find project for id='" + id + "'");
        }

        return saveProject(project);
    }

    @Override
    public boolean deleteProject(@Nonnull Project project) {
        return projectCacheStore.deleteThrough(project);
    }

    @Override
    public boolean deleteProjectById(@Nonnull String id) {

        final Project project = getProjectById(id);

        if (project == null) {
            throw new IllegalArgumentException("Could not find project for id='" + id + "'");
        }

        return deleteProject(project);
    }

    @Nonnull
    @Override
    public List<Project> getProjects() {
        return projectCacheStore.asList();
    }

    @Nullable
    @Override
    public Project getProjectById(@Nonnull String id) {
        return projectCacheStore.getElementById(id);
    }

    @Nonnull
    @Override
    public List<Project> getProjectsByName(@Nonnull String name, boolean ignoreCase) {
        return projectCacheStore.getElementsByName(name, ignoreCase);
    }

    @Nonnull
    @Override
    public List<Project> getProjectsByName(@Nonnull String name) {
        return getProjectsByName(name, false);
    }

    @Nonnull
    @Override
    public List<Project> getProjectsByTags(@Nonnull List<String> tags) {
        return projectCacheStore.getElementsByTags(tags);
    }

    @Override
    public void handle(@Nonnull Event event) {
        eventHandler.handle(event);
    }

    @Nonnull
    @Override
    public EventHandler getEventHandler() {
        return eventHandler;
    }

    @Override
    public void setEventHandler(@Nonnull EventHandler eventHandler) {
        this.eventHandler = Objects.requireNonNull(eventHandler);
    }

    @Override
    public String translate(@Nonnull String key) {

        try {

            ResourceBundle bundle = settings.getLanguage()
                    .getResourceBundle();

            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    @Override
    public String translateAll(@Nonnull String str) {

        String[] args = str.split(" ");

        StringBuilder builder = new StringBuilder();

        for (String arg : args) {

            String translation = translate(arg);

            if (translation == null) {
                translation = arg;
            }

            builder.append(translation + " ");
        }

        return builder.toString();
    }
}
