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

package com.easymind.api.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A time utility class.
 */

public class TimeUtils {

    /**
     * Parses the provided date/time with the given format.
     *
     * @param date The String to parse.
     *
     * @param format
     *        The date/time format.
     *
     * @return Possibly-null parsed date/time.
     */

    public static Date parse(String date, String format) {

        if (date != null && format != null) {
            try {
                return new SimpleDateFormat(format).parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Formats the date/time to the provided format.
     *
     * @param date
     *        The date/time to format.
     *
     * @param format
     *        The date/time format.
     *
     * @return Possibly-null String of the formatted date/time.
     */

    public static String format(Date date,String format) {

        if (date == null || format == null) {
            return null;
        }

        return new SimpleDateFormat(format).format(date);
    }
}
