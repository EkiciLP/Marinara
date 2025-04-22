package net.tomatentum.marinara.structure;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;

import net.tomatentum.cutin.MethodParser;
import net.tomatentum.cutin.ReflectedMethodFactory;
import net.tomatentum.cutin.ReflectedMethodFactory.ParserResults;
import net.tomatentum.cutin.method.ReflectedMethod;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.structure.parser.ComponentStructureParser;

public class ComponentStructureMethod extends ReflectedMethod<InteractionIdentifier, Void> {

    private InteractionIdentifier identifier;

    private ComponentStructureMethod(
            Method method, 
            Object containingObject,
            InteractionIdentifier identifier
        ) {
        super(method, containingObject);
        this.identifier = identifier;
    }

    @Override
    public Object getParameter(Void context, int index) {
        return null;
    }

    @Override
    public InteractionIdentifier identifier() {
        return this.identifier;
    }

    public static class Factory implements ReflectedMethodFactory.Factory<InteractionIdentifier, Void> {

        private Class<? extends Object> buttonClass;

        public Factory(Class<? extends Object> buttonClass) {
            this.buttonClass = buttonClass;
        }

        @Override
        public void addParser(Set<MethodParser> parser) {
            parser.add(new ComponentStructureParser(buttonClass));
        }

        @Override
        public Optional<ReflectedMethod<InteractionIdentifier, Void>> produce(
                Method method, 
                Object containingObject,
                ParserResults parserResults) {
            InteractionIdentifier identifier = parserResults.get(ComponentStructureParser.class);
            if (identifier == null)
                return Optional.empty();
            
            return Optional.of(new ComponentStructureMethod(method, containingObject, identifier));
        }

    }
    
}
