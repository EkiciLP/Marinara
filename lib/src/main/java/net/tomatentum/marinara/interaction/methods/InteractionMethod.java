package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.checks.AppliedCheck;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.parser.AnnotationParser;
import net.tomatentum.marinara.parser.InteractionCheckParser;
import net.tomatentum.marinara.reflection.ReflectedMethod;
import net.tomatentum.marinara.reflection.ReflectedMethodFactory;

public abstract class InteractionMethod extends ReflectedMethod {

    protected Marinara marinara;
    protected List<AppliedCheck> appliedChecks;

    protected InteractionMethod(
        Method method, 
        InteractionHandler handler, 
        Marinara marinara
        ) {
        super(method, handler);
        this.marinara = marinara;
        this.appliedChecks = new ArrayList<>();
    }
    
    @Override
    public Object run(Object context) {
        Object result = null;
        if (this.appliedChecks.stream().filter(x -> !x.pre(context)).count() > 0)
            return null;

        result = super.run(context);

        this.appliedChecks.forEach(x -> x.post(context));

        return result;
    }

    public abstract InteractionIdentifier identifier();

    public Marinara marinara() {
        return this.marinara;
    }

    public List<AppliedCheck> appliedChecks() {
        return this.appliedChecks;
    }

    public static abstract class Factory implements ReflectedMethodFactory.Factory {

        @Override
        public void addParser(ReflectedMethod method, List<AnnotationParser> parser) {
            InteractionMethod imethod = (InteractionMethod) method;
            parser.add(
                new InteractionCheckParser(method.method(), imethod.appliedChecks::add, imethod.marinara().getCheckRegistry())
            );
        }

    }

}
