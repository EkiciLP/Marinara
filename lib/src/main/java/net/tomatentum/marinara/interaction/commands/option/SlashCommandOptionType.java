package net.tomatentum.marinara.interaction.commands.option;

public enum SlashCommandOptionType {
    SUB_COMMAND(1),
    SUB_COMMAND_GROUP(2),
    STRING(3),
    INTEGER(4),
    BOOLEAN(5),
    USER(6),
    CHANNEL(7),
    ROLE(8),
    MENTIONABLE(9),
    DOUBLE(10),
    ATTACHMENT(11),
    UNKNOWN(-1);

    private final int value;

    private SlashCommandOptionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
