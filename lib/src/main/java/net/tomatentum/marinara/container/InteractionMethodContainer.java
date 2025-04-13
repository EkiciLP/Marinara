package net.tomatentum.marinara.container;

import net.tomatentum.cutin.ReflectedMethodFactory;
import net.tomatentum.cutin.ReflectedMethodFactoryImpl;
import net.tomatentum.cutin.container.MethodContainer;
import net.tomatentum.cutin.container.MultiMethodContainer;
import net.tomatentum.cutin.method.ReflectedMethod;
import net.tomatentum.marinara.checks.CheckExecutionContext;
import net.tomatentum.marinara.checks.CheckMethodIdentifier;
import net.tomatentum.marinara.interaction.components.methods.ButtonInteractionMethod;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.wrapper.ContextObjectProvider;
import net.tomatentum.marinara.interaction.methods.AutoCompleteInteractionMethod;
import net.tomatentum.marinara.interaction.methods.SlashCommandInteractionMethod;

public class InteractionMethodContainer extends MultiMethodContainer<InteractionIdentifier, Object> {


    private static ReflectedMethodFactory<InteractionIdentifier, Object> createFactory(MethodContainer<CheckMethodIdentifier, CheckExecutionContext> checkRegistry, ContextObjectProvider cop) {
        return new ReflectedMethodFactoryImpl<InteractionIdentifier, Object>()
            .addFactory(new AutoCompleteInteractionMethod.Factory(checkRegistry, cop))
            .addFactory(new SlashCommandInteractionMethod.Factory(checkRegistry, cop))
            .addFactory(new ButtonInteractionMethod.Factory(checkRegistry, cop));
    }

    public InteractionMethodContainer(MethodContainer<CheckMethodIdentifier, CheckExecutionContext> checkRegistry, ContextObjectProvider cop) {
        super(createFactory(checkRegistry, cop));
    }

    @Override
    public MethodContainer<InteractionIdentifier, Object> addMethod(ReflectedMethod<InteractionIdentifier, Object> method) {
        super.identifiers().stream()
            .filter(method.identifier()::equals)
            .forEach(i -> InteractionIdentifier.tryAddDescriptions(i, method.identifier()));
        return super.addMethod(method);
    }

}
