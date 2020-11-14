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

import com.easymind.api.entities.Project;
import com.easymind.api.utils.TimeUtils;
import com.easymind.api.data.DataAccessObject;
import com.easymind.core.entities.ProjectBuilder;
import com.easymind.core.utils.AppUtils;
import com.easymind.core.utils.cache.ProjectItemCacheStoreImpl;
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

public class ProjectAccessObject implements DataAccessObject<Project> {

    private final Logger logger = AppUtils.getLogger();

    private final Connection connection;

    public ProjectAccessObject(Connection connection) {
        this.connection = Objects.requireNonNull(connection);
        this.init();
    }

    @Override
    public void init() {

        String sql = "CREATE TABLE IF NOT EXISTS PROJECTS " +
                "(ID TEXT PRIMARY KEY, " +
                "NAME TEXT, " +
                "DESCRIPTION TEXT, " +
                "TAGS TEXT, " +
                "FAVORITE INTEGER, " +
                "LAST_MODIFIED_TIME TEXT, " +
                "CREATION_TIME TEXT)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Failed to create table='PROJECTS' to database. \nReason: " + ex.getMessage());
        }
    }

    @Override
    public boolean insert(Project e) {

        String sql = "INSERT INTO PROJECTS " +
                "(ID, " +
                "NAME, " +
                "DESCRIPTION, " +
                "TAGS, " +
                "FAVORITE, " +
                "LAST_MODIFIED_TIME, " +
                "CREATION_TIME) " +
                "VALUES(?,?,?,?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, e.getId());
            statement.setString(2, e.getName());
            statement.setString(3, e.getDescription());
            statement.setString(4, String.join(",", e.getTags()));
            statement.setBoolean(5, e.isFavorite());
            statement.setString(6, TimeUtils.format(e.getLastModifiedTime(), "dd/MM/yyyy HH:mm"));
            statement.setString(7, TimeUtils.format(e.getCreationTime(), "dd/MM/yyyy HH:mm"));

            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Failed to insert element for ID='" + e.getId() + "' to database table='PROJECTS'. \nReason: " + ex.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean update(Project e) {

        String sql = "UPDATE PROJECTS SET " +
                "NAME = ?, " +
                "DESCRIPTION = ?, " +
                "TAGS = ?, " +
                "FAVORITE = ?, " +
                "LAST_MODIFIED_TIME = ? " +
                "WHERE ID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, e.getName());
            statement.setString(2, e.getDescription());
            statement.setString(3, String.join(",", e.getTags()));
            statement.setBoolean(4, e.isFavorite());
            statement.setString(5, TimeUtils.format(e.getLastModifiedTime(), "dd/MM/yyyy HH:mm"));
            statement.setString(6, e.getId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Failed to update element for ID='" + e.getId() + "' to database table='PROJECTS'. \nReason: " + ex.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(Project e) {

        String sql = "DELETE FROM PROJECTS WHERE ID = '" + e.getId() + "'";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Failed to delete element for ID='" + e.getId() + "' from database table='PROJECTS'. \nReason: " + ex.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public Project findById(String id) {

        String sql = "SELECT * FROM PROJECTS WHERE ID = '" + id + "'";

        Project project = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                project = new ProjectBuilder()
                        .setId(id)
                        .setName(resultSet.getString("NAME"))
                        .setDescription(resultSet.getString("DESCRIPTION"))
                        .setTags(new ArrayList<>(Arrays.asList(resultSet.getString("TAGS").split(","))))
                        .setFavorite(resultSet.getBoolean("FAVORITE"))
                        .setNoteCacheStore(new ProjectItemCacheStoreImpl<>(new NoteAccessObject(connection)))
                        .setTaskCacheStore(new ProjectItemCacheStoreImpl<>(new TaskAccessObject(connection)))
                        .setLastModifiedTime(TimeUtils.parse(resultSet.getString("LAST_MODIFIED_TIME"),"dd/MM/yyyy HH:mm"))
                        .setCreationTime(TimeUtils.parse(resultSet.getString("CREATION_TIME"), "dd/MM/yyyy HH:mm"))
                        .build();
            }

            resultSet.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("Failed to find element for ID='" + id + "' from database table='PROJECTS'. \nReason: " + ex.getMessage());
        }

        return project;
    }

    @Override
    public List<Project> findAll() {

        String sql = "SELECT ID FROM PROJECTS";

        final List<Project> projects = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String id = resultSet.getString("ID");

                projects.add(findById(id));
            }

            resultSet.close();
        } catch (SQLException ex) {
            logger.error("Failed to find elements from database table='PROJECTS'. \nReason: " + ex.getMessage());
        }

        return projects;
    }

    @Override
    public List<String> getValues(String column) {

        String sql = "SELECT " + column + " FROM PROJECTS";

        final List<String> values = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                values.add(resultSet.getString(column));
            }
        } catch (SQLException ex) {
            logger.error("Failed to retrieve values from database table='PROJECTS(" + column + ")'. \nReason: " + ex.getMessage());
        }

        return values;
    }

    @Override
    public int getCount() {

        String sql = "SELECT COUNT(ID) FROM PROJECTS";

        int count = 0;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            count = resultSet.getInt(1);
            resultSet.close();
        } catch (SQLException ex) {
            logger.error("Failed to count elements from database table='PROJECTS'. \nReason: " + ex.getMessage());
        }

        return count;
    }

    @Nonnull
    @Override
    public Connection getConnection() {
        return connection;
    }
}
