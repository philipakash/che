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
package org.eclipse.che.ide.api.macro;

import org.eclipse.che.api.core.model.machine.Machine;
import org.eclipse.che.api.promises.client.Promise;
import org.eclipse.che.ide.api.command.CommandImpl;
import org.eclipse.che.ide.api.command.CommandManager;

/**
 * Macro which can be used for the simple text substitutions.
 * Mainly used in command lines before sending command to the machine for execution.
 *
 * @author Artem Zatsarynnyi
 * @see MacroProcessor#expandMacros(String)
 * @see CommandManager#executeCommand(CommandImpl, Machine)
 */
public interface CommandMacro {

    /** Returns macro name. The recommended syntax is ${macro.name}. */
    String getName();

    /** Returns macro description. */
    String getDescription();

    /**
     * Expand macro into the real value.
     *
     * @return a promise that resolves to the real value associated with macro
     */
    Promise<String> expand();
}
