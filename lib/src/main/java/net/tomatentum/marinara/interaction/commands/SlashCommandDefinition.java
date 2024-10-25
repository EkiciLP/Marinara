package net.tomatentum.marinara.interaction.commands;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;

public class SlashCommandDefinition {
    private List<ExecutableSlashCommandDefinition> executableDefinitons;
    private SlashCommand applicationCommand;
    private int subCommandGroupCount = -1;
    private boolean isRootCommand = false;

    public SlashCommandDefinition(SlashCommand applicationCommand) {
        this.executableDefinitons = new ArrayList<>();
        this.applicationCommand = applicationCommand;
    }

    public SlashCommandDefinition addExecutableCommand(ExecutableSlashCommandDefinition def) {
        if (this.subCommandGroupCount == -1)
            this.subCommandGroupCount = def.subCommandGroups().length;
        if (def.subCommandGroups().length != subCommandGroupCount)
            throw new IllegalArgumentException(def + ": has a non matching amount of subcommand groups. All subcommands must have the same amount of subcommand groups!");
        if (def.applicationCommand() != null) {
            if (applicationCommand == null)
                this.applicationCommand = def.applicationCommand();
            if (!this.applicationCommand.equals(def.applicationCommand()))
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

    public SlashCommand getApplicationCommand() {
        return applicationCommand;
    }

    public ExecutableSlashCommandDefinition[] getExecutableDefinitons() {
        return executableDefinitons.toArray(new ExecutableSlashCommandDefinition[0]);
    }

    public ExecutableSlashCommandDefinition[] getUniqueExecutableDefinitions() {
        HashSet<ExecutableSlashCommandDefinition> set = new HashSet<>();
        executableDefinitons.forEach(set::add);
        return set.toArray(new ExecutableSlashCommandDefinition[0]);
    }
    public int getSubCommandGroupCount() {
        return subCommandGroupCount;
    }

}
