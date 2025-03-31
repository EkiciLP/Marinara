package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.ident.SlashCommandIdentifier;
import net.tomatentum.marinara.parser.AnnotationParser;
import net.tomatentum.marinara.parser.AutocompleteParser;
import net.tomatentum.marinara.parser.SlashCommandParser;
import net.tomatentum.marinara.reflection.ReflectedMethod;

public class SlashCommandInteractionMethod extends InteractionMethod {

    private SlashCommandIdentifier interactionIdentifier;

    private SlashCommandInteractionMethod(Method method, InteractionHandler handler, Marinara marinara) {
        super(method, handler, marinara);
    }

    @Override
    public Object getParameter(Object context, int index) {
        return marinara.getWrapper().getContextObjectProvider().convertCommandOption(context, interactionIdentifier.options()[index].name());
    }

    @Override
    public InteractionIdentifier identifier() {
        return interactionIdentifier;
    }

    public static class Factory extends InteractionMethod.Factory {

        @Override
        public Optional<ReflectedMethod> produce(Marinara marinara, Method method, Object containingObject) {
            ReflectedMethod rMethod = null;
            if ((containingObject instanceof InteractionHandler) && 
                (method.isAnnotationPresent(SlashCommand.class) ||
                method.isAnnotationPresent(SubCommand.class)))
                rMethod = new SlashCommandInteractionMethod(method, (InteractionHandler) containingObject, marinara);
            return Optional.ofNullable(rMethod);
        }

        @Override
        public void addParser(ReflectedMethod method, List<AnnotationParser> parser) {
            super.addParser(method, parser);

            SlashCommandInteractionMethod imethod = (SlashCommandInteractionMethod) method;
            parser.add(
                new SlashCommandParser(method.method(), x -> imethod.interactionIdentifier = x)
            );
            parser.add(
                new AutocompleteParser(method.method(), x -> imethod.interactionIdentifier.autocompleteRef(x))
            );
        }

    }

}
