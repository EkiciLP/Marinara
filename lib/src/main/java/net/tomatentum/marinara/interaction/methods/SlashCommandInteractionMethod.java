package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import net.tomatentum.cutin.MethodParser;
import net.tomatentum.cutin.ReflectedMethodFactory.ParserResults;
import net.tomatentum.cutin.container.MethodContainer;
import net.tomatentum.cutin.method.ReflectedMethod;
import net.tomatentum.marinara.checks.AppliedCheck;
import net.tomatentum.marinara.checks.CheckExecutionContext;
import net.tomatentum.marinara.checks.CheckMethodIdentifier;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.ident.SlashCommandIdentifier;
import net.tomatentum.marinara.parser.InteractionCheckParser;
import net.tomatentum.marinara.parser.SlashCommandParser;
import net.tomatentum.marinara.wrapper.ContextObjectProvider;

public class SlashCommandInteractionMethod extends InteractionMethod {

    private SlashCommandIdentifier identifier;
    private ContextObjectProvider cop;

    private SlashCommandInteractionMethod(
            Method method, 
            InteractionHandler handler,
            List<AppliedCheck> appliedChecks,
            SlashCommandIdentifier identifier,
            ContextObjectProvider cop
        ) {
        super(method, handler, appliedChecks);
        this.identifier = identifier;
        this.cop = cop;
    }

    @Override
    public Object getParameter(Object context, int index) {
        Object superResult = super.getParameter(context, index);
        if (superResult == null)
            return this.cop.convertCommandOption(context, identifier.options()[index-1].name());
        else
            return superResult;
    }

    @Override
    public InteractionIdentifier identifier() {
        return identifier;
    }

    public static class Factory extends InteractionMethod.Factory {

        private ContextObjectProvider cop;

        public Factory(MethodContainer<CheckMethodIdentifier, CheckExecutionContext> checkContainer, ContextObjectProvider cop) {
            super(checkContainer);
            this.cop = cop;
        }

        @Override
        public Optional<ReflectedMethod<InteractionIdentifier, Object>> produce(Method method, Object containingObject, ParserResults parserResults) {
            if (!(containingObject instanceof InteractionHandler)) return Optional.empty();
            SlashCommandIdentifier ident = parserResults.get(SlashCommandParser.class);
            if (ident == null) return Optional.empty();
            return Optional.of(new SlashCommandInteractionMethod(
                method, 
                (InteractionHandler) containingObject,
                parserResults.get(InteractionCheckParser.class),
                ident,
                cop
            ));
        }

        @Override
        public void addParser(Set<MethodParser> parser) {
            super.addParser(parser);

            parser.add(
                new SlashCommandParser()
            );
        }

    }

}
