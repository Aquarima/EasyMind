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

package com.easymind.core.entities;

import com.easymind.api.entities.*;
import com.easymind.api.utils.cache.ProjectItemCacheStore;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProjectImpl implements Project {

    private final String id;

    private String name, description;

    private final Date creationTime;

    private Date lastModifiedTime;

    private boolean isFavorite;

    private List<String> tags;

    private ProjectItemCacheStore<Note> noteCacheStore;
    private ProjectItemCacheStore<Task> taskCacheStore;

    public ProjectImpl(@Nonnull Date creationTime, @Nonnull String id, @Nonnull String name, @Nonnull String description) {
        this.creationTime = Objects.requireNonNull(creationTime);
        this.id = Objects.requireNonNull(id);
        this.tags = new ArrayList<>();
        this.setName(name);
        this.setDescription(description);
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(@Nonnull String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Nonnull
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(@Nonnull String description) {
        this.description = Objects.requireNonNull(description);
    }

    @Override
    public List<String> getTags() {
        return tags;
    }

    @Override
    public void setTags(@Nonnull List<String> tags) {
        this.tags = Objects.requireNonNull(tags);
    }

    @Override
    public boolean isFavorite() {
        return isFavorite;
    }

    @Override
    public void setFavorite(boolean favorite) {
        this.isFavorite = favorite;
    }

    @Nonnull
    @Override
    public ProjectItemCacheStore<Note> getNoteCacheStore() {
        return noteCacheStore;
    }

    @Override
    public void setNoteCacheStore(@Nonnull ProjectItemCacheStore<Note> noteCacheStore) {
        this.noteCacheStore = Objects.requireNonNull(noteCacheStore);
    }

    @Nonnull
    @Override
    public ProjectItemCacheStore<Task> getTaskCacheStore() {
        return taskCacheStore;
    }

    @Override
    public void setTaskCacheStore(@Nonnull ProjectItemCacheStore<Task> taskCacheStore) {
        this.taskCacheStore = Objects.requireNonNull(taskCacheStore);
    }

    @Nonnull
    @Override
    public Note createNote(@Nonnull String title) {

        final Note note = new NoteImpl.Builder()
                .setTitle(title)
                .setOwnerId(this.id)
                .build();

        noteCacheStore.writeThrough(note);

        return note;
    }

    @Nonnull
    @Override
    public Task createTask(@Nonnull String title, @Nonnull Priority priority, @Nullable Date start, @Nullable Date end) {

        final Task task = new TaskImpl.Builder()
                .setTitle(title)
                .setOwnerId(this.id)
                .setPriority(priority)
                .setStart(start)
                .setEnd(end)
                .build();

        taskCacheStore.writeThrough(task);

        return task;
    }

    @Override
    public boolean saveNote(@Nonnull Note note) {

        Objects.requireNonNull(note);

        if (!note.getOwnerId().equals(id)) {
            throw new IllegalStateException("Invalid note");
        }

        return noteCacheStore.writeThrough(note);
    }

    @Override
    public boolean saveNoteById(@Nonnull String id) {

        final Note note = getNoteById(id);

        if (note == null) {
            throw new IllegalArgumentException("Could not find note for id: " + id);
        }

        return saveNote(note);
    }

    @Override
    public boolean saveTask(@Nonnull Task task) {

        Objects.requireNonNull(task);

        if (!task.getOwnerId().equals(id)) {
            throw new IllegalStateException("Invalid task");
        }

        return taskCacheStore.writeThrough(task);
    }

    @Override
    public boolean saveTaskById(@Nonnull String id) {

        final Task task = getTaskById(id);

        if (task == null) {
            throw new IllegalArgumentException("Could not find task for id: " + id);
        }

        return saveTask(task);
    }

    @Override
    public boolean deleteNote(@Nonnull Note note) {

        Objects.requireNonNull(note);

        if (!note.getOwnerId().equals(id)) {
            throw new IllegalStateException("Invalid note");
        }

        return noteCacheStore.deleteThrough(note);
    }

    @Override
    public boolean deleteNoteById(@Nonnull String id) {

        final Note note = getNoteById(id);

        if (note == null) {
            throw new IllegalArgumentException("Could not find note for id: " + id);
        }

        return deleteNote(note);
    }

    @Override
    public boolean deleteTask(@Nonnull Task task) {

        Objects.requireNonNull(task);

        if (!task.getOwnerId().equals(id)) {
            throw new IllegalStateException("Invalid task");
        }

        return taskCacheStore.deleteThrough(task);
    }

    @Override
    public boolean deleteTaskById(@Nonnull String id) {

        final Task task = getTaskById(id);

        if (task == null) {
            throw new IllegalArgumentException("Could not find task for id: " + id);
        }

        return deleteTask(task);
    }

    @Nonnull
    @Override
    public List<Note> getNotes() {
        return noteCacheStore.getElementsByOwnerId(this.id);
    }

    @Nonnull
    @Override
    public List<Task> getTasks() {
        return taskCacheStore.getElementsByOwnerId(id);
    }

    @Nullable
    @Override
    public Note getNoteById(@Nonnull String id) {

        Note note = noteCacheStore.getElementById(id);

        if (note != null) {

            if (note.getOwnerId().equals(id)) {
                return note;
            }

        }

        return null;
    }

    @Nullable
    @Override
    public Task getTaskById(@Nonnull String id) {

        Task task = taskCacheStore.getElementById(id);

        if (task != null) {

            if (task.getOwnerId().equals(id)) {
                return task;
            }

        }

        return null;
    }

    @Nonnull
    @Override
    public List<Note> getNotesByTitle(@Nonnull String title, boolean ignoreCase) {
        return noteCacheStore.getElementsByTitle(title, ignoreCase).stream()
                .filter(note -> note.getOwnerId().equals(id))
                .collect(Collectors.toUnmodifiableList());
    }

    @Nonnull
    @Override
    public List<Note> getNotesByTitle(@Nonnull String title) {
        return getNotesByTitle(title, false);
    }

    @Nonnull
    @Override
    public List<Task> getTasksByTitle(@Nonnull String title, boolean ignoreCase) {
        return taskCacheStore.getElementsByTitle(title, ignoreCase).stream()
                .filter(task -> task.getOwnerId().equals(id))
                .collect(Collectors.toUnmodifiableList());
    }

    @Nonnull
    @Override
    public List<Task> getTasksByTitle(@Nonnull String title) {
        return getTasksByTitle(title, false);
    }

    @Nonnull
    @Override
    public List<Note> getNotesByTags(@Nonnull List<String> tags) {
        return noteCacheStore.getElementsByTags(tags).stream()
                .filter(note -> note.getOwnerId().equals(id))
                .collect(Collectors.toUnmodifiableList());
    }

    @Nonnull
    @Override
    public List<Task> getTasksByTags(@Nonnull List<String> tags) {
        return taskCacheStore.getElementsByTags(tags).stream()
                .filter(task -> task.getOwnerId().equals(id))
                .collect(Collectors.toUnmodifiableList());
    }

    @Nonnull
    @Override
    public Date getLastModifiedTime() {
        return Objects.requireNonNullElse(lastModifiedTime, creationTime);
    }

    @Override
    public void setLastModifiedTime(@Nonnull Date lastModifiedTime) {
        this.lastModifiedTime = Objects.requireNonNull(lastModifiedTime);
    }

    @Nonnull
    @Override
    public Date getCreationTime() {
        return creationTime;
    }

    @Nonnull
    @Override
    public String getId() {
        return id;
    }
}
