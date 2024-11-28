package net.tomatentum.marinara.checks;

import java.lang.annotation.Annotation;

public interface InteractionCheck<A extends Annotation> {

    public boolean preExec(A annotation);
    public boolean postExec(A annotation);
    
}
