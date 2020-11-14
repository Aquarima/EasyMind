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

import com.easymind.api.behaviors.Language;

import javax.annotation.Nonnull;

/**
 * Settings interface that allows to interact with the application behaviors.
 */

public interface Settings {

    /**
     * Gets the current {@link com.easymind.api.behaviors.Language Language} used by the application to display text elements.
     *
     * @return Non-null currently used language.
     */

    @Nonnull
    Language getLanguage();

    /**
     * Sets the {@link com.easymind.api.behaviors.Language Language} that will be used by the application to display text elements.
     *
     * @param language
     *        The language to use.
     *
     * @throws java.lang.NullPointerException
     *         If language is null.
     */

    void setLanguage(@Nonnull Language language);
}
