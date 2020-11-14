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

import com.easymind.api.entities.Note;

import javax.annotation.Nonnull;
import java.util.*;

public class NoteImpl implements Note {

    private final String id;

    private final Date creationTime;

    private Date lastModifiedTime;

    private String ownerId, title, content;

    private boolean isFavorite;

    private List<String> tags;

    public NoteImpl(@Nonnull Builder builder) {
        this.creationTime = Objects.requireNonNull(builder.creationTime);
        this.id = Objects.requireNonNull(builder.id);
        this.setOwnerId(builder.ownerId);
        this.setTitle(builder.title);
        this.setTags(builder.tags);
        this.setFavorite(builder.isFavorite);
        this.setContent(builder.content);
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
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(@Nonnull String content) {
        this.content = Objects.requireNonNull(content);
    }

    @Nonnull
    @Override
    public String getId() {
        return id;
    }

    public static class Builder extends ItemBuilder<Note> {

        private String content;

        public Builder(@Nonnull String title) {
            super(title);
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
            super.setOwnerId(ownerId);
            return this;
        }

        @Override
        public Builder setId(@Nonnull String id) {
            super.setId(id);
            return this;
        }

        public Builder setContent(@Nonnull String content) {
            this.content = content;
            return this;
        }

        @Override
        public Note build() {

            if (tags == null) {
                this.tags = new ArrayList<>();
            }

            if (id == null) {
                this.id = UUID.randomUUID().toString();
            }

            if (creationTime == null) {
                this.creationTime = new Date();
            }

            if (content == null) {
                this.content = "";
            }

            return new NoteImpl(this);
        }
    }
}
