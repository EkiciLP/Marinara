package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import net.tomatentum.cutin.MethodParser;
import net.tomatentum.cutin.container.MethodContainer;
import net.tomatentum.cutin.method.ReflectedMethod;
import net.tomatentum.marinara.checks.CheckExecutionContext;
import net.tomatentum.marinara.checks.CheckMethodIdentifier;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.ident.SlashCommandIdentifier;
import net.tomatentum.marinara.parser.SlashCommandParser;
import net.tomatentum.marinara.wrapper.ContextObjectProvider;

public class SlashCommandInteractionMethod extends InteractionMethod {

    private SlashCommandIdentifier interactionIdentifier;
    private ContextObjectProvider cop;

    private SlashCommandInteractionMethod(Method method, InteractionHandler handler, ContextObjectProvider cop) {
        super(method, handler);
        this.cop = cop;
    }

    @Override
    public Object getParameter(Object context, int index) {
        Object superResult = super.getParameter(context, index);
        if (superResult == null)
            return this.cop.convertCommandOption(context, interactionIdentifier.options()[index-1].name());
        else
            return superResult;
    }

    @Override
    public InteractionIdentifier identifier() {
        return interactionIdentifier;
    }

    public static class Factory extends InteractionMethod.Factory {

        private ContextObjectProvider cop;

        public Factory(MethodContainer<CheckMethodIdentifier, CheckExecutionContext> checkContainer, ContextObjectProvider cop) {
            super(checkContainer);
            this.cop = cop;
        }

        @Override
        public Optional<ReflectedMethod<InteractionIdentifier, Object>> produce(Method method, Object containingObject) {
            SlashCommandInteractionMethod rMethod = null;
            if ((containingObject instanceof InteractionHandler iHandler) && 
                (method.isAnnotationPresent(SlashCommand.class) ||
                method.isAnnotationPresent(SubCommand.class)))
                rMethod = new SlashCommandInteractionMethod(method, iHandler, cop);
            return Optional.ofNullable(rMethod);
        }

        @Override
        public void addParser(ReflectedMethod<InteractionIdentifier, Object> method, List<MethodParser> parser) {
            super.addParser(method, parser);

            parser.add(
                new SlashCommandParser(method.method(), x -> ((SlashCommandInteractionMethod) method).interactionIdentifier = x)
            );
        }

    }

}
