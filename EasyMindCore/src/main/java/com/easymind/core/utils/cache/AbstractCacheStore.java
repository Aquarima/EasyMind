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

package com.easymind.core.utils.cache;

import com.easymind.api.behaviors.Identifiable;
import com.easymind.api.utils.cache.CacheStore;
import com.easymind.api.data.DataAccessObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Stream;

public abstract class AbstractCacheStore<T extends Identifiable> implements CacheStore<T> {

    protected final DataAccessObject<T> dataSource;

    protected List<T> cachedElements;

    protected long lastUpdateTime;

    public AbstractCacheStore(@Nonnull final DataAccessObject<T> dataSource) {
        this.dataSource = Objects.requireNonNull(dataSource);
        this.cachedElements = new LinkedList<>(dataSource.findAll());
    }

    @Nonnull
    @Override
    public List<T> asList() {
        updateCache();
        return Collections.unmodifiableList(cachedElements);
    }

    @Nonnull
    @Override
    public Set<T> asSet() {
        updateCache();
        return new LinkedHashSet<>(cachedElements);
    }

    @Override
    public void clear() {
        cachedElements.clear();
    }

    @Override
    public int size() {
        return cachedElements.size();
    }

    @Override
    public boolean isEmpty() {
        return cachedElements.isEmpty();
    }

    @Nonnull
    @Override
    public Stream<T> stream() {
        return cachedElements.stream();
    }

    @Nonnull
    @Override
    public Stream<T> parallelStream() {
        return cachedElements.parallelStream();
    }

    @Nonnull
    @Override
    public Iterator<T> iterator() {
        return cachedElements.iterator();
    }

    @Override
    public boolean writeThrough(@Nonnull T element) {

        Objects.requireNonNull(element);

        updateCache();

        if (getElementById(element.getId()) == null) {
            return dataSource.insert(element);
        }

        if (!cachedElements.contains(element)) {
            cachedElements.add(element);
        }

        return dataSource.update(element);
    }

    @Override
    public boolean deleteThrough(@Nonnull T element) {

        Objects.requireNonNull(element);

        updateCache();

        if (getElementById(element.getId()) != null) {
            dataSource.delete(element);
        }

        return cachedElements.remove(element);
    }

    @Override
    public int updateCache() {

        int count = dataSource.getCount();

        if (count > size()) {

            dataSource.getValues("ID")
                    .stream()
                    .filter(id -> getElementById(id) == null)
                    .map(dataSource::findById)
                    .forEach(cachedElements::add);

            this.lastUpdateTime = System.currentTimeMillis();
        }

        return count - size();
    }

    @Nullable
    @Override
    public T getElementById(@Nonnull String id) {

        Objects.requireNonNull(id);

        return stream()
                .filter(element -> element.getId().equals(id))
                .findAny()
                .orElse(dataSource.findById(id));
    }

    @Override
    public long getLastUpdateTime() {
        return (System.currentTimeMillis() - lastUpdateTime) / 1000;
    }
}
