package net.tomatentum.marinara.interaction.commands;

import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOption;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;

public record ExecutableSlashCommandDefinition(
    SlashCommand applicationCommand,
    SubCommand subCommand,
    String[] subCommandGroups, 
    SlashCommandOption[] options) {

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof ExecutableSlashCommandDefinition))
            return false;
        ExecutableSlashCommandDefinition other = (ExecutableSlashCommandDefinition) o;
        return other.applicationCommand.name().equals(this.applicationCommand.name()) && 
            other.subCommandGroups.equals(this.subCommandGroups) &&
            other.subCommand.equals(this.subCommand);
    }

    @Override
    public final String toString() {
        return applicationCommand.name() + subCommand.name() != null ? "::" + subCommand.name() : "";
    }

    public boolean isRootCommand() {
        return subCommand == null;    
    }

    public static class Builder {
        private SlashCommand applicationCommand;
        private SubCommand subCommand;
        private String[] subCommandGroupNames;

        public Builder() {
            this.subCommandGroupNames = new String[0];
        }

        public ExecutableSlashCommandDefinition build() {
            if (applicationCommand == null)
                throw new IllegalArgumentException("applicationCommandName cant be null");

            return new ExecutableSlashCommandDefinition(applicationCommand, subCommand, subCommandGroupNames, subCommand != null ? subCommand.options() : applicationCommand.options());
        }

        public void setApplicationCommand(SlashCommand applicationCommand) {
            this.applicationCommand = applicationCommand;
        }

        public void setSubCommand(SubCommand subCommand) {
            this.subCommand = subCommand;
        }

        public void setSubCommandGroupNames(String[] subCommandGroupNames) {
            this.subCommandGroupNames = subCommandGroupNames;
        }

        public SlashCommand getApplicationCommand() {
            return applicationCommand;
        }

        public SubCommand getSubCommand() {
            return subCommand;
        }

        public String[] getSubCommandGroupNames() {
            return subCommandGroupNames;
        }

    }
}
