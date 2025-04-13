package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.tomatentum.cutin.MethodParser;
import net.tomatentum.cutin.ReflectedMethodFactory;
import net.tomatentum.cutin.container.MethodContainer;
import net.tomatentum.cutin.method.ReflectedMethod;
import net.tomatentum.marinara.checks.AppliedCheck;
import net.tomatentum.marinara.checks.CheckExecutionContext;
import net.tomatentum.marinara.checks.CheckMethodIdentifier;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.parser.InteractionCheckParser;

public abstract class InteractionMethod extends ReflectedMethod<InteractionIdentifier, Object> {

    protected List<AppliedCheck> appliedChecks;

    protected InteractionMethod(
        Method method, 
        InteractionHandler handler
        ) {
        super(method, handler);
        this.appliedChecks = new ArrayList<>();
    }

    @Override
    public Object getParameter(Object context, int index) {
        if (index == 0)
            return context;
        return null;
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

    public List<AppliedCheck> appliedChecks() {
        return this.appliedChecks;
    }

    public abstract static class Factory implements ReflectedMethodFactory.Factory<InteractionIdentifier, Object> {

        private MethodContainer<CheckMethodIdentifier, CheckExecutionContext> checkContainer;

        protected Factory(MethodContainer<CheckMethodIdentifier, CheckExecutionContext> checkContainer) {
            this.checkContainer = checkContainer;
        }

        @Override
        public void addParser(ReflectedMethod<InteractionIdentifier, Object> method, List<MethodParser> parser) {
            InteractionMethod imethod = (InteractionMethod) method;
            parser.add(
                new InteractionCheckParser(method.method(), imethod.appliedChecks::add, this.checkContainer)
            );
        }

    }

}
