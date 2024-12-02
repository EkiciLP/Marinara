package net.tomatentum.marinara.wrapper.javacord.checks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Optional;

import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.InteractionBase;

import net.tomatentum.marinara.checks.InteractionCheck;

public class PermissionCheck implements InteractionCheck<PermissionCheck.HasPermission> {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public static @interface HasPermission {
        public PermissionType[] value();
    }

    @Override
    public boolean preExec(Object context, HasPermission annotation) {
        throw new UnsupportedOperationException("Unimplemented method 'preExec'");
    }

    public boolean preExec(InteractionBase context, HasPermission annotation) {
        Optional<Server> server = context.getServer();
        if (!server.isPresent())
            return false;

       return server.get().hasPermissions(context.getUser(), annotation.value());
    }

    @Override
    public void postExec(Object context, HasPermission annotation) {
        
    }
    
}
