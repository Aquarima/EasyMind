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

package com.easymind.api;

import com.easymind.api.entities.Project;
import com.easymind.api.entities.Settings;
import com.easymind.api.events.Event;
import com.easymind.api.hook.EventHandler;
import com.easymind.api.managers.WindowManager;
import com.easymind.api.behaviors.Identifiable;
import com.easymind.api.utils.cache.ProjectCacheStore;
import com.easymind.api.views.Window;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Connection;
import java.util.List;

/**
 * The application core where all parts of the API can be accessed starting from this class.
 */

public interface EasyMind {

    /**
     * Gets the used {@link com.easymind.api.entities.Settings Settings} used for the current session.
     *
     * @return Non-null settings for this EasyMind instance.
     */

    @Nonnull
    Settings getSettings();

    /**
     * Sets the {@link com.easymind.api.entities.Settings Settings} used for the current session.
     *
     * @throws java.lang.NullPointerException
     *         If settings is null.
     *
     * @param settings
     *        The settings to set for this EasyMind instance.
     *
     */

    void setSettings(@Nonnull Settings settings);

    /**
     * Saves as a <i>JSON</i> file the current {@link com.easymind.api.entities.Settings Settings} state into the application data folder.
     */

    void saveSettings();

    /**
     * Opens the given {@link com.easymind.api.views.Window Window}.
     *
     * @throws java.lang.NullPointerException
     *         If window is null.
     *
     * @param window
     *        The window to close;
     */

    void open(@Nonnull Window window);

    /**
     * Closes the given {@link com.easymind.api.views.Window Window}.
     *
     * @throws java.lang.NullPointerException
     *         If window is null.
     *
     * @param window
     *        The window to close.
     *
     */

    void close(@Nonnull Window window);

    /**
     * Gets the container of all created {@link com.easymind.api.views.Window Window} used for this EasyMind instance.
     *
     * @return Non-null window manager for this EasyMind instance.
     */

    @Nonnull
    WindowManager getWindowManager();

    /**
     * Sets the {@link com.easymind.api.managers.WindowManager WindowManager} for this EasyMind instance.
     *
     * @throws java.lang.NullPointerException
     *         If windowManager is null.
     *
     * @param windowManager
     *        The window manager to use for this EasyMind instance.
     *
     */

    void setWindowManager(@Nonnull WindowManager windowManager);

    /**
     * Gets the database {@link java.sql.Connection Connection} used for this EasyMind instance.
     *
     * @return The database connection for this EasyMind instance.
     */

    @Nonnull
    Connection getConnection();

    /**
     * Sets the database {@link java.sql.Connection Connection} to use for this EasyMind instance.
     *
     * @throws java.lang.NullPointerException
     *         If connection is null.
     *
     * @param connection
     *        The database connection to use for this EasyMind instance.
     */

    void setConnection(@Nonnull Connection connection);

    /**
     * Returns the {@link com.easymind.api.utils.cache.ProjectCacheStore ProjectCacheStore} used for this EasyMind instance.
     *
     * @return Non-null project cache store for this EasyMind instance.
     */

    @Nonnull
    ProjectCacheStore getProjectCacheStore();

    /**
     * Defines the {@link com.easymind.api.utils.cache.ProjectCacheStore ProjectCacheStore} to use for this EasyMind instance.
     *
     * @throws java.lang.NullPointerException
     *         If project cache store is null.
     *
     * @param projectCacheStore
     *        The project cache store to use for this EasyMind instance.
     */

    void setProjectCacheStore(@Nonnull ProjectCacheStore projectCacheStore);

    /**
     * Constructs and save a new {@link com.easymind.api.entities.Project Project} with the provided parameters using
     * the current cache store.
     *
     * @param name
     *        The name for the resulting project.
     *
     * @param description
     *        The description for the resulting project.
     *
     * @throws java.lang.NullPointerException
     *         If name or description is null.
     *
     * @see ProjectCacheStore#writeThrough(Identifiable)
     *
     * @return Non-null resulting project.
     */

    @Nonnull
    Project createProject(@Nonnull String name, @Nonnull String description);

    /**
     * Saves the given {@link com.easymind.api.entities.Project Project} using the current cache store.
     *
     * @see #saveProjectById(String)
     *
     * @see ProjectCacheStore#writeThrough(Identifiable)
     *
     * @throws java.lang.NullPointerException
     *         If project is null.
     *
     * @param project
     *        The project to save.
     */

    boolean saveProject(@Nonnull Project project);

