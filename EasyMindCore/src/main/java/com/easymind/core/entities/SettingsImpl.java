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

package com.easymind.core.entities;

import com.easymind.api.behaviors.Language;
import com.easymind.api.entities.Settings;

import javax.annotation.Nonnull;
import java.util.Objects;

public class SettingsImpl implements Settings {

    private Language language;

    @Nonnull
    @Override
    public Language getLanguage() {
        return Objects.isNull(language) ? Language.getDefault() : language;
    }

    @Override
    public void setLanguage(@Nonnull Language language) {
        this.language = Objects.requireNonNull(language);
    }
}
