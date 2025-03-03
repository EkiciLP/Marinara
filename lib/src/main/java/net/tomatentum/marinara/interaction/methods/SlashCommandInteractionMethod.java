package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.Method;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.ident.SlashCommandIdentifier;
import net.tomatentum.marinara.parser.AnnotationParser;
import net.tomatentum.marinara.parser.SlashCommandParser;

public class SlashCommandInteractionMethod extends InteractionMethod {

    private SlashCommandIdentifier interactionIdentifier;

    SlashCommandInteractionMethod(Method method, InteractionHandler handler, Marinara marinara) {
        super(method, handler, marinara);
    }

    @Override
    public AnnotationParser[] parsers() {
        return new AnnotationParser[] { 
            new SlashCommandParser(method, false, (x) -> { this.interactionIdentifier = x; } ) 
        };
    }

    @Override
    public Object getParameter(Object context, int index) {
        return marinara.getWrapper().getContextObjectProvider().convertCommandOption(context, interactionIdentifier.options()[index].name());
    }

    @Override
    public InteractionIdentifier identifier() {
        return interactionIdentifier;
    }

}
