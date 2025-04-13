package net.tomatentum.marinara;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;

import net.tomatentum.cutin.MethodExecutor;
import net.tomatentum.cutin.ProcessorMethodExecutor;
import net.tomatentum.cutin.container.MethodContainer;
import net.tomatentum.marinara.checks.CheckExecutionContext;
import net.tomatentum.marinara.checks.CheckMethodIdentifier;
import net.tomatentum.marinara.container.InteractionCheckContainer;
import net.tomatentum.marinara.container.InteractionMethodContainer;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.commands.SlashCommandDefinition;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.ident.RootCommandIdentifier;
import net.tomatentum.marinara.interaction.processor.AutocompleteInteractionProcessor;
import net.tomatentum.marinara.interaction.processor.DirectInteractionProcessor;
import net.tomatentum.marinara.util.LoggerUtil;
import net.tomatentum.marinara.util.ObjectAggregator;
import net.tomatentum.marinara.wrapper.IdentifierProvider;
import net.tomatentum.marinara.wrapper.LibraryWrapper;

public class Marinara {

    private Logger logger = LoggerUtil.getLogger(getClass());
    
    public static Marinara load(LibraryWrapper wrapper) {
        return new Marinara(wrapper);
    }

    private LibraryWrapper wrapper;
    private MethodContainer<CheckMethodIdentifier, CheckExecutionContext> checkContainer;
    private MethodContainer<InteractionIdentifier, Object> interactionContainer;
    private MethodExecutor<Object> interactionExecutor;

    private Marinara(LibraryWrapper wrapper) {
        this.wrapper = wrapper;
        this.checkContainer = new InteractionCheckContainer();
        this.interactionContainer = new InteractionMethodContainer(getCheckContainer(), getWrapper().getContextObjectProvider());
        IdentifierProvider provider = wrapper.createIdentifierProvider();
        ProcessorMethodExecutor<InteractionIdentifier, Object> exec = new ProcessorMethodExecutor<>(getInteractionContainer());
        exec
            .addProcessor(new DirectInteractionProcessor(provider, InteractionType.COMMAND, InteractionType.BUTTON))
            .addProcessor(new AutocompleteInteractionProcessor(getWrapper(), provider));
        this.interactionExecutor = exec;
        wrapper.subscribeInteractions(this.interactionExecutor::handle);
        logger.info("Marinara loaded successfully!");
    }

    //TODO move to future interactionstructure module
    public void registerCommands() {
        List<InteractionIdentifier> slashIdentifiers = getInteractionContainer().identifiers().stream()
            .filter(i -> i.type().equals(InteractionType.COMMAND))
            .toList();

        SlashCommandDefinition[] defs = new ObjectAggregator<InteractionIdentifier, RootCommandIdentifier, SlashCommandDefinition>(
            i -> Arrays.asList((RootCommandIdentifier)i.rootNode()),
            SlashCommandDefinition::addIdentifier,
            SlashCommandDefinition::new)
            .aggregate(slashIdentifiers)
            .toArray(SlashCommandDefinition[]::new);

        wrapper.getRegisterer().register(defs);
    }

    public LibraryWrapper getWrapper() {
        return this.wrapper;
    }

    public MethodContainer<InteractionIdentifier, Object> getInteractionContainer() {
        return this.interactionContainer;
    }

    public MethodContainer<CheckMethodIdentifier, CheckExecutionContext> getCheckContainer() {
        return this.checkContainer;
    }

    public MethodExecutor<Object> getInteractionExecutor() {
        return interactionExecutor;
    }
    
}
