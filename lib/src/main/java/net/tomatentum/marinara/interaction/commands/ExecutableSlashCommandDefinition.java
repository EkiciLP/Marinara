package net.tomatentum.marinara.interaction.commands;

import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOption;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommandGroup;

public record ExecutableSlashCommandDefinition(
    SlashCommand applicationCommand,
    SubCommand subCommand,
    SubCommandGroup subCommandGroup, 
    SlashCommandOption[] options) {

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof ExecutableSlashCommandDefinition))
            return false;
        ExecutableSlashCommandDefinition other = (ExecutableSlashCommandDefinition) o;
        return other.applicationCommand.name().equals(this.applicationCommand.name()) && 
            other.subCommandGroup.name().equals(this.subCommandGroup.name()) &&
            other.subCommand.name().equals(this.subCommand.name());
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
        private SubCommandGroup subCommandGroup;

        public ExecutableSlashCommandDefinition build() {
            if (applicationCommand == null)
                throw new IllegalArgumentException("applicationCommandName cant be null");

            return new ExecutableSlashCommandDefinition(applicationCommand, subCommand, subCommandGroup, subCommand != null ? subCommand.options() : applicationCommand.options());
        }

        public void setApplicationCommand(SlashCommand applicationCommand) {
            this.applicationCommand = applicationCommand;
        }

        public void setSubCommand(SubCommand subCommand) {
            this.subCommand = subCommand;
        }

        public void setSubCommandGroup(SubCommandGroup subCommandGroup) {
            this.subCommandGroup = subCommandGroup;
        }

        public SlashCommand getApplicationCommand() {
            return applicationCommand;
        }

        public SubCommand getSubCommand() {
            return subCommand;
        }

        public SubCommandGroup getSubCommandGroup() {
            return subCommandGroup;
        }

    }
}
