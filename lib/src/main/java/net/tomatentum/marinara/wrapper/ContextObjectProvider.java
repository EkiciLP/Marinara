package net.tomatentum.marinara.wrapper;

public interface ContextObjectProvider {

    public Object convertCommandOption(Object context, String optionName);

    public Object getComponentContextObject(Object context, Class<?> type);
    public Object getInteractionContextObject(Object context, Class<?> type);

    public Object getAutocompleteFocusedOption(Object context);
}
