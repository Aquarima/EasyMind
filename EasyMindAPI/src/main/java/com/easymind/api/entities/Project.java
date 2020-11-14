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

package com.easymind.api.entities;

import com.easymind.api.behaviors.Identifiable;
import com.easymind.api.utils.cache.ProjectItemCacheStore;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;

public interface Project extends Identifiable, Comparable<Project> {

    /**
     * Gets the name for this {@code Project}.
     *
     * @return Non-null name.
     */

    @Nonnull
    String getName();

    /**
     * Sets the name for this {@code Project}.
     *
     * @param name
     *        The project name to set.
     *
     * @throws java.lang.NullPointerException
     *          If the name is null.
     *
     */

    void setName(@Nonnull String name);

    /**
     * Gets the description for this {@code Project} that give information about the project purpose.
     *
     * @return Non-null description.
     */

    @Nonnull
    String getDescription();

    /**
     * Sets the description for this {@code Project} that will gives information about it.
     *
     * @param description
     *        The description to set.
     *
     * @throws java.lang.NullPointerException
     *         If description is null.
     */

    void setDescription(@Nonnull String description);

    /**
     * Gets the tags for this {@code Project} that describes the project characteristics and allows easy grouping of items that
     * have the same characteristics.
     *
     * @return List of tags.
     */

    List<String> getTags();

    /**
     * Sets the tags for this {@code Project}.
     *
     * @param tags
     *        The tags to set.
     *
     * @throws java.lang.NullPointerException
     *         If tags is null.
     */

    void setTags(@Nonnull List<String> tags);

    /**
     * Returns if this project is marked as favorite.
     *
     * @return True, If the project is marked as favorite.
     */

    boolean isFavorite();

    /**
     * Defines if this project is marked as favorite.
     *
     * @param favorite
     *        If the project is marked as favorite.
     */

    void setFavorite(boolean favorite);

    /**
     * Returns the current {@link ProjectItemCacheStore NoteCacheStore} for this project.
     *
     * @return Non-null task cache store.
     */

    @Nonnull
    ProjectItemCacheStore<Note> getNoteCacheStore();

    /**
     * Sets the {@link ProjectItemCacheStore NoteCacheStore} to use for this project.
     *
     * @param noteCacheStore
     *        The note cache store to set.
     *
     * @throws java.lang.NullPointerException
     *         If the note cache store is null.
     */

    void setNoteCacheStore(@Nonnull ProjectItemCacheStore<Note> noteCacheStore);

    /**
     * Returns the current {@link ProjectItemCacheStore TaskCacheStore} for this project.
     *
     * @return Non-null note cache store.
     */

    @Nonnull
    ProjectItemCacheStore<Task> getTaskCacheStore();

    /**
     * Sets the {@link ProjectItemCacheStore TaskCacheStore} to use for this project.
     *
     * @param taskCacheStore
     *        The task cache store to set.
     *
     * @throws java.lang.NullPointerException
     *         If the task cache store is null.
     */

    void setTaskCacheStore(@Nonnull ProjectItemCacheStore<Task> taskCacheStore);

    /**
     * Creates and save a note using the current {@link ProjectItemCacheStore NoteCacheStore}.
     *
     * @param title
     *        The title of the resulting note.
     *
     * @throws java.lang.NullPointerException
     *         If the title is null.
     *
     * @return The resulting note.
     */

    @Nonnull
    Note createNote(@Nonnull String title);

    /**
     * Creates and save a task using the current {@link ProjectItemCacheStore TaskCacheStore}.
     *
     * @param title
     *        The title of the resulting task
     *
     * @param priority
     *        The {@link com.easymind.api.entities.Priority Priority} of the resulting task.
     *
     * @param start
     *        The start {@link java.util.Date Date} of the resulting task.
     *
     * @param end
     *        The end {@link java.util.Date Date} of the resulting task.
     *
     * @throws java.lang.NullPointerException
     *         If the tile is null.
     *
     * @return The resulting task.
     */

    @Nonnull
    Task createTask(@Nonnull String title, @Nonnull Priority priority, @Nullable Date start, @Nullable Date end);

    /**
     * Saves the {@link com.easymind.api.entities.Note Note} to this project using the current
     * {@link ProjectItemCacheStore NoteCacheStore}.
     *
     * @param note
     *        The note to save to this project.
     *
     * @throws java.lang.NullPointerException
     *         If note is null.
     *
     * @throws java.lang.IllegalStateException
     *         If this project does not own the provided note.
     */

    boolean saveNote(@Nonnull Note note);

