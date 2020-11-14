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

import com.easymind.api.entities.Project;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * A {@link com.easymind.api.utils.cache.CacheStore CacheStore} extended interface for {@link com.easymind.api.entities.Project Project}
 * witch provides specifics filtering functions.
 */

public interface ProjectCacheStore extends CacheStore<Project> {

    /**
     * Creates an immutable list of all {@link com.easymind.api.entities.Project Project} matching the given name.
     *
     * @throws java.lang.NullPointerException
     *         If name is null.
     *
     * @param name
     *        The targets name.
     *
     * @param ignoreCase
     *        True, To ignore the name case.
     *
     * @return Immutable list of projects with the given name.
     */

    @Nonnull
    List<Project> getElementsByName(@Nonnull String name, boolean ignoreCase);

    /**
     * Creates an immutable list of all {@link com.easymind.api.entities.Project Project} matching the given tags.
     *
     * @throws java.lang.NullPointerException
     *         If tags is null.
     *
     * @param tags
     *        The targets tags.
     *
     * @return Immutable list of projects with the given tags.
     */

    @Nonnull
    List<Project> getElementsByTags(@Nonnull List<String> tags);
}
