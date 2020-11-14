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

import com.easymind.api.entities.Note;
import com.easymind.api.utils.TimeUtils;
import com.easymind.api.data.DataAccessObject;
import com.easymind.core.entities.NoteImpl;
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

public class NoteAccessObject implements DataAccessObject<Note> {

    private final Logger logger = AppUtils.getLogger();

    private final Connection connection;

    public NoteAccessObject(Connection connection) {
        this.connection = Objects.requireNonNull(connection);
        this.init();
    }

    @Override
    public void init() {

        String sql = "CREATE TABLE IF NOT EXISTS NOTES " +
                "(ID TEXT PRIMARY KEY, " +
                "OWNER_ID TEXT, " +
                "TITLE TEXT, " +
                "TAGS TEXT, " +
                "FAVORITE INTEGER, " +
                "CONTENT TEXT, " +
                "LAST_MODIFIED_TIME TEXT, " +
                "CREATION_TIME TEXT)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Failed to create table='NOTES' to the database. \nReason: " + ex.getMessage());
        }
    }

    @Override
    public boolean insert(Note e) {

        String sql = "INSERT INTO NOTES " +
                "(ID, " +
                "OWNER_ID, " +
                "TITLE, " +
                "TAGS, " +
                "FAVORITE, " +
                "CONTENT, " +
                "LAST_MODIFIED_TIME, " +
                "CREATION_TIME) " +
                "VALUES(?,?,?,?,?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, e.getId());
            statement.setString(2, e.getOwnerId());
            statement.setString(3, e.getTitle());
            statement.setString(4, String.join(", ", e.getTags()));
            statement.setBoolean(5, e.isFavorite());
            statement.setString(6, e.getContent());
            statement.setString(7, TimeUtils.format(e.getLastModifiedTime(), "dd/MM/yyyy HH:mm"));
            statement.setString(8, TimeUtils.format(e.getCreationTime(), "dd/MM/yyyy HH:mm"));

            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Failed to insert element for ID='" + e.getId() + "' to the database table='NOTES'. \nReason: " + ex.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean update(Note e) {

        String sql = "UPDATE NOTES SET " +
                "OWNER_ID = ?, " +
                "TITLE = ?, " +
                "TAGS = ?, " +
                "FAVORITE = ?, " +
                "CONTENT = ?, " +
                "LAST_MODIFIED_TIME = ? " +
                "WHERE ID = ?";


        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, e.getOwnerId());
            statement.setString(2, e.getTitle());
            statement.setString(3, String.join(", ", e.getTags()));
            statement.setBoolean(4, e.isFavorite());
            statement.setString(5, e.getContent());
            statement.setString(6, TimeUtils.format(e.getLastModifiedTime(), "dd/MM/yyyy HH:mm"));
            statement.setString(7, e.getId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Failed to update element for ID='" + e.getId() + "' to the database table='NOTES'. \nReason: " + ex.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(Note e) {

        String sql = "DELETE FROM NOTES WHERE ID = '" + e.getId() + "'";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Failed to delete element for ID='" + e.getId() + "' from the database table='NOTES'. \nReason: " + ex.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public Note findById(String id) {

        String sql = "SELECT * FROM NOTES WHERE ID = '" + id + "'";

        Note note = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                note = new NoteImpl.Builder(resultSet.getString("TITLE"))
                        .setContent(resultSet.getString("CONTENT"))
                        .setFavorite(resultSet.getBoolean("FAVORITE"))
                        .setTags(new ArrayList<>(Arrays.asList(resultSet.getString("TAGS").split(", "))))
                        .setLastModifiedTime(TimeUtils.parse(resultSet.getString("LAST_MODIFIED_TIME"), "dd/MM/yyyy HH:mm"))
                        .setCreationTime(TimeUtils.parse(resultSet.getString("CREATION_TIME"), "dd/MM/yyyy HH:mm"))
                        .setOwnerId(resultSet.getString("OWNER_ID"))
                        .setId(id)
                        .build();
            }

            resultSet.close();
        } catch (SQLException ex) {
            logger.error("Failed to find element for ID='" + id + "' from the database table='NOTES'. \nReason: " + ex.getMessage());
        }

        return note;
    }

    @Override
    public List<Note> findAll() {

        String sql = "SELECT ID FROM NOTES";

        final List<Note> notes = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String id = resultSet.getString("ID");

                notes.add(findById(id));
            }

            resultSet.close();
        } catch (SQLException ex) {
            logger.error("Failed to find elements from the database table='NOTES'. \nReason: " + ex.getMessage());
        }

        return notes;
    }

    @Override
    public List<String> getValues(String column) {

        String sql = "SELECT " + column + " FROM NOTES";

        final List<String> values = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                values.add(resultSet.getString(column));
            }

        } catch (SQLException ex) {
            logger.error("Failed to retrieve values from database table='NOTES(" + column + ")'. \nReason: " + ex.getMessage());
        }
        return values;
    }

    @Override
    public int getCount() {

        String sql = "SELECT COUNT(*) FROM NOTES";

        int count = 0;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            count = resultSet.getInt(1);
            resultSet.close();
        } catch (SQLException ex) {
            logger.error("Failed to count elements from database table='NOTES'. \nReason: " + ex.getMessage());
        }

        return count;
    }

    @Nonnull
    @Override
    public Connection getConnection() {
        return connection;
    }
}
