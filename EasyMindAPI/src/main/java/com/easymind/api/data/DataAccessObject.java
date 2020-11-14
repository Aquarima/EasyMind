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

package com.easymind.api.data;

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.util.List;

/**
 * The layer that interacts directly with the data source.
 * <br>Uses the Data Access Object <i>DAO</i> design pattern.
 *
 * @param <T>
 *        The data type.
 */

public interface DataAccessObject<T> {

    /**
     * Permits to initialize the data source.
     */

    void init();

    /**
     * Inserts if not already exist the provided e to the data source.
     *
     * @param e
     *        The element to insert.
     */

    boolean insert(T e);

    /**
     * Updates if exist the provided element to the data source.
     *
     * @param e
     *        The element to update.
     */

    boolean update( T e);

    /**
     * Deletes if exists the provided element from the data source.
     *
     * @param e
     *        The element to delete.
     */

    boolean delete(T e);

    /**
     * Looks for the element that matching to the provided id.
     *
     * @param id
     *        The element id.
     *
     * @return The element for the id or null.
     */

    T findById(String id);

    /**
     * Looks for the inserted elements into the data source.
     *
     * @return Possibly-empty list of all inserted elements.
     */

    List<T> findAll();

    /**
     * Looks into the data source for all column values from the given column name.
     *
     * @param column
     *        The column to get values.
     *
     * @return List of column values.
     */

    List<String> getValues(String column);

    /**
     * Looks for the amounts of elements into the data source.
     *
     * @return The amount of elements.
     */

    int getCount();

    /**
     * Returns the currently used {@link java.sql.Connection Connection} for this DataAccessObject instance.
     *
     * @return The current data source connection for this instance.
     */

    @Nonnull
    Connection getConnection();
}
