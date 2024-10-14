package net.tomatentum.marinara.interaction.commands;

import org.apache.logging.log4j.core.tools.picocli.CommandLine.Command;

import net.tomatentum.marinara.interaction.commands.annotation.CommandOption;

public record ExecutableCommandDefinition(
    String applicationCommand,
    String applicationCommandDescription, 
    String[] subCommandGroups, 
    String subCommand, 
    String subCommandDescription,
    CommandOption[] options) {

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof ExecutableCommandDefinition))
            return false;
        ExecutableCommandDefinition other = (ExecutableCommandDefinition) o;
        return other.applicationCommand.equals(this.applicationCommand) && 
            other.subCommandGroups.equals(this.subCommandGroups) &&
            other.subCommand.equals(this.subCommand);
    }

    public static class Builder {
        private String applicationCommandName;
        private String applicationCommandDescription;
        private String[] subCommandGroupNames;
        private String subCommandName;
        private String subCommandDescription;
        public CommandOption[] options;

        public Builder() {
            this.subCommandGroupNames = new String[0];
        }

        public ExecutableCommandDefinition build() {
            if (applicationCommandName == null)
                throw new IllegalArgumentException("applicationCommandName cant be null");

            return new ExecutableCommandDefinition(applicationCommandName, applicationCommandDescription, subCommandGroupNames, subCommandName, subCommandDescription, options);
        }

        public void setApplicationCommandName(String applicationCommandName) {
            this.applicationCommandName = applicationCommandName;
        }

        public void setApplicationCommandDescription(String applicationCommandDescription) {
            this.applicationCommandDescription = applicationCommandDescription;
        }

        public void setSubCommandGroupNames(String[] subCommandGroupNames) {
            this.subCommandGroupNames = subCommandGroupNames;
        }

        public void setSubCommandName(String subCommandName) {
            this.subCommandName = subCommandName;
        }

        public void setSubCommandDescription(String subCommandDescription) {
            this.subCommandDescription = subCommandDescription;
        }

        public void setOptions(CommandOption[] options) {
            this.options = options;
        }

        public String getApplicationCommandName() {
            return applicationCommandName;
        }

        public String getApplicationCommandDescription() {
            return applicationCommandDescription;
        }

        public String[] getSubCommandGroupNames() {
            return subCommandGroupNames;
        }

        public String getSubCommandName() {
            return subCommandName;
        }

        public String getSubCommandDescription() {
            return subCommandDescription;
        }

        public CommandOption[] getOptions() {
            return options;
        }
    }
}
