package net.tomatentum.marinara.interaction.commands;

import net.tomatentum.marinara.interaction.commands.annotation.ApplicationCommand;
import net.tomatentum.marinara.interaction.commands.annotation.CommandOption;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;

public record ExecutableSlashCommandDefinition(
    ApplicationCommand applicationCommand,
    SubCommand subCommand,
    String[] subCommandGroups, 
    CommandOption[] options) {

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
        private ApplicationCommand applicationCommand;
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

        public void setApplicationCommand(ApplicationCommand applicationCommand) {
            this.applicationCommand = applicationCommand;
        }

        public void setSubCommand(SubCommand subCommand) {
            this.subCommand = subCommand;
        }

        public void setSubCommandGroupNames(String[] subCommandGroupNames) {
            this.subCommandGroupNames = subCommandGroupNames;
        }

        public ApplicationCommand getApplicationCommand() {
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