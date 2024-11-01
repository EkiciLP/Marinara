package net.tomatentum.marinara.interaction.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommandGroup;

public class SlashCommandDefinition {
    private List<ExecutableSlashCommandDefinition> executableDefinitons;
    private SlashCommand slashCommand;
    private boolean isRootCommand;

    public SlashCommandDefinition(SlashCommand applicationCommand) {
        this.executableDefinitons = new ArrayList<>();
        this.slashCommand = applicationCommand;
    }

    public SlashCommandDefinition addExecutableCommand(ExecutableSlashCommandDefinition def) {
        if (def.applicationCommand() != null) {
            if (slashCommand == null)
                this.slashCommand = def.applicationCommand();
            if (!this.slashCommand.name().equals(def.applicationCommand().name()))
                throw new IllegalArgumentException(def + ": has a non matching Application Command description. Please edit it to equal all other descriptions or remove it to use other definitions descriptions");
        }

        if (executableDefinitons.isEmpty())
            this.isRootCommand = def.isRootCommand();

        if ((isRootCommand && !def.isRootCommand()) || (!isRootCommand && def.isRootCommand())) {
            throw new IllegalArgumentException(def + ": cannot have subcommands and rootcommand definitions together");
        }
        
        executableDefinitons.add(def);
        return this;
    }

    public SubCommandGroup[] getSubCommandGroups() {
        List<SubCommandGroup> subCommandGroups = Arrays.stream(getExecutableDefinitons())
            .filter((x) -> x.subCommandGroup() != null)
            .map((x) -> x.subCommandGroup())
            .toList();

        HashMap<String, SubCommandGroup> subCommandGroupMap = new HashMap<>();
        subCommandGroups.forEach((x) -> {
            SubCommandGroup current = subCommandGroupMap.get(x.name());
            if (current == null || (current.description().isBlank() && !x.description().isBlank()))
                subCommandGroupMap.put(x.name(), x);
        });

        return subCommandGroupMap.values().toArray(new SubCommandGroup[0]);
    }

    public SubCommand[] getSubCommands(String groupName) {
        List<SubCommand> subCommands;
        if (groupName == null)
            subCommands = Arrays.stream(getExecutableDefinitons())
            .filter((x) -> x.subCommandGroup() == null && x.subCommand() != null)
            .map((x) -> x.subCommand())
            .toList();
        else 
            subCommands = Arrays.stream(getExecutableDefinitons())
            .filter((x) -> x.subCommandGroup().name().equals(groupName) && x.subCommand() != null)
            .map((x) -> x.subCommand())
            .toList();
        
        HashMap<String, SubCommand> subCommandMap = new HashMap<>();
        subCommands.forEach((x) -> {
            SubCommand current = subCommandMap.get(x.name());
            if (current == null || (current.description().isBlank() && !x.description().isBlank()))
                subCommandMap.put(x.name(), x);
        });

        return subCommandMap.values().toArray(new SubCommand[0]);
    }

    public SlashCommand getFullSlashCommand() {
        if (isRootCommand())
            return getSlashCommand();
        for (ExecutableSlashCommandDefinition executableSlashCommandDefinition : executableDefinitons) {
            if (executableSlashCommandDefinition.options().length > 0)
                return executableSlashCommandDefinition.applicationCommand();
        }

        return null;
    }

    public SlashCommand getSlashCommand() {
        return slashCommand;
    }

    public ExecutableSlashCommandDefinition[] getExecutableDefinitons() {
        return executableDefinitons.toArray(new ExecutableSlashCommandDefinition[0]);
    }

    public boolean isRootCommand() {
        return isRootCommand;
    }
}
