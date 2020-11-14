/*
 * Copyright 2020 Exalow (Discord: Exalow#6074)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.easymind.api.events;

import com.easymind.api.EasyMind;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class AbstractEvent implements Event {

    protected final EasyMind api;

    protected final long time;

    /**
     * @param api
     *        The corresponding EasyMind instance.
     *
     * @param time
     *        The current time in milliseconds starting from 1970.
     *
     * @throws java.lang.NullPointerException
     *         If api is null.
     *
     * @see System#currentTimeMillis()
     */

    public AbstractEvent(@Nonnull EasyMind api, long time) {
        this.api = Objects.requireNonNull(api);
        this.time = time;
    }

    @Nonnull
    @Override
    public EasyMind getEasyMind() {
        return api;
    }

    @Override
    public long getTime() {
        return time;
    }
}
