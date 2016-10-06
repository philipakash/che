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
package org.eclipse.che.ide.command.macro;

import com.google.inject.Inject;

import org.eclipse.che.ide.api.command.macro.CommandMacro;
import org.eclipse.che.ide.api.command.macro.CommandMacroRegistry;
import org.eclipse.che.ide.util.loging.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation for {@link CommandMacroRegistry}.
 *
 * @author Artem Zatsarynnyi
 */
public class CommandMacroRegistryImpl implements CommandMacroRegistry {

    private final Map<String, CommandMacro> macros;

    public CommandMacroRegistryImpl() {
        this.macros = new HashMap<>();
    }

    @Inject(optional = true)
    public void register(Set<CommandMacro> macros) {
        for (CommandMacro macro : macros) {
            final String name = macro.getName();
            if (this.macros.containsKey(name)) {
                Log.warn(CommandMacroRegistryImpl.class, "Command macro '" + name + "' is already registered.");
            } else {
                this.macros.put(name, macro);
            }
        }
    }

    @Override
    public void unregister(CommandMacro macro) {
        macros.remove(macro.getName());
    }

    @Override
    public CommandMacro getMacro(String name) {
        return macros.get(name);
    }

    @Override
    public List<CommandMacro> getMacros() {
        return new ArrayList<>(macros.values());
    }

    @Override
    public Set<String> getNames() {
        return macros.keySet();
    }
}
