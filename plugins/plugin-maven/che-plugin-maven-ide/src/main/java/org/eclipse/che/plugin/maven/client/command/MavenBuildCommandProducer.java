/*******************************************************************************
 * Copyright (c) 2012-2016 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.plugin.maven.client.command;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.che.api.core.model.machine.Machine;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.command.CommandImpl;
import org.eclipse.che.ide.api.command.CommandProducer;
import org.eclipse.che.ide.api.resources.Project;
import org.eclipse.che.ide.api.resources.Resource;

import java.util.Collections;
import java.util.Set;

/**
 * Produces commands for building module with Maven.
 *
 * @author Artem Zatsarynnyi
 */
@Singleton
public class MavenBuildCommandProducer implements CommandProducer {

    private final AppContext appContext;

    @Inject
    public MavenBuildCommandProducer(AppContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public String getName() {
        return "Build '" + getFileName() + "' module";
    }

    private String getFileName() {
        final Resource[] resources = appContext.getResources();
        if (resources == null || resources.length != 1) {
            return "";
        }

        final Optional<Project> projectOptional = appContext.getResource().getRelatedProject();

        if (!projectOptional.isPresent()) {
            return "";
        }

        final Project project = projectOptional.get();
        if (project.isTypeOf("maven")) {
            return project.getName();
        }

        return "";
    }

    @Override
    public boolean isApplicable() {
        final Resource[] resources = appContext.getResources();
        if (resources == null || resources.length != 1) {
            return false;
        }

        final Resource resource = appContext.getResource();
        final Optional<Project> projectOptional = appContext.getResource().getRelatedProject();

        if (!projectOptional.isPresent()) {
            return false;
        }

        final Project project = projectOptional.get();
        if (project.isTypeOf("maven") && resource.isFile()) {
            return "pom.xml".equals(resource.getName());
        }

        return false;
    }

    @Override
    public CommandImpl createCommand(Machine machine) {
        final Optional<Project> projectOptional = appContext.getResource().getRelatedProject();

        String workingDirectory = null;

        if (projectOptional.isPresent()) {
            workingDirectory = projectOptional.get().getPath();
        }

        MavenCommandModel mavenCommandModel = new MavenCommandModel("/projects" + workingDirectory, "clean install");

        return new CommandImpl("name", mavenCommandModel.toCommandLine(), "mvn");
    }

    @Override
    public Set<String> getMachineTypes() {
        return Collections.emptySet();
    }
}
