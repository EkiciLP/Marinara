package net.tomatentum.marinara.wrapper;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.tomatentum.marinara.interaction.commands.SlashCommandDefinition;
import net.tomatentum.marinara.util.ObjectAggregator;

public class CommandRegisterer<A extends Object> {

    public static <A extends Object> CommandRegisterer<A> of(Strategy<A> strategy, CommandConverter<A, ?, ?> converter) {
        return new CommandRegisterer<A>(strategy, converter);
    }

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Strategy<A> strategy;
    private CommandConverter<A, ?, ?> converter;

    CommandRegisterer(Strategy<A> strategy, CommandConverter<A, ?, ?> converter) {
        this.strategy = strategy;
        this.converter = converter;
    }

    public void register(SlashCommandDefinition[] slashDefs) {
        Set<ServerCommandList<A>> serverCommands = new ObjectAggregator<SlashCommandDefinition, Long, ServerCommandList<A>>(
                def -> Arrays.stream(def.serverIds()).boxed().toList(),
                (l, o) -> {
                    logger.debug("Added {} for server ({}) registration.", o.rootIdentifier(), l.serverId());
                    l.add(converter.convert(o));
                },
                ServerCommandList::new)
            .aggregate(Arrays.asList(slashDefs)).stream()
            .collect(Collectors.toSet());

        Set<A> globalCommands = Arrays.stream(slashDefs)
            .filter(x -> x.serverIds().length <= 0)
            .peek(c -> logger.debug("Added {} for global registration.", c.rootIdentifier()))
            .map(converter::convert)
            .collect(Collectors.toSet());

        serverCommands.forEach(strategy::registerServer);
        strategy.registerGlobal(globalCommands);
        logger.info("Registered all SlashCommands");
    }
    
    public interface Strategy<A extends Object> {
        void registerServer(ServerCommandList<A> commands);
        void registerGlobal(Set<A> defs);
    }
}
