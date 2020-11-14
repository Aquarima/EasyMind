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

import com.easymind.api.behaviors.Identifiable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Memory Efficient Usage.
 * <br>The application caching interface that allows a decrease of queries to the data source.
 *
 * @param <T>
 *         The cache store type.
 */

public interface CacheStore<T extends Identifiable> extends Iterable<T> {

    /**
     * Creates an immutable snapshot of all cached elements of this cache.
     *
     * @return Immutable list of all cached elements.
     */

    @Nonnull
    List<T> asList();

    /**
     * Creates an immutable snapshot of all cached elements of this cache.
     *
     * @return Immutable set of all cached elements.
     */

    @Nonnull
    Set<T> asSet();

    /**
     * Clears the cache.
     */

    void clear();

    /**
     * The current size of the cache.
     * This is more efficient than creating a cache snapshot to check its size.
     *
     * @return The current cache size.
     */

    int size();

    /**
     * Checks if the cache is empty.
     * This is more efficient than creating a cache snapshot to check if its empty.
     *
     * @return True, If this cache is empty.
     */

    boolean isEmpty();

    /**
     * Creates a {@link java.util.stream.Stream Stream} of all cached elements.
     *
     * @return Stream of elements.
     */

    @Nonnull
    Stream<T> stream();

    /**
     * Creates a parallel {@link java.util.stream.Stream Stream} of all cached elements.
     *
     * @return Parallel Stream of elements.
     */

    @Nonnull
    Stream<T> parallelStream();

    /**
     * Inserts or updates depending if the provided element is already stored into the data source.
     *
     * @throws java.lang.NullPointerException
     *         If element is null.
     *
     * @param element
     * *      The element to write to the data source.
     *
     * @return True, If the element has been written to the data source.
     */

    boolean writeThrough(@Nonnull T element);

    /**
     * Deletes if exists the provided element from this cache and the data source.
     *
     * @throws java.lang.NullPointerException
     *         If element is null.
     *
     * @param element
     *        The element to delete from the data source.
     *
     * @return True, If the element has been deleted from the data source.
     */

    boolean deleteThrough(@Nonnull T element);

    /**
     * Updates if necessary the cache store by comparing the current cache elements number and the data source
     * elements number. If the current cache number of elements is lower than the data source, The missing elements are
     * added from the data source.
     *
     * @return The amount of elements added to the cache store.
     */

    int updateCache();

    /**
     * Creates an immutable list of all elements matching the given id.
     *
     * @throws java.lang.NullPointerException
     *         If id is null.
     *
     * @param id
     *        The targets id.
     *
     * @return Immutable list of elements with the given id.
     */

    @Nullable
    T getElementById(String id);

    /**
     * Returns the {@code long} time in seconds since the cache has been updated for the last time.
     *
     * @return The time since the cache last update.
     */

    long getLastUpdateTime();
}
