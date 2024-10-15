package net.tomatentum.marinara.registry;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.tomatentum.marinara.handler.InteractionHandler;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.commands.ApplicationCommandDefinition;
import net.tomatentum.marinara.interaction.commands.ExecutableCommandDefinition;
import net.tomatentum.marinara.interaction.methods.CommandInteractionMethod;
import net.tomatentum.marinara.interaction.methods.InteractionMethod;
import net.tomatentum.marinara.wrapper.LibraryWrapper;

public class InteractionRegistry {
    private List<InteractionMethod> interactionMethods;
    private LibraryWrapper wrapper;

    public InteractionRegistry(LibraryWrapper wrapper) {
        this.wrapper = wrapper;
        wrapper.subscribeInteractions(this::handle);
    }

    public void addInteractions(InteractionHandler interactionHandler) {
        for (Method method : interactionHandler.getClass().getMethods()) {
            interactionMethods.add(InteractionMethod.create(method, interactionHandler, wrapper));
        }
    }

    public void registerCommands() {
        List<ApplicationCommandDefinition> defs = new ArrayList<>();
        List<ExecutableCommandDefinition> execDefs = interactionMethods.stream()
            .filter((x) -> x.getClass().isAssignableFrom(CommandInteractionMethod.class))
            .map((x) -> ((CommandInteractionMethod)x).getCommandDefinition())
            .toList();

        execDefs.forEach((def) -> {
            Optional<ApplicationCommandDefinition> appDef = defs.stream()
                .filter((x) -> x.getApplicationCommand().equals(def.applicationCommand()))
                .findFirst();
            if (appDef.isPresent())
                appDef.get().addExecutableCommand(def);
            else
                defs.add(new ApplicationCommandDefinition(def.applicationCommand()).addExecutableCommand(def));
        });
    }

    public void handle(Object context) {
        interactionMethods.forEach((m) -> {
            InteractionType type = wrapper.getInteractionType(context.getClass());
            if (m.getType().equals(type))
                m.run(context);
        });
    }
}