    /**
     * Saves the {@link com.easymind.api.entities.Note Note} matching the given id to this project using the current
     * {@link ProjectItemCacheStore NoteCacheStore}.
     *
     * @param id
     *        The target note id to save to this project.
     *
     * @throws java.lang.NullPointerException
     *         If id is null.
     *
     * @throws java.lang.IllegalArgumentException
     *         If no note is matching the id.
     *
     * @see #saveNote(Note)
     */

    boolean saveNoteById(@Nonnull String id);

    /**
     * Saves the {@link com.easymind.api.entities.Task Task} to this project using the current
     * {@link ProjectItemCacheStore TaskCacheStore}.
     *
     * @param task
     *        The task to save to this project.
     *
     * @throws java.lang.NullPointerException
     *         If task is null.
     *
     * @throws java.lang.IllegalStateException
     *         If this project does not own the provided task.
     *
     * @see #getTaskById(String)
     *
     * @see ProjectItemCacheStore#writeThrough(Identifiable)
     */

    boolean saveTask(@Nonnull Task task);

    /**
     * Saves the {@link com.easymind.api.entities.Task Task} matching the given id to this project using the current
     * {@link ProjectItemCacheStore TaskCacheStore}.
     *
     * @param id
     *        The target task id to save to this project.
     *
     * @throws java.lang.NullPointerException
     *         If id is null.
     *
     * @throws java.lang.IllegalArgumentException
     *         If no task is matching the id.
     *
     * @see #saveTask(Task)
     */

    boolean saveTaskById(@Nonnull String id);

    /**
     * Deletes the provided {@link  com.easymind.api.entities.Note Note} from this project using the current
     * {@link ProjectItemCacheStore NoteCacheStore}.
     *
     * @param note
     *        The note to delete form this project.
     *
     * @throws java.lang.NullPointerException
     *         If note is null.
     *
     * @throws java.lang.IllegalStateException
     *         If this project does not own the provided note.
     *
     * @see #getNoteById(String)
     *
     * @see ProjectItemCacheStore#deleteThrough(Identifiable)
     */

    boolean deleteNote(@Nonnull Note note);

    /**
     * Deletes the {@link  com.easymind.api.entities.Note Note} matching the given id from this project using the current
     * {@link ProjectItemCacheStore NoteCacheStore}.
     *
     * @param id
     *        The target note id to delete from this project.
     *
     * @throws java.lang.NullPointerException
     *         If id is null.
     *
     * @throws java.lang.IllegalArgumentException
     *         If no note is matching the id.
     *
     * @see #getNoteById(String)
     */

    boolean deleteNoteById(@Nonnull String id);

    /**
     * Deletes the provided {@link  com.easymind.api.entities.Task Task} from this project using the current
     * {@link ProjectItemCacheStore TaskCacheStore}.
     *
     * @param task
     *        The task to delete from this project..
     *
     * @throws java.lang.NullPointerException
     *         If task is null.
     *
     * @throws java.lang.IllegalStateException
     *         If this project does not own the provided task.
     *
     * @see #getTaskById(String)
     *
     * @see ProjectItemCacheStore#deleteThrough(Identifiable)
     */

    boolean deleteTask(@Nonnull Task task);

    /**
     * Deletes the {@link  com.easymind.api.entities.Task Task} matching the given id from this project using the current
     * {@link ProjectItemCacheStore TaskCacheStore}.
     *
     * @param id
     *        The target task id to delete from this project.
     *
     * @throws java.lang.NullPointerException
     *         If id is null.
     *
     * @throws java.lang.IllegalArgumentException
     *         If no task is matching the id.
     *
     * @see #deleteTask(Task)
     */

    boolean deleteTaskById(@Nonnull String id);

    /**
     * Gets all {@link com.easymind.api.entities.Note Note} from this {@code Project}.
     * <br>This copies the backing note cache store into a list.
     *
     * @return Immutable list of notes.
     */

    @Nonnull
    List<Note> getNotes();

    /**
     * Gets all {@link com.easymind.api.entities.Task Task} from this {@code Project}.
     * <br>This copies the backing task cache store into a list.
     *
     * @return Immutable list of tasks.
     */

    @Nonnull
    List<Task> getTasks();

    /**
     * Retrieves the {@link com.easymind.api.entities.Note Note} matching the given id using the current
     * {@link ProjectItemCacheStore NoteCacheStore}.
     *
     * @param id
     *        The target id.
     *
     * @throws java.lang.NullPointerException
     *         If id is null.
     *
     * @return The note with the given id or null.
     */

