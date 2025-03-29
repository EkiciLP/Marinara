package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.Method;
import java.util.List;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.ident.SlashCommandIdentifier;
import net.tomatentum.marinara.parser.AnnotationParser;
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
        public ReflectedMethod produce(Marinara marinara, Method method, Object containingObject) {
            if (!(method.isAnnotationPresent(SlashCommand.class) ||
                method.isAnnotationPresent(SubCommand.class)) ||
                !(containingObject instanceof InteractionHandler)
                )
                return null;

            return new SlashCommandInteractionMethod(method, (InteractionHandler) containingObject, marinara);
        }

        @Override
        public void addParser(ReflectedMethod method, List<AnnotationParser> parser) {
            super.addParser(method, parser);

            SlashCommandInteractionMethod imethod = (SlashCommandInteractionMethod) method;
            parser.add(
                new SlashCommandParser(method.method(), false, x -> imethod.interactionIdentifier = x)
            );
        }

    }

}
