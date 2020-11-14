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
import javax.annotation.Nullable;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

/**
 * Allows to perform IO actions by using only one line of code that helps to get a more readable code.
 */

public class IOUtils {

    /**
     * Reads the provided file using the {@link java.nio.file.Files Files} and returns its content as a List.
     *
     * @param path
     *        The target file {@link java.nio.file.Path Path}.
     *
     * @return Possibly-null file lines.
     */

    public static List<String> readAllLines(@Nonnull Path path) {

        Objects.requireNonNull(path);

        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Reads the provided file using the {@link java.nio.file.Files Files} and returns its content as String.
     *
     * @param path
     *        The target file {@link java.nio.file.Path Path}.
     *
     * @return Possibly-null provided path file content.
     */

    public static String read(@Nonnull Path path) {

        Objects.requireNonNull(path);

        try {
            return Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Writes the provided text with a {@link java.io.BufferedWriter BufferedWriter} to the provided file.
     *
     * @param str
     *        The text to write.
     *
     * @param target
     *        The target file {@link java.nio.file.Path Path}.
     */

    public static void write(@Nonnull String str, @Nonnull Path target) {

        Objects.requireNonNull(target);

        try {
            BufferedWriter writer = Files.newBufferedWriter(target);
            writer.write(str);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Duplicates the provided file path to the provided destination.
     *
     * @param source
     *        The target source {@link java.nio.file.Path Path} to duplicate.
     *
     * @param destination
     *        The target duplicated file destination {@link java.nio.file.Path Path}.
     *
     *  @param copyOption
     *         The {@link java.nio.file.StandardCopyOption StandardCopyOption}.
     *
     * @return The duplicated file {@link java.nio.file.Path Path}.
     */

    public static Path copy(@Nonnull Path source, @Nonnull Path destination, @Nullable StandardCopyOption copyOption) {

        try {

            if (copyOption == null) {
                return Files.copy(source, destination);
            }

            return Files.copy(source, destination, copyOption);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Duplicates the provided file path to the provided destination.
     *
     * @param source
     *        The target source {@link java.nio.file.Path Path} to duplicate.
     *
     * @param destination
     *        The target duplicated file destination {@link java.io.InputStream InputStream}.
     *
     *  @param copyOption
     *         The {@link java.nio.file.StandardCopyOption StandardCopyOption}.
     *
     * @return The number of bytes read or written.
     */

    public static long copy(@Nonnull InputStream source, @Nonnull Path destination, @Nullable StandardCopyOption copyOption) {

        try {

            if (copyOption == null) {
                return Files.copy(source, destination);
            }

            return Files.copy(source, destination, copyOption);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Creates a file to the provided path.
     *
     * @param path
     *        The target file {@link java.nio.file.Path Path} to create.
     *
     * @param asDirectory
     *        True, To create a directory else this creates a file.
     */

    public static Path create(@Nonnull Path path, boolean asDirectory) {

        try {

            if (!Files.exists(path)) {

                if (asDirectory) {
                    Files.createDirectory(path);
                }

                Files.createFile(path);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }

    /**
     * Deletes the file and if its a directory, deletes its children.
     *
     * @param target
     *        The target file {@link java.nio.file.Path Path} to delete.
     */

    public static void delete(@Nonnull Path target) {

        try {

            if (Files.isDirectory(target)) {
                Files.list(target).forEach(IOUtils::delete);
            }

            Files.deleteIfExists(target);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
