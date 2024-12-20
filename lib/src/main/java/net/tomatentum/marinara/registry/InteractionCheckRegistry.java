package net.tomatentum.marinara.registry;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;

import io.leangen.geantyref.GenericTypeReflector;
import net.tomatentum.marinara.checks.InteractionCheck;
import net.tomatentum.marinara.util.LoggerUtil;

public class InteractionCheckRegistry {
    
    private List<InteractionCheck<?>> checks;

    private Logger logger = LoggerUtil.getLogger(getClass());

    public InteractionCheckRegistry() {
        this.checks = new ArrayList<>();
    }

    public void addCheck(InteractionCheck<?> check) {
        checks.add(check);
        logger.info("Registered Check {}", check.getClass().getName());
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
