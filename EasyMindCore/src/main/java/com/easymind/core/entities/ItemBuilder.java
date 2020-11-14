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

import com.easymind.api.entities.AbstractBuilder;
import com.easymind.api.entities.ProjectItem;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.List;

public abstract class ItemBuilder<T extends ProjectItem> extends AbstractBuilder<T> {

    protected Date lastModifiedTime, creationTime;

    protected String id, ownerId, title;

    protected boolean isFavorite;

    protected List<String> tags;

    public ItemBuilder(@Nonnull String title) {
        this.title = title;
    }

    public ItemBuilder() {}

    public ItemBuilder<T> setId(@Nonnull String id) {
        this.id = id;
        return this;
    }

    public ItemBuilder<T> setOwnerId(@Nonnull String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public ItemBuilder<T> setTitle(@Nonnull String title) {
        this.title = title;
        return this;
    }

    public ItemBuilder<T> setFavorite(boolean favorite) {
        this.isFavorite = favorite;
        return this;
    }

    public ItemBuilder<T> setTags(@Nonnull List<String> tags) {
        this.tags = tags;
        return this;
    }

    public ItemBuilder<T> setLastModifiedTime(@Nonnull Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
        return this;
    }

    public ItemBuilder<T> setCreationTime(@Nonnull Date creationTime) {
        this.creationTime = creationTime;
        return this;
    }
}
