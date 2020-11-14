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

package com.easymind.api.utils.cache;

import com.easymind.api.entities.ProjectItem;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * A {@link com.easymind.api.utils.cache.CacheStore CacheStore} extended interface for {@link ProjectItem Item}
 * witch provides specifics filtering functions.
 *
 * @param <T>
 *         The item type.
 */

public interface ProjectItemCacheStore<T extends ProjectItem> extends CacheStore<T> {

    /**
     * Creates an immutable list of all {@link ProjectItem Item} matching the given title.
     *
     * @throws java.lang.NullPointerException
     *         If title is null.
     *
     * @param title
     *        The targets title.
     *
     * @param ignoreCase
     *        True, To ignore the title case.
     *
     * @return Immutable list of items with the given title.
     */

    @Nonnull
    List<T> getElementsByTitle(@Nonnull String title, boolean ignoreCase);

    /**
     * Creates an immutable list of all {@link ProjectItem Item} matching the given tags.
     *
     * @throws java.lang.NullPointerException
     *         If tags is null.
     *
     * @param tags
     *        The targets tags.
     *
     * @return Immutable list of items with the given tags.
     */

    @Nonnull
    List<T> getElementsByTags(@Nonnull List<String> tags);

    /**
     * Creates an immutable list of all {@link ProjectItem Item} matching the owner id.
     *
     * @throws java.lang.NullPointerException
     *         If owner id is null.
     *
     * @param ownerId
     *        The targets owner id.
     *
     * @return Immutable list of items with the given owner id.
     */

    @Nonnull
    List<T> getElementsByOwnerId(@Nonnull String ownerId);
}
