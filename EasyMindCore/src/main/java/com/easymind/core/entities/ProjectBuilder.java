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

import com.easymind.api.EasyMind;
import com.easymind.api.entities.AbstractBuilder;
import com.easymind.api.entities.Note;
import com.easymind.api.entities.Project;
import com.easymind.api.entities.Task;
import com.easymind.api.utils.cache.ProjectItemCacheStore;
import com.easymind.core.utils.cache.ProjectItemCacheStoreImpl;
import com.easymind.core.data.NoteAccessObject;
import com.easymind.core.data.TaskAccessObject;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ProjectBuilder extends AbstractBuilder<Project> {

    private ProjectItemCacheStore<Note> noteCacheStore;
    private ProjectItemCacheStore<Task> taskCacheStore;

    private Date lastModifiedTime, creationTime;

    private EasyMind easyMind;

    private String id, name, description;

    private boolean isFavorite;

    private List<String> tags;

    public ProjectBuilder(EasyMind easyMind) {
        this.easyMind = easyMind;
    }

    public ProjectBuilder() {}

    public ProjectBuilder setName(@Nonnull String name) {
        this.name = name;
        return this;
    }

    public ProjectBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProjectBuilder setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public ProjectBuilder setFavorite(boolean favorite) {
        this.isFavorite = favorite;
        return this;
    }

    public ProjectBuilder setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
        return this;
    }

    public ProjectBuilder setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public ProjectBuilder setNoteCacheStore(@Nonnull ProjectItemCacheStore<Note> noteCacheStore) {
        this.noteCacheStore = noteCacheStore;
        return this;
    }

    public ProjectBuilder setTaskCacheStore(@Nonnull ProjectItemCacheStore<Task> taskCacheStore) {
        this.taskCacheStore = taskCacheStore;
        return this;
    }

    public ProjectBuilder setId(@Nonnull String id) {
        this.id = id;
        return this;
    }

    @Override
    public Project build() {

        if (creationTime == null) {
            this.creationTime = new Date();
        }

        if (id == null) {
            this.id = UUID.randomUUID().toString();
        }

        final Project project = new ProjectImpl(creationTime, id, name, description);

        if (easyMind != null) {

            if (noteCacheStore == null) {

                final NoteAccessObject nao = new NoteAccessObject(easyMind.getConnection());

                this.noteCacheStore = new ProjectItemCacheStoreImpl<>(nao);
            }

            if (taskCacheStore == null) {

                final TaskAccessObject tao = new TaskAccessObject(easyMind.getConnection());

                this.taskCacheStore = new ProjectItemCacheStoreImpl<>(tao);
            }

        }

        if (tags != null) {
            project.setTags(tags);
        }

        if (lastModifiedTime != null) {
            project.setLastModifiedTime(lastModifiedTime);
        }

        project.setFavorite(isFavorite);
        project.setNoteCacheStore(noteCacheStore);
        project.setTaskCacheStore(taskCacheStore);

        return project;
    }
}
