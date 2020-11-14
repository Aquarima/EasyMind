package com.easymind.core.utils.cache;

import com.easymind.api.entities.ProjectItem;
import com.easymind.api.utils.cache.ProjectItemCacheStore;
import com.easymind.api.data.DataAccessObject;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProjectItemCacheStoreImpl<T extends ProjectItem> extends AbstractCacheStore<T> implements ProjectItemCacheStore<T> {

    public ProjectItemCacheStoreImpl(@Nonnull DataAccessObject<T> dataSource) {
        super(dataSource);
    }

    @Nonnull
    @Override
    public List<T> getElementsByTitle(@Nonnull String title, boolean ignoreCase) {

        Objects.requireNonNull(title);

        final List<T> items = new ArrayList<>();

        for (T item : asList()) {

            if (ignoreCase && item.getTitle().equalsIgnoreCase(title)) {
                items.add(item);
            }

            if (!ignoreCase && item.getTitle().equals(title)) {
                items.add(item);
            }

        }

        return Collections.unmodifiableList(items);
    }

    @Nonnull
    @Override
    public List<T> getElementsByTags(@Nonnull List<String> tags) {

        Objects.requireNonNull(tags);

        return stream()
                .filter(item -> item.getTags().containsAll(tags))
                .collect(Collectors.toUnmodifiableList());
    }

    @Nonnull
    @Override
    public List<T> getElementsByOwnerId(@Nonnull String ownerId) {

        Objects.requireNonNull(ownerId);

        return stream()
                .filter(item -> Objects.equals(item.getOwnerId(), ownerId))
                .collect(Collectors.toUnmodifiableList());
    }
}
