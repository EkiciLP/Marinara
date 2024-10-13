package net.tomatentum.marinara.wrapper;

public interface LibraryConverter {
    public Object toAttachment(Object context, String option);
    public Object toChannel(Object context, String option);
    public Object toMentionable(Object context, String option);
    public Object toRole(Object context, String option);
    public Object toUser(Object context, String option);
}
