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
package org.eclipse.che.ide.api.command.macro;

import org.eclipse.che.api.core.model.machine.Machine;
import org.eclipse.che.api.promises.client.Promise;
import org.eclipse.che.ide.api.command.CommandImpl;
import org.eclipse.che.ide.api.command.CommandManager;

/**
 * Expands the {@link CommandMacro}s in a command line.
 *
 * @author Artem Zatsarynnyi
 * @see CommandMacro
 * @see CommandManager#executeCommand(CommandImpl, Machine)
 */
public interface MacroProcessor {

    /**
     * Expands all macros in the given {@code commandLine}.
     * <p>If {@link MacroProcessor} is unable to find a macro, the macro will not be expanded.
     */
    Promise<String> expandMacros(String commandLine);
}
