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

import com.easymind.api.behaviors.Identifiable;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.List;

public interface ProjectItem extends Identifiable, Comparable<ProjectItem> {

    /**
     * Gets the title for this {@code Item} title.
     *
     * @return Non-null title.
     */

    @Nonnull
    String getTitle();

    /**
     * Sets the title for this {@code Item}.
     *
     * @param title
     *        The title to set.
     *
     * @throws java.lang.NullPointerException
     *         If title is null.
     */

    void setTitle(@Nonnull String title);

    /**
     * Returns a {@code boolean} that say if the {@code Item} is marked as favorite.
     *
     * @return True, If this item is favorite.
     */

    boolean isFavorite();

    /**
     * Defines if this {@code Item} is favorite.
     *
     * @param favorite
     *        True, To set this item favorite.
     */

    void setFavorite(boolean favorite);

    /**
     * Gets the tags for this {@code Item} that describes the item characteristics and allows easy grouping of items that
     * have the same characteristics.
     *
     * @return List of tags.
     */

    @Nonnull
    List<String> getTags();

    /**
     * Sets the tags for this {@code Item}.
     *
     * @param tags
     *        The tags to set.
     *
     * @throws java.lang.NullPointerException
     *         If tags is null.
     */

    void setTags(@Nonnull List<String> tags);

    /**
     * Gets the last modified time for this {@code Item}.
     * This returns the item creation time if the item has been not modified since its creation.
     *
     * @see #getCreationTime()
     *
     * @return Non-null last modified time.
     */

    @Nonnull
    Date getLastModifiedTime();

    /**
     * Sets the last modified time for this {@code item}.
     *
     * @param lastModifiedTime
     *        The last modified time to set.
     *
     * @throws java.lang.NullPointerException
     *         If last modified time is null.
     */

    void setLastModifiedTime(@Nonnull Date lastModifiedTime);

    /**
     * Gets the creation time for this {@code Item}.
     *
     * @return Non-null creation time.
     */

    @Nonnull
    Date getCreationTime();

    /**
     * Gets the owner {@link com.easymind.api.entities.Project Project} id for this {@code Item}..
     *
     * @return Non-null owner id.
     */

    @Nonnull
    String getOwnerId();

    /**
     * Sets the owner {@link com.easymind.api.entities.Project Project} id for this {@code Item}.
     *
     * @param ownerId
     *        The owner id to set.
     *
     * @throws java.lang.NullPointerException
     *         If owner id is null.
     */

    void setOwnerId(@Nonnull String ownerId);

    /**
     * Compares the current and the provided {@code Item} last modified time.
     *
     * @param o
     *        The item to be compared.
     *
     * @return 0 if the current item last modified time is equal to the provided item last modified time, a value less than 0 if this time is
     * before and greater if after.
     */

    @Override
    default int compareTo(ProjectItem o) {
        return o.getLastModifiedTime().compareTo(getLastModifiedTime());
    }
}
