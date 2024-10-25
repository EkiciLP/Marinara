package net.tomatentum.marinare.wrapper.javacord;

import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.commands.ApplicationCommandDefinition;
import net.tomatentum.marinara.interaction.commands.ExecutableCommandDefinition;
import net.tomatentum.marinara.interaction.commands.option.OptionType;
import net.tomatentum.marinara.wrapper.LibraryWrapper;

public class JavacordWrapper extends LibraryWrapper {

    @Override
    public void registerApplicationCommand(ApplicationCommandDefinition def) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registerApplicationCommand'");
    }

    @Override
    public InteractionType getInteractionType(Class<?> clazz) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInteractionType'");
    }

    @Override
    public Object convertCommandOption(Object context, OptionType type, String optionName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertCommandOption'");
    }

    @Override
    public ExecutableCommandDefinition getCommandDefinition(Object context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCommandDefinition'");
    }
    
}
