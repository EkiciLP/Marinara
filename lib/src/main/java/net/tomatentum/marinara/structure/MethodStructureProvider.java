package net.tomatentum.marinara.structure;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.tomatentum.cutin.ReflectedMethodFactoryImpl;
import net.tomatentum.cutin.container.LoneMethodContainer;
import net.tomatentum.cutin.method.ReflectedMethod;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;

public class MethodStructureProvider<B extends Object> 
    extends LoneMethodContainer<InteractionIdentifier, Void>
    implements StructureProvider<B> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Class<? extends B> buttonClass;

    public MethodStructureProvider(Class<? extends B> buttonClass) {
        super(new ReflectedMethodFactoryImpl<>());
        super.factory().addFactory(new ComponentStructureMethod.Factory(buttonClass));
        this.buttonClass = buttonClass;
    }

    @Override
    public B button(InteractionIdentifier identifier) {
        Optional<ReflectedMethod<InteractionIdentifier, Void>> method = super.findFirstFor(identifier);
        if (method.isEmpty()) return null;
        try {
            return buttonClass.cast(method.get().run(null));
        }catch (ClassCastException ex) {
            logger.warn("Structure Method {} return type did not match expected {}", method.get(), buttonClass);
            return null;
        }
    }
    
}
