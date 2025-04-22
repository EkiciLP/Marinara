package net.tomatentum.marinara.structure.data;

import net.tomatentum.marinara.interaction.annotation.Button;

public record ButtonStructureData(
    String customId, 
    String label,
    ButtonStyle style,
    String url,
    boolean disabled,
    String emoji
    ) {

    public ButtonStructureData(Button button) {
        this(button.value(), button.label(), button.style(), button.url(), button.disabled(), button.emoji());
    }

    public enum ButtonStyle {
        PRIMARY,
        SECONDARY,
        SUCCESS,
        DANGER,
        LINK;
    }
    
}
