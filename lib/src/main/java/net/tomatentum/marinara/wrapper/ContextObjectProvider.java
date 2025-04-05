package net.tomatentum.marinara.wrapper;

import net.tomatentum.marinara.interaction.commands.option.AutocompleteOptionData;

public interface ContextObjectProvider {

    public Object convertCommandOption(Object context, String optionName);

    public Object getComponentContextObject(Object context, Class<?> type);
    public Object getInteractionContextObject(Object context, Class<?> type);

    public AutocompleteOptionData getAutocompleteFocusedOption(Object context);
}
