package net.tomatentum.marinara.registry;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.leangen.geantyref.GenericTypeReflector;
import net.tomatentum.marinara.checks.InteractionCheck;

public class InteractionCheckRegistry {
    
    private List<InteractionCheck<?>> checks;

    public InteractionCheckRegistry() {
        this.checks = new ArrayList<>();
    }

    public void addCheck(InteractionCheck<?> check) {
        checks.add(check);
    }

    public Optional<InteractionCheck<?>> getCheckFromAnnotation(Type annotation) {
        for (InteractionCheck<?> interactionCheck : checks) {
            ParameterizedType type = (ParameterizedType) GenericTypeReflector.getExactSuperType(interactionCheck.getClass(), InteractionCheck.class);
            Type typeParam = type.getActualTypeArguments()[0];
            if (typeParam.equals(annotation))
                return Optional.of(interactionCheck);
        }
        return Optional.empty();
    }

}
