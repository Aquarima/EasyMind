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

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Date;

public enum Status {

    /** Placeholder for unsupported types */

    UNKNOWN(-1, "/images/status/unknown.jpg", Color.LIGHTGREY),

    /** Waiting for being active status */

    PENDING(1, "/images/status/pending.jpg", Color.YELLOW),

    /** Started and active status */

    ACTIVE(2, "/images/status/active.jpg", Color.LIGHTGREEN),

    /** Ended and non-active status */

    DONE(3, "/images/status/done.jpg", Color.LIGHTBLUE),

    /* Non-done and passed date */

    EXPIRED(4, "/images/status/expired", Color.GREY);

    private final int key;

    private final String icon;

    private final Color color;

    Status(int key, String icon, Color color) {
        this.key = key;
        this.icon = icon;
        this.color = color;
    }

    /**
     * Gets the raw key for this status.
     *
     * @return Key or -1 for {@link #UNKNOWN}.
     */

    public int getKey() {
        return key;
    }

    /**
     * Gets the icon {@link javafx.scene.image.Image Image} for this status.
     *
     * @return Icon image for this status.
     */

    public Image getIcon() {
        return new Image(getClass().getResource(icon).toString());
    }

    /**
     * Gets the {@link javafx.scene.paint.Color Color} for this status.
     *
     * @return The color or light grey for {@link #UNKNOWN}.
     */

    public Color getColor() {
        return color;
    }

    /**
     * Returns the type for the provided start and end {@link java.util.Date Date}.
     * <p>
     * <br>Case start or end is null this returns {@link #UNKNOWN}.
     * <p>
     * <br>Case start is after now this returns {@link #PENDING}.
     * <p>
     * <br>Case end is after now this returns {@link #ACTIVE}.
     * <p>
     * <br>Case end is before now this returns {@link #EXPIRED}.
     *
     * @param start
     *        The start date/time.
     *
     * @param end
     *        The end date/time.
     *
     * @return The status for the provided dates/times or {@link #UNKNOWN}.
     */

    @Nonnull
    public static Status of(Date start, Date end) {

        if(start == null || end == null) {
            return Status.UNKNOWN;
        }

        final Date now = new Date();

        if (start.after(now)) {
            return Status.PENDING;
        }

        if (end.after(now)) {
            return Status.ACTIVE;
        }

        return Status.EXPIRED;
    }

    /**
     * Returns the corresponding {@code Status} for the given key.
     *
     * @param key
     *        The status key.
     *
     * @return The status for the key or {@link #UNKNOWN}.
     */

    @Nonnull
    public static Status fromKey(int key) {
        return Arrays.stream(values())
                .filter(st -> st.getKey() == key)
                .findAny()
                .orElse(UNKNOWN);
    }
}
