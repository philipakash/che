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

import org.eclipse.che.api.core.model.machine.Machine;
import org.eclipse.che.api.promises.client.Function;
import org.eclipse.che.api.promises.client.FunctionException;
import org.eclipse.che.api.promises.client.Promise;
import org.eclipse.che.api.promises.client.js.Promises;
import org.eclipse.che.ide.api.command.CommandImpl;
import org.eclipse.che.ide.api.command.CommandManager;
import org.eclipse.che.ide.api.command.macro.CommandMacro;
import org.eclipse.che.ide.api.command.macro.CommandMacroRegistry;
import org.eclipse.che.ide.api.command.macro.MacroProcessor;

import java.util.Iterator;

/**
 * Implementation of {@link MacroProcessor}.
 *
 * @author Artem Zatsarynnyi
 * @see CommandMacro
 * @see CommandManager#executeCommand(CommandImpl, Machine)
 */
public class MacroProcessorImpl implements MacroProcessor {

    private final CommandMacroRegistry macroRegistry;

    @Inject
    public MacroProcessorImpl(CommandMacroRegistry macroRegistry) {
        this.macroRegistry = macroRegistry;
    }

    @Override
    public Promise<String> expandMacros(String commandLine) {
        Promise<String> promise = Promises.resolve(null);
        CommandLineContainer commandLineContainer = new CommandLineContainer(commandLine);
        return expandMacros(promise, commandLineContainer, macroRegistry.getMacros().iterator());
    }

    private Promise<String> expandMacros(Promise<String> promise,
                                         CommandLineContainer commandLineContainer,
                                         Iterator<CommandMacro> iterator) {
        if (!iterator.hasNext()) {
            return promise;
        }

        final CommandMacro provider = iterator.next();

        Promise<String> derivedPromise = promise.thenPromise(expandMacros(commandLineContainer, provider));

        return expandMacros(derivedPromise, commandLineContainer, iterator);
    }

    private Function<String, Promise<String>> expandMacros(final CommandLineContainer commandLineContainer, final CommandMacro macro) {
        return new Function<String, Promise<String>>() {
            @Override
            public Promise<String> apply(String arg) throws FunctionException {
                return macro.expand().thenPromise(new Function<String, Promise<String>>() {
                    @Override
                    public Promise<String> apply(String arg) throws FunctionException {
                        commandLineContainer.setCommandLine(commandLineContainer.getCommandLine().replace(macro.getName(), arg));
                        return Promises.resolve(commandLineContainer.getCommandLine());
                    }
                });
            }
        };
    }

    private class CommandLineContainer {
        String commandLine;

        CommandLineContainer(String commandLine) {
            this.commandLine = commandLine;
        }

        String getCommandLine() {
            return commandLine;
        }

        void setCommandLine(String commandLine) {
            this.commandLine = commandLine;
        }
    }
}
