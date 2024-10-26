package net.tomatentum.marinara.interaction.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommandGroup;

public class SlashCommandDefinition {
    private List<ExecutableSlashCommandDefinition> executableDefinitons;
    private SlashCommand slashCommand;
    private boolean isRootCommand = false;

    public SlashCommandDefinition(SlashCommand applicationCommand) {
        this.executableDefinitons = new ArrayList<>();
        this.slashCommand = applicationCommand;
    }

    public SlashCommandDefinition addExecutableCommand(ExecutableSlashCommandDefinition def) {
        if (def.applicationCommand() != null) {
            if (slashCommand == null)
                this.slashCommand = def.applicationCommand();
            if (!this.slashCommand.equals(def.applicationCommand()))
                throw new IllegalArgumentException(def + ": has a non matching Application Command description. Please edit it to equal all other descriptions or remove it to use other definitions descriptions");
        }
        if (isRootCommand) {
            if (!def.isRootCommand())
                throw new IllegalArgumentException(def + ": cannot have subcommands and rootcommand definitions together");
            long subCommandAmount = executableDefinitons.stream()
                .filter((x) -> !x.isRootCommand())
                .count();
            if (subCommandAmount > 0)
                throw new IllegalArgumentException(def + ": cannot have subcommands and rootcommand definitions together");
        }
        
        executableDefinitons.add(def);
        return this;
    }

    public SlashCommand getSlashCommand() {
        return slashCommand;
    }

    public ExecutableSlashCommandDefinition[] getExecutableDefinitons() {
        return executableDefinitons.toArray(new ExecutableSlashCommandDefinition[0]);
    }

    public ExecutableSlashCommandDefinition[] getUniqueExecutableDefinitions() {
        HashSet<ExecutableSlashCommandDefinition> set = new HashSet<>();
        executableDefinitons.forEach(set::add);
        return set.toArray(new ExecutableSlashCommandDefinition[0]);
    }

    public SubCommandGroup[] getSubCommandGroups() {
        HashSet<SubCommandGroup> subCommandGroups = new HashSet<>();

        for (ExecutableSlashCommandDefinition execDef : getUniqueExecutableDefinitions()) {
            String currName = execDef.subCommandGroup().name();
            if (execDef.subCommandGroup() == null)
                continue;
            SubCommandGroup[] matching = (SubCommandGroup[]) subCommandGroups.stream().filter((x) -> x.name().equals(currName)).toArray();
            if (matching.length < 1)
                subCommandGroups.add(execDef.subCommandGroup());
            else if (matching[0].description().isBlank() && execDef.subCommandGroup().description().isBlank()) {
                subCommandGroups.removeIf((x) -> x.name().equals(currName));
                subCommandGroups.add(execDef.subCommandGroup());
            }
        }

        return subCommandGroups.toArray(new SubCommandGroup[0]);
    }

    public SubCommand[] getSubCommands(SubCommandGroup group) {
        if (group == null)
            return (SubCommand[]) Arrays.asList(getUniqueExecutableDefinitions()).stream().filter((x) -> x.subCommandGroup() == null && x.subCommand() != null).toArray();
        else 
            return (SubCommand[]) Arrays.asList(getUniqueExecutableDefinitions()).stream().filter((x) -> x.subCommandGroup().name().equals(group.name()) && x.subCommand() != null).toArray();
    }
}
