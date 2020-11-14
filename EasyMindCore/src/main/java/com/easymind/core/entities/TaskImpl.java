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

import com.easymind.api.entities.Priority;
import com.easymind.api.entities.Status;
import com.easymind.api.entities.Task;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class TaskImpl implements Task {

    private final String id;

    private final Date creationTime;

    private String ownerId, title, content;

    private Date start, deadline, lastModifiedTime;

    private boolean isFavorite, isDone;

    private Priority priority;

    private List<String> tags;

    public TaskImpl(@Nonnull Builder builder) {
        this.creationTime = Objects.requireNonNull(builder.creationTime);
        this.id = Objects.requireNonNull(builder.id);
        this.setOwnerId(builder.ownerId);
        this.setTitle(builder.title);
        this.setTags(builder.tags);
        this.setFavorite(builder.isFavorite);
        this.setDone(builder.isDone);
        this.setPriority(builder.priority);
        this.setStart(builder.start);
        this.setDeadline(builder.deadline);
        this.setContent(builder.content);
    }

    @Nonnull
    @Override
    public Priority getPriority() {

        if (priority == null) {
            return Priority.UNKNOWN;
        }

        return priority;
    }

    @Override
    public void setPriority(@Nonnull Priority priority) {
        this.priority = Objects.requireNonNull(priority);
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public void setDone(boolean done) {
        this.isDone = done;
    }

    @Nonnull
    @Override
    public Optional<Date> getStart() {
        return Optional.ofNullable(start);
    }

    @Override
    public void setStart(@Nullable Date start) {
        this.start = start;
    }

    @Nonnull
    @Override
    public Optional<Date> getDeadline() {
        return Optional.ofNullable(deadline);
    }

    @Override
    public void setDeadline(@Nullable Date deadline) {
        this.deadline = deadline;
    }

    @Nonnull
    @Override
    public Status getStatus() {

        if (isDone) {
            return Status.DONE;
        }

        return Status.of(start, deadline);
    }

    @Nonnull
    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(@Nonnull String content) {
        this.content = Objects.requireNonNull(content);
    }

    @Nonnull
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(@Nonnull String title) {
        this.title = Objects.requireNonNull(title);
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
    public List<String> getTags() {
        return tags;
    }

    @Override
    public void setTags(@Nonnull List<String> tags) {
        this.tags = Objects.requireNonNull(tags);
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
    public String getOwnerId() {
        return ownerId;
    }

    @Override
    public void setOwnerId(@Nonnull String ownerId) {
        this.ownerId = Objects.requireNonNull(ownerId);
    }

    @Nonnull
    @Override
    public String getId() {
        return id;
    }

    public static class Builder extends ItemBuilder<Task> {

        private Date start, deadline;

        private String content;

        private boolean isDone;

        private Priority priority;

        public Builder(@Nonnull String title) {
            this.title = title;
        }

        public Builder() {}

        @Override
        public Builder setTitle(@Nonnull String title) {
            super.setTitle(title);
            return this;
        }

        @Override
        public Builder setFavorite(boolean favorite) {
            super.setFavorite(favorite);
            return this;
        }

        @Override
        public Builder setTags(@Nonnull List<String> tags) {
            super.setTags(tags);
            return this;
        }

        @Override
        public Builder setLastModifiedTime(@Nonnull Date lastModifiedTime) {
            super.setLastModifiedTime(lastModifiedTime);
            return this;
        }

        @Override
        public Builder setCreationTime(@Nonnull Date creationTime) {
            super.setCreationTime(creationTime);
            return this;
        }

        @Override
        public Builder setOwnerId(@Nonnull String ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        @Override
        public Builder setId(@Nonnull String id) {
            super.setId(id);
            return this;
        }

        public Builder setDone(boolean done) {
            this.isDone = done;
            return this;
        }

        public Builder setPriority(@Nonnull Priority priority) {
            this.priority = priority;
            return this;
        }

        public Builder setStart(@Nullable Date start) {
            this.start = start;
            return this;
        }

        public Builder setEnd(@Nullable Date deadline) {
            this.deadline = deadline;
            return this;
        }

        public Builder setContent(@Nonnull String content) {
            this.content = content;
            return this;
        }

        @Override
        public Task build() {

            if (tags == null) {
                this.tags = new ArrayList<>();
            }

            if (id == null) {
                this.id = UUID.randomUUID().toString();
            }

            if (creationTime == null) {
                this.creationTime = new Date();
            }

            return new TaskImpl(this);
        }
    }
}
