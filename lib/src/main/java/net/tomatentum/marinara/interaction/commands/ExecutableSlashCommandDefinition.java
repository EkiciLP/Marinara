package net.tomatentum.marinara.interaction.commands;

import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOption;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOptionChoice;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommandGroup;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOption.PlaceHolderEnum;

public record ExecutableSlashCommandDefinition(
    SlashCommand applicationCommand,
    SubCommand subCommand,
    SubCommandGroup subCommandGroup, 
    SlashCommandOption[] options) {

    public static SlashCommandOptionChoice[] getActualChoices(SlashCommandOption option) {
        SlashCommandOptionChoice[] choices = option.choices();
        if (choices.length <= 0 && !option.choiceEnum().equals(PlaceHolderEnum.class))
            choices = EnumChoices.of(option.choiceEnum()).choices();
        return choices;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof ExecutableSlashCommandDefinition))
            return false;
        ExecutableSlashCommandDefinition other = (ExecutableSlashCommandDefinition) o;
        boolean equals = false;

        if (this.applicationCommand() != null && other.applicationCommand() != null)
            equals = this.applicationCommand().name().equals(other.applicationCommand().name());

        if (this.subCommandGroup() != null && other.subCommandGroup() != null)
            equals = this.subCommandGroup().name().equals(other.subCommandGroup().name());
        
        if (this.subCommand() != null && other.subCommand() != null)
            equals = this.subCommand().name().equals(other.subCommand().name());

        return equals;
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
