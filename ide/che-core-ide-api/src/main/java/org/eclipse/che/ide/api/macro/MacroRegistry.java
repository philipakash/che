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

import java.util.List;
import java.util.Set;

/**
 * Registry for {@link CommandMacro}s.
 *
 * @author Artem Zatsarynnyi
 * @see CommandMacro
 */
public interface MacroRegistry {

    /** Register set of macros. */
    void register(Set<CommandMacro> macros);

    /** Unregister the given macro. */
    void unregister(CommandMacro macro);

    /** Returns the names of all registered {@link CommandMacro}s. */
    Set<String> getNames();

    /** Returns {@link CommandMacro} by it's name. */
    CommandMacro getMacro(String name);

    /** Returns all registered {@link CommandMacro}s. */
    List<CommandMacro> getMacros();
}
