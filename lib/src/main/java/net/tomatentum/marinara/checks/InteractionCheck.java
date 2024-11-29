package net.tomatentum.marinara.checks;

import java.lang.annotation.Annotation;

public interface InteractionCheck<A extends Annotation> {

    public boolean preExec(Object context, A annotation);
    public boolean postExec(Object context, A annotation);
    
}
