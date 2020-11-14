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

public interface Event {

    void onEvent();

    /**
     * Returns the corresponding EasyMind instance for this event.
     *
     * @return Non-null corresponding EasyMind instance.
     */

    @Nonnull
    EasyMind getEasyMind();

    /**
     * Returns the number of milliseconds starting from 1970 since the event has been created.
     *
     * @return The number of seconds since the event has been created.
     */

    long getTime();
}
