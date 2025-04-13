package net.tomatentum.marinara.checks;

import java.util.List;
import java.util.Optional;

import net.tomatentum.cutin.MethodParser;
import net.tomatentum.cutin.container.MethodContainer;
import net.tomatentum.cutin.method.BestCandidateMethod;
import net.tomatentum.cutin.method.ReflectedMethod;
import net.tomatentum.marinara.checks.CheckMethodIdentifier.CheckMethodType;
import net.tomatentum.marinara.parser.InteractionCheckClassParser;

public class InteractionCheckMethod extends BestCandidateMethod<CheckMethodIdentifier, CheckExecutionContext> {

    private CheckMethodIdentifier identifier;

    public InteractionCheckMethod(String methodName, Object containingObject) {
        super(methodName, containingObject);
    }

    @Override
    public Object getParameter(CheckExecutionContext context, int index) {
        switch (index) {
            case 0:
                return context.originalContext();
            case 1:
                return context.annotation();
            default:
                return null;
        }
    }

    @Override
    public CheckMethodIdentifier identifier() {
        return this.identifier;
    }

    public static class InteractionCheckMethodFactory extends BestCandidateMethod.Factory<CheckMethodIdentifier, CheckExecutionContext> {

        private CheckMethodType type;

        public InteractionCheckMethodFactory(MethodContainer<CheckMethodIdentifier, CheckExecutionContext> methodContainer, CheckMethodType type) {
            super(methodContainer, type.methodName());
            this.type = type;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void addParser(ReflectedMethod<CheckMethodIdentifier, CheckExecutionContext> method, List<MethodParser> parsers) {
            parsers.add(
                new InteractionCheckClassParser((Class<InteractionCheck<?>>) method.containingObject().getClass(),
                    a -> ((InteractionCheckMethod) method).identifier = new CheckMethodIdentifier(a, type))
            );
        }

        @Override
        protected Optional<BestCandidateMethod<CheckMethodIdentifier, CheckExecutionContext>> bcProduce(String methodName,
                Object containingObject) {
            if (!(containingObject instanceof InteractionCheck))
                return Optional.empty();
            return Optional.of(new InteractionCheckMethod(methodName, containingObject));
        }

    }
    
}
