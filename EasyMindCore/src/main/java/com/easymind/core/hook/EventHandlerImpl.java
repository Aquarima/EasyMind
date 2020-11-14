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

package com.easymind.core.hook;

import com.easymind.api.events.Event;
import com.easymind.api.hook.EventHandler;

import javax.annotation.Nonnull;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class EventHandlerImpl implements EventHandler {

    private final Queue<Event> events;

    public EventHandlerImpl() {
        this.events = new LinkedBlockingQueue<>();
        this.init();
    }

    private void init() {

        Thread thread = new Thread(() -> {

            while (true) {

                Event event = events.poll();

                if (event != null) {
                    event.onEvent();
                }
            }

        }, "EventHandler");

        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void handle(@Nonnull Event event) {
        events.add(event);
    }
}
