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

package com.easymind.api.entities;

import javafx.scene.paint.Color;

import javax.annotation.Nonnull;
import java.util.Arrays;

public enum Priority {

    /** Placeholder for unsupported types */

    UNKNOWN(-1, Color.LIGHTGRAY),

    /** Lowest level of priority */

    LOW(1, Color.CYAN),

    /** Normal level of priority */

    NORMAL(2, Color.GREEN),

    /** Medium level of priority */

    MEDIUM(3, Color.ORANGE),

    /** Highest level of priority */

    HIGH(4, Color.RED);

    private final int key;

    private final Color color;

    Priority(int key, Color color) {
        this.key = key;
        this.color = color;
    }

    /**
     * Gets the raw key for this priority.
     *
     * @return The key or -1 for {@link #UNKNOWN}.
     */

    public int getKey() {
        return key;
    }

    /**
     * Gets the {@link javafx.scene.paint.Color Color} for this priority.
     *
     * @return The color or light grey for {@link #UNKNOWN}.
     */

    public Color getColor() {
        return color;
    }

    /**
     * Retrieves the corresponding {@code Priority} for the given key.
     *
     * @param key
     *        The priority key.
     *
     * @return The priority for the key or {@link #UNKNOWN}.
     */

    @Nonnull
    public static Priority fromKey(int key) {
        return Arrays.stream(values())
                .filter(pl -> pl.key == key)
                .findAny()
                .orElse(UNKNOWN);
    }
}