    /**
     * Saves the {@link com.easymind.api.entities.Project Project} matching the given the id using the current cache store.
     *
     * @see #saveProject(Project)
     *
     * @see ProjectCacheStore#writeThrough(Identifiable)
     *
     * @throws java.lang.NullPointerException
     *         If id is null.
     *
     * @throws java.lang.IllegalArgumentException
     *         If no project is matching the id.
     *
     * @param id
     *        The target project id to save.
     */

    boolean saveProjectById(@Nonnull String id);

    /**
     * Deletes the given {@link com.easymind.api.entities.Project Project} using the current cache store.
     *
     * @see ProjectCacheStore#writeThrough(Identifiable)
     *
     * @throws java.lang.NullPointerException
     *         If project is null.
     *
     * @param project
     *        The project to delete.
     */

    boolean deleteProject(@Nonnull Project project);

    /**
     * Deletes the {@link com.easymind.api.entities.Project Project} matching the given id using the current cache store.
     *
     * @see ProjectCacheStore#writeThrough(Identifiable)
     *
     * @throws java.lang.NullPointerException
     *         If id is null.
     *
     * @throws java.lang.IllegalArgumentException
     *         If no project is matching the id.
     *
     * @param id
     *        The target project id to delete.
     */

    boolean deleteProjectById(@Nonnull String id);

    /**
     * Gets a list of all {@link com.easymind.api.entities.Project Project} using the current cache store.
     * <br>This copies the backing task cache store into a list.
     *
     * @see ProjectCacheStore#asList()
     *
     * @return Possibly-empty immutable list of all projects from the cache store.
     */

    @Nonnull
    List<Project> getProjects();

    /**
     * Retrieves the {@link com.easymind.api.entities.Project Project} matching the given id using the current cache store.
     *
     * @see ProjectCacheStore#getElementById(String)
     *
     * @throws java.lang.NullPointerException
     *         If id is null.
     *
     * @param id
     *        The target project id to get.
     *
     * @return Possibly-null project with the given id.
     */

    @Nullable
    Project getProjectById(@Nonnull String id);

    /**
     * Retrieves all {@link com.easymind.api.entities.Project Project} matching the given name ignoring the name case
     * if specified using the current cache store.
     *
     * @see ProjectCacheStore#getElementsByName(String, boolean)
     *
     * @throws java.lang.NullPointerException
     *         If name is null.
     *
     * @param name
     *        The target project name to get.
     *
     * @param ignoreCase
     *        To ignore case when comparing the names.
     *
     * @return Possibly-empty immutable list of projects with the given the name.
     */

    @Nonnull
    List<Project> getProjectsByName(@Nonnull String name, boolean ignoreCase);

    /**
     * Retrieves all {@link com.easymind.api.entities.Project Project} matching the given name without
     * ignoring the case using the current cache store.
     *
     * @see #getProjectsByName(String, boolean)
     *
     * @throws java.lang.NullPointerException
     *         If name is null.
     *
     * @param name
     *        The targets name.
     *
     * @return Possibly-empty immutable list of projects with the given name.
     */

    @Nonnull
    List<Project> getProjectsByName(@Nonnull String name);

    /**
     * Retrieves all {@link com.easymind.api.entities.Project Project} matching the given tags.
     *
     * @param tags
     *        The targets tags.
     *
     * @return Possibly-empty immutable list of projects with the given tags.
     */

    @Nonnull
    List<Project> getProjectsByTags(@Nonnull List<String> tags);

    /**
     * Handles the provided event.
     *
     * @param event
     *        The event to handle.
     */

    void handle(@Nonnull Event event);

    /**
     * Returns the {@link com.easymind.api.hook.EventHandler EventHandler} used for the current session.
     *
     * @return The EventHandler used for the current session.
     */

    @Nonnull
    EventHandler getEventHandler();

    /**
     * Sets the {@link com.easymind.api.hook.EventHandler EventHandler} to use for this EasyMind instance.
     *
     * @param eventHandler
     *        The EventHandler to use for this EasyMind instance.
     */

    void setEventHandler(@Nonnull EventHandler eventHandler);

    /**
     * Translates the given key if exist.
     *
     * @param key
     *        The key to get translation.
     *
     *
     * @return Possibly-null translation for the given key or null if there is not.
     */

    String translate(@Nonnull String key);

    /**
     * Translates each given String elements.
     * If there is no translation for an element, it will not change.
     *
     * @param str
     *        The String to get translation.
     *
     *
     * @return Possibly-null translation for the given String.
     */

    String translateAll(@Nonnull String str);
}
