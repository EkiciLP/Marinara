package net.tomatentum.marinara.registry;

import java.lang.annotation.Annotation;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.tomatentum.marinara.checks.InteractionCheck;

public class InteractionCheckRegistry {
    
    private List<InteractionCheck<?>> checks;

    public InteractionCheckRegistry() {
        this.checks = new ArrayList<>();
    }

    public void addCheck(InteractionCheck<?> check) {
        checks.add(check);
    }

    public Optional<InteractionCheck<?>> getCheckFromAnnotation(Class<? extends Annotation> annotation) {
        for (InteractionCheck<?> interactionCheck : checks) {
            TypeVariable<?> type = interactionCheck.getClass().getTypeParameters()[0];
            if (type.getClass().equals(annotation.getClass()))
                return Optional.of(interactionCheck);
        }
        return Optional.empty();
    }

}
