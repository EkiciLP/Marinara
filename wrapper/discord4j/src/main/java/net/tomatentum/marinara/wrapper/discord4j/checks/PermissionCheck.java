package net.tomatentum.marinara.wrapper.discord4j.checks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Optional;

import discord4j.core.event.domain.interaction.InteractionCreateEvent;
import discord4j.core.object.entity.Member;
import discord4j.rest.util.Permission;
import discord4j.rest.util.PermissionSet;
import net.tomatentum.marinara.checks.InteractionCheck;

public class PermissionCheck implements InteractionCheck<PermissionCheck.HasPermission> {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public static @interface HasPermission {
        public Permission[] value();
    }

    @Override
    public boolean preExec(Object context, HasPermission annotation) {
        throw new UnsupportedOperationException("Unimplemented method 'preExec'");
    }

    public boolean preExec(InteractionCreateEvent context, HasPermission annotation) {
        Optional<Member> member = context.getInteraction().getMember();
        if (member.isEmpty())
            return false;
        PermissionSet ownPerms = PermissionSet.of(annotation.value());
        PermissionSet permSet = member.get().getBasePermissions().block();
        return permSet.containsAll(ownPerms);
    }

    @Override
    public void postExec(Object context, HasPermission annotation) {
        
    }
    
}