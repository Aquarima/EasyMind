package com.easymind.core.utils.cache;

import com.easymind.api.entities.Project;
import com.easymind.api.utils.cache.ProjectCacheStore;
import com.easymind.api.data.DataAccessObject;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProjectCacheStoreImpl extends AbstractCacheStore<Project> implements ProjectCacheStore {

    public ProjectCacheStoreImpl(@Nonnull DataAccessObject<Project> dataSource) {
        super(dataSource);
    }

    @Nonnull
    @Override
    public List<Project> getElementsByName(@Nonnull String name, boolean ignoreCase) {

        Objects.requireNonNull(name);

        final List<Project> projects = new ArrayList<>();

        for (Project project : asList()) {

            if (ignoreCase && project.getName().equalsIgnoreCase(name)) {
                projects.add(project);
            }

            if (!ignoreCase && project.getName().equals(name)) {
                projects.add(project);
            }

        }

        return Collections.unmodifiableList(projects);
    }

    @Nonnull
    @Override
    public List<Project> getElementsByTags(@Nonnull List<String> tags) {
        return stream()
                .filter(project -> project.getTags().containsAll(tags))
                .collect(Collectors.toUnmodifiableList());
    }
}
