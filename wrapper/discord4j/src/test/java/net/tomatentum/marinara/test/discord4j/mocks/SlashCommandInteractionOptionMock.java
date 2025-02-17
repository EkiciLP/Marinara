package net.tomatentum.marinara.test.discord4j.mocks;

import java.util.Optional;

import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.core.object.command.ApplicationCommandOption.Type;

public class SlashCommandInteractionOptionMock extends ApplicationCommandInteractionOption {

    public SlashCommandInteractionOptionMock() {
        super(null, null, null, null);
    }

    @Override
    public String getName() {
        return "foo";
    }

    @Override
    public Optional<ApplicationCommandInteractionOptionValue> getValue() {
        return Optional.of(new ApplicationCommandInteractionOptionValue(null, null, Type.STRING.getValue(), "test", null));
    }
    
}
