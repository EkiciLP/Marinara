package net.tomatentum.marinara.interaction.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.Logger;

import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOption;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOption.PlaceHolderEnum;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOptionChoice;
import net.tomatentum.marinara.interaction.commands.choice.EnumChoices;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.ident.RootCommandIdentifier;
import net.tomatentum.marinara.interaction.ident.SlashCommandIdentifier;
import net.tomatentum.marinara.util.LoggerUtil;

public class SlashCommandDefinition {

    public static SlashCommandOptionChoice[] getActualChoices(SlashCommandOption option) {
        SlashCommandOptionChoice[] choices = option.choices();
        if (choices.length <= 0 && !option.choiceEnum().equals(PlaceHolderEnum.class))
            choices = EnumChoices.of(option.choiceEnum()).choices();
        return choices;
    }

    private Set<InteractionIdentifier> entries;
    private RootCommandIdentifier rootIdentifier;
    private boolean isRootCommand;

    private Logger logger = LoggerUtil.getLogger(getClass());

    public SlashCommandDefinition(RootCommandIdentifier rootIdentifier) {
        this.entries = new HashSet<>();
        this.rootIdentifier = rootIdentifier;
        this.isRootCommand = false;
    }

    public SlashCommandDefinition addIdentifier(InteractionIdentifier identifier) {
        RootCommandIdentifier rootIdentifier = (RootCommandIdentifier) identifier.rootNode();

        if (!this.rootIdentifier.equals(rootIdentifier))
            throw new IllegalArgumentException("Root Node did not match.");

        if (this.rootIdentifier.description() == null)
            this.rootIdentifier = rootIdentifier;

        if (!isRootCommand)
            this.isRootCommand = identifier.parent() == null ? true : false;

        if ((isRootCommand && identifier.parent() != null) || (!isRootCommand && identifier.parent() == null)) {
            throw new IllegalArgumentException(identifier.toString() + ": cannot have subcommands and rootcommand definitions together");
        }
        
        entries.add(identifier);
        this.logger.debug("Added identifer {} to command {}", identifier, rootIdentifier);
        return this;
    }

    public SlashCommandIdentifier[] getSubCommandGroups() {
        if (isRootCommand)
            return null;
        
        List<InteractionIdentifier> subCommandGroups = entries().stream()
            .filter(x -> x.parent().parent() != null)
            .map(x -> x.parent())
            .toList();

        return InteractionIdentifier.distinct(subCommandGroups).toArray(SlashCommandIdentifier[]::new);
    }

    public SlashCommandIdentifier[] getSubCommands() {
        if (isRootCommand)
            return null;
        return InteractionIdentifier.distinct(entries.stream().toList()).toArray(SlashCommandIdentifier[]::new);
    }

    public SlashCommandIdentifier[] getSubCommands(String groupName) {
        if (isRootCommand)
            return null;

        List<InteractionIdentifier> subCommands = entries().stream()
            .filter(x -> x.parent().parent() != null && x.parent().name().equals(groupName))
            .map(x -> x.parent().parent())
            .toList();

        return InteractionIdentifier.distinct(subCommands).toArray(SlashCommandIdentifier[]::new);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SlashCommandDefinition))
            return false;
        SlashCommandDefinition other = (SlashCommandDefinition) obj;
        return this.rootIdentifier().equals(other.rootIdentifier());
    }

    public Set<InteractionIdentifier> entries() {
        return this.entries;
    }

    public RootCommandIdentifier rootIdentifier() {
        return rootIdentifier;
    }

    public boolean isRootCommand() {
        return isRootCommand;
    }
}
