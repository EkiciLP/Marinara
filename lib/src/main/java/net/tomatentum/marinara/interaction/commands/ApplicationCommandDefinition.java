package net.tomatentum.marinara.interaction.commands;

import java.util.ArrayList;
import java.util.List;

public record ApplicationCommandDefinition(
    List<ExecutableCommandDefinition> executableDefinitons,
    String applicationCommand
) {

    public ApplicationCommandDefinition(String applicationCommand) {
        this(new ArrayList<>(), applicationCommand);
    }

    public ApplicationCommandDefinition addExecutableCommand(ExecutableCommandDefinition def) {
        executableDefinitons.add(def);
        return this;
    }

}
