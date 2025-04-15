package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

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
        InteractionHandler handler,
        List<AppliedCheck> appliedChecks
        ) {
        super(method, handler);
        this.appliedChecks = appliedChecks;
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
        public void addParser(Set<MethodParser> parser) {
            parser.add(
                new InteractionCheckParser(this.checkContainer)
            );
        }

    }

}
