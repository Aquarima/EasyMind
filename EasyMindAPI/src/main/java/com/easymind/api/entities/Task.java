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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;
import java.util.Optional;

public interface Task extends ProjectItem {

    /**
     * Gets the {@link com.easymind.api.entities.Priority Priority} for this {@code Task}.
     *
     * @return Non-null priority.
     */

    @Nonnull
    Priority getPriority();

    /**
     * Sets the {@link com.easymind.api.entities.Priority Priority} for this {@code Task}.
     *
     * @param priority
     *        The priority to set.
     *
     * @throws java.lang.NullPointerException
     *         If priority is null.
     */

    void setPriority(@Nonnull Priority priority);

    /**
     * If the current {@code Task} is done.
     *
     * @return True, If the task is done.
     */

    boolean isDone();

    /**
     * Defines if this {@code Task} is done.
     *
     * @param done
     *        True, To set this task done.
     */

    void setDone(boolean done) ;

    /**
     * Gets the {@link java.util.Optional Optional} start {@link java.util.Date Date} for this {@code Task}.
     *
     * @return Non-null Optional start date.
     */

    @Nonnull
    Optional<Date> getStart();

    /**
     * Sets the start {@link java.util.Date Date} for this {@code Task}.
     *
     * @param startDate
     *        The start date to set.
     */

    void setStart(@Nullable Date startDate);

    /**
     *  Gets the {@link java.util.Optional Optional} end {@link java.util.Date Date} for this {@code Task}.
     *
     * @return Non-null Optional end date.
     */

    @Nonnull
    Optional<Date> getDeadline();

    /**
     * Sets the end {@link java.util.Date Date} for this {@code Task}
     *
     * @param deadline
     *        The end date to set.
     */

    void setDeadline(@Nullable Date deadline);

    /**
     * Gets the {@link com.easymind.api.entities.Status StatusType} for this task using start and end date or {@link Status#DONE}
     * if the task is done.
     *
     * @see Status#of(Date, Date)
     *
     * @return Non-null status.
     */

    @Nonnull
    Status getStatus();

    /**
     * Gets the text for this {@code Task}.
     *
     * @return Non-null text.
     */

    @Nonnull
    String getContent();

    /**
     * Sets the content for this {@code Task}.
     *
     * @param content
     *        The content to set.
     *
     * @throws java.lang.NullPointerException
     *         If content is null.
     */

    void setContent(@Nonnull String content);
}