    @Nullable
    Note getNoteById(@Nonnull String id);

    /**
     * Retrieves the {@link com.easymind.api.entities.Task Task} matching the given id using the current
     * {@link ProjectItemCacheStore TaskCacheStore}.
     *
     * @param id
     *        The target task id.
     *
     * @throws java.lang.NullPointerException
     *         If id is null.
     *
     * @return The task with the given id or null.
     */

    @Nullable
    Task getTaskById(@Nonnull String id);

    /**
     * Retrieves all {@link com.easymind.api.entities.Note Note} matching the given title using the current
     * {@link ProjectItemCacheStore NoteCacheStore}.
     *
     * @param title
     *        The targets title.
     *
     * @param ignoreCase
     *        True, To ignore the title case when checking.
     *
     * @throws java.lang.NullPointerException
     *         if title is null.
     *
     * @return Possibly-empty immutable list of notes with the given title.
     */

    @Nonnull
    List<Note> getNotesByTitle(@Nonnull String title, boolean ignoreCase);

    /**
     * Retrieves all {@link com.easymind.api.entities.Note Note} matching the given title without ignoring the case
     * using the current {@link ProjectItemCacheStore NoteCacheStore}.
     *
     * @param title
     *        The targets title.
     *
     * @throws java.lang.NullPointerException
     *         If title is null.
     *
     * @see #getNotesByTitle(String, boolean)
     *
     * @return Possibly-empty immutable list of notes with the given title.
     */

    @Nonnull
    List<Note> getNotesByTitle(@Nonnull String title);

    /**
     * Retrieves all {@link com.easymind.api.entities.Task Task} matching the given title using the current
     * {@link ProjectItemCacheStore TaskCacheStore}.
     *
     * @param title
     *        The targets title.
     *
     * @param ignoreCase
     *        True, To ignore the title case when checking.
     *
     * @throws java.lang.NullPointerException
     *         If title is null.
     *
     * @return Possibly-empty immutable list of notes with the title.
     */

    @Nonnull
    List<Task> getTasksByTitle(@Nonnull String title, boolean ignoreCase);

    /**
     * Retrieves all {@link com.easymind.api.entities.Task Task} matching the given title ignoring the case
     * using the current {@link ProjectItemCacheStore TaskCacheStore}.
     *
     * @param title
     *        The targets title.
     *
     * @throws java.lang.NullPointerException
     *         If title is null.
     *
     * @return Possibly-empty immutable list of notes with the title.
     */

    @Nonnull
    List<Task> getTasksByTitle(@Nonnull String title);

    /**
     * Retrieves all {@link com.easymind.api.entities.Note Note} matching the provided tags using the current
     * {@link ProjectItemCacheStore NoteCacheStore}.
     *
     * @param tags
     *        The targets notes tags.
     *
     * @throws java.lang.NullPointerException
     *         If tags is null.
     *
     * @return Possibly-empty immutable list of notes with the tags.
     */

    @Nonnull
    List<Note> getNotesByTags(@Nonnull List<String> tags);

    /**
     * Retrieves all the {@link com.easymind.api.entities.Task Task} matching the provided tags using the current
     * {@link ProjectItemCacheStore TaskCacheStore}.
     *
     * @param tags
     *        The targets tags.
     *
     * @throws java.lang.NullPointerException
     *         If tags is null.
     *
     * @return Possibly-empty immutable list of tasks with the tags.
     */

    @Nonnull
    List<Task> getTasksByTags(@Nonnull List<String> tags);

    /**
     * Gets the moment at which the {@code Project} has been modified for the last time.
     *
     * @see #getCreationTime()
     *
     * @return Non-null last modified time.
     */

    @Nonnull
    Date getLastModifiedTime();

    /**
     * Sets the {@code Project} last modified time.
     *
     * @param lastModifiedTime
     *        The last modified time.
     *
     * @throws java.lang.NullPointerException
     *         If last modified time is null.
     *
     */

    void setLastModifiedTime(@Nonnull Date lastModifiedTime);

    /**
     * Returns the creation time for this {@code Project}.
     *
     * @return Non-null creation time.
     */

    @Nonnull
    Date getCreationTime();

    /**
     * Compares the current and the provided {@code Project} last update {@link java.util.Date Date}.
     *
     * @param o
     *        The project to be compared.
     *
     * @return 0 if the current project last update is equal to the provided project last update, a value less than 0 if this date is
     * before and greater if after.
     */

    @Override
    default int compareTo(Project o) {
        return this.getLastModifiedTime().compareTo(o.getLastModifiedTime());
    }
}
