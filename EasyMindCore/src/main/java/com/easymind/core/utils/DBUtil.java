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

package com.easymind.core.utils;

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    /**
     * Attempts to establish a connection to the given database URL.
     *
     * @see DriverManager#getConnection(String)
     *
     * @param database
     *        The location of the database (file.db) to use.
     *
     * @return The {@link Connection Connection} to the database.
     */

    public static Connection connectTo(@Nonnull String database) {

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(database);

        } catch (SQLException e) {
            AppUtils.getLogger()
                    .error("Failed to connect to database. \n Reason: " + e.getMessage());
        }

        return connection;
    }
}
