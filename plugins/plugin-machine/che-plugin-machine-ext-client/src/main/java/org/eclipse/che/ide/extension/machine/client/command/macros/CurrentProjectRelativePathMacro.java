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
package org.eclipse.che.ide.extension.machine.client.command.macros;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.che.api.promises.client.Promise;
import org.eclipse.che.api.promises.client.PromiseProvider;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.macro.Macro;
import org.eclipse.che.ide.api.resources.Resource;

/**
 * Provides relative path to specific project. Path to project resolves from current workspace root.
 * e.g. /project_name.
 *
 * Need for IDEX-3924 as intermediate solution.
 *
 * @author Vlad Zhukovskiy
 */
@Singleton
public class CurrentProjectRelativePathMacro implements Macro {

    private static final String KEY = "${current.project.relpath}";

    private       AppContext      appContext;
    private final PromiseProvider promises;

    @Inject
    public CurrentProjectRelativePathMacro(AppContext appContext, PromiseProvider promises) {
        this.appContext = appContext;
        this.promises = promises;
    }

    @Override
    public String getName() {
        return KEY;
    }

    @Override
    public String getDescription() {
        return "The path to the currently selected project relative to /projects. Effectively removes the /projects path from any project reference";
    }

    @Override
    public Promise<String> expand() {
        final Resource[] resources = appContext.getResources();

        if (resources != null && resources.length == 1) {
            return promises.resolve(resources[0].getLocation().toString());
        }

        return promises.resolve("");
    }
}
