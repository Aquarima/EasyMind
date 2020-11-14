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
import com.easymind.api.entities.Project;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class ProjectEvent extends AbstractEvent {

    protected final Project project;

    public ProjectEvent(@Nonnull EasyMind api, long time, @Nonnull Project project) {
        super(api, time);
        this.project = Objects.requireNonNull(project);
    }

    /**
     * Returns the corresponding {@link com.easymind.api.entities.Project Project} for this event.
     *
     * @return Non-null
     */

    @Nonnull
    public Project getProject() {
        return project;
    }
}
