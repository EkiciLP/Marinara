package net.tomatentum.marinara.checks;

import java.util.Optional;
import java.util.Set;

import net.tomatentum.cutin.MethodParser;
import net.tomatentum.cutin.ReflectedMethodFactory.ParserResults;
import net.tomatentum.cutin.container.MethodContainer;
import net.tomatentum.cutin.method.BestCandidateMethod;
import net.tomatentum.marinara.checks.CheckMethodIdentifier.CheckMethodType;
import net.tomatentum.marinara.parser.InteractionCheckClassParser;

public class InteractionCheckMethod extends BestCandidateMethod<CheckMethodIdentifier, CheckExecutionContext> {

    private CheckMethodIdentifier identifier;

    public InteractionCheckMethod(
            String methodName, 
            Object containingObject,
            CheckMethodIdentifier identifier
        ) {
        super(methodName, containingObject);
        this.identifier = identifier;
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

        @Override
        public void addParser(Set<MethodParser> parsers) {
            parsers.add(
                new InteractionCheckClassParser()
            );
        }

        @Override
        protected Optional<BestCandidateMethod<CheckMethodIdentifier, CheckExecutionContext>> bcProduce(
                    String methodName,
                    Object containingObject,
                    ParserResults parserResults
                ) {

            CheckMethodIdentifier identifier = new CheckMethodIdentifier(parserResults.get(InteractionCheckClassParser.class), type);
            if (identifier.annotationType() == null)
                return null;
            return Optional.of(new InteractionCheckMethod(methodName, containingObject, identifier));
        }

    }
    
}
