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

package com.easymind.api.behaviors;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public enum Language {

    EN("English", "images/flags/united_kingdom.png", Locale.ENGLISH),

    FR("Français", "images/flags/france.png", Locale.FRENCH);

    private final String name;

    private final String flag;

    private final Locale locale;

    Language(String name, String flag, Locale locale) {
        this.name = name;
        this.flag = flag;
        this.locale = locale;
    }

    /**
     * Gets the type country language name for this type.
     *
     * @return The language country name.
     */

    public String getName() {
        return name;
    }

    /**
     * Gets the flag url for this type.
     *
     * @return The language flag url.
     */

    public String getFlag() {
        return flag;
    }

    /**
     * Gets the {@link java.util.Locale Locale} for this type.
     *
     * @return The language locale.
     */

    public Locale getLocale() {
        return locale;
    }

    public ResourceBundle getResourceBundle() {

        String language = locale.getLanguage();

        return ResourceBundle.getBundle("assets/lang/" + language + "_" + language.toUpperCase());
    }

    public static Language getDefault() {
        return Stream.of(values())
                .filter(language -> language.getLocale() == Locale.getDefault())
                .findAny()
                .orElse(Language.EN);
    }
}
