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

package com.easymind.core.data;

import com.easymind.api.entities.Priority;
import com.easymind.api.entities.Task;
import com.easymind.api.utils.TimeUtils;
import com.easymind.api.data.DataAccessObject;
import com.easymind.core.entities.TaskImpl;
import com.easymind.core.utils.AppUtils;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TaskAccessObject implements DataAccessObject<Task> {

    private final Logger logger = AppUtils.getLogger();

    private final Connection connection;

    public TaskAccessObject(Connection connection) {
        this.connection = Objects.requireNonNull(connection);
        this.init();
    }

    @Override
    public void init() {

        String sql = "CREATE TABLE IF NOT EXISTS TASKS " +
                "(ID TEXT PRIMARY KEY, " +
                "OWNER_ID TEXT, " +
                "TITLE TEXT, " +
                "TAGS TEXT, " +
                "FAVORITE INTEGER, " +
                "PRIORITY TEXT, " +
                "DONE INTEGER, " +
                "START TEXT, " +
                "END TEXT, " +
                "CONTENT TEXT, " +
                "LAST_MODIFIED_TIME TEXT, " +
                "CREATION_TIME TEXT)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Failed to create table='TASKS' to database. \nReason: " + ex.getMessage());
        }
    }

    @Override
    public boolean insert(Task e) {

        String sql = "INSERT INTO TASKS " +
                "(ID, " +
                "OWNER_ID, " +
                "TITLE, " +
                "TAGS, " +
                "FAVORITE, " +
                "PRIORITY, " +
                "DONE, " +
                "START, " +
                "END, " +
                "CONTENT, " +
                "LAST_MODIFIED_TIME, " +
                "CREATION_TIME) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, e.getId());
            statement.setString(2, e.getOwnerId() );
            statement.setString(3, e.getTitle());
            statement.setString(4, String.join(", ", e.getTags()));
            statement.setBoolean(5, e.isFavorite());
            statement.setBoolean(6, e.isDone());
            statement.setString(7, e.getPriority().toString());
            statement.setString(8, TimeUtils.format(e.getStart().orElse(null), "dd/MM/yyyy HH:mm"));
            statement.setString(9, TimeUtils.format(e.getDeadline().orElse(null), "dd/MM/yyyy HH:mm"));
            statement.setString(10, e.getContent());
            statement.setString(11, TimeUtils.format(e.getLastModifiedTime(), "dd/MM/yyyy HH:mm"));
            statement.setString(12, TimeUtils.format(e.getCreationTime(), "dd/MM/yyyy HH:mm"));

            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Failed to insert element for ID='" + e.getId() + "' to database table='TASKS'. \nReason: " + ex.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean update(Task e) {

        String sql = "UPDATE TASKS SET " +
                "OWNER_ID = ?, " +
                "TITLE = ?, " +
                "TAGS = ?, " +
                "FAVORITE = ?, " +
                "PRIORITY = ?, " +
                "DONE = ?, " +
                "START = ?, " +
                "END = ?, " +
                "TEXT = ?, " +
                "LAST_MODIFIED_TIME = ?, " +
                "WHERE ID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, e.getOwnerId());
            statement.setString(2, e.getTitle());
            statement.setString(3, String.join(", ", e.getTags()));
            statement.setBoolean(4, e.isFavorite());
            statement.setBoolean(5, e.isDone());
            statement.setString(6, e.getPriority().toString());
            statement.setString(7, TimeUtils.format(e.getStart().orElse(null), "dd/MM/yyyy HH:mm"));
            statement.setString(8, TimeUtils.format(e.getDeadline().orElse(null),"dd/MM/yyyy HH:mm"));
            statement.setString(9, e.getContent());
            statement.setString(10, TimeUtils.format(e.getLastModifiedTime(), "dd/MM/yyyy HH:mm"));
            statement.setString(11, e.getId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Failed to update element for ID='" + e.getId() + "' to database table='TASKS'. \nReason: " + ex.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(Task e) {

        String sql = "DELETE FROM TASKS WHERE ID = '" + e.getId() + "'";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Failed to delete element for ID='" + e.getId() + "' from database table='TASKS'. \nReason: " + ex.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public Task findById(String id) {

        String sql = "SELECT * FROM TASKS WHERE ID = '" + id + "'";

        Task task = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                task = new TaskImpl.Builder(resultSet.getString("TITLE"))
                        .setPriority(Priority.valueOf(resultSet.getString("PRIORITY")))
                        .setStart(TimeUtils.parse(resultSet.getString("START"), "dd/MM/yyyy HH:mm"))
                        .setEnd(TimeUtils.parse(resultSet.getString("END"), "dd/MM/yyyy HH:mm"))
                        .setContent(resultSet.getString("CONTENT"))
                        .setFavorite(resultSet.getBoolean("FAVORITE"))
                        .setDone(resultSet.getBoolean("DONE"))
                        .setTags(new ArrayList<>(Arrays.asList(resultSet.getString("TAGS").split(", "))))
                        .setLastModifiedTime(TimeUtils.parse(resultSet.getString("LAST_MODIFIED_TIME"), "dd/MM/yyyy HH:mm"))
                        .setCreationTime(TimeUtils.parse(resultSet.getString("CREATION_TIME"), "dd/MM/yyyy HH:mm"))
                        .setOwnerId(resultSet.getString("OWNER_ID"))
                        .setId(id)
                        .build();
            }

            resultSet.close();
        } catch (SQLException ex) {
            logger.error("Failed to find element for ID='" + id + "' from database table='TASKS'. \nReason: " + ex.getMessage());
        }

        return task;
    }

    @Override
    public List<Task> findAll() {

        String sql = "SELECT ID FROM TASKS";

        final List<Task> tasks = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String id = resultSet.getString("ID");

                tasks.add(findById(id));
            }

            resultSet.close();
        } catch (SQLException ex) {
            logger.error("Failed to find elements from database table='TASKS'. \nReason: " + ex.getMessage());
        }

        return tasks;
    }

    @Override
    public List<String> getValues(String column) {

        String sql = "SELECT " + column + " FROM TASKS";

        final List<String> values = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                values.add(resultSet.getString(column));
            }

        } catch (SQLException ex) {
            logger.error("Failed to retrieve values from database table='TASKS(" + column + ")'. \nReason: " + ex.getMessage());
        }

        return values;
    }

    @Override
    public int getCount() {

        String sql = "SELECT COUNT(*) FROM TASKS";

        int count = 0;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            count = resultSet.getInt(1);
            resultSet.close();
        } catch (SQLException ex) {
            logger.error("Failed to count elements from database table='TASKS'. \nReason: " + ex.getMessage());
        }

        return count;
    }

    @Nonnull
    @Override
    public Connection getConnection() {
        return connection;
    }
}
