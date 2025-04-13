package net.tomatentum.marinara.container;

import net.tomatentum.cutin.ReflectedMethodFactoryImpl;
import net.tomatentum.cutin.container.LoneMethodContainer;
import net.tomatentum.marinara.checks.CheckExecutionContext;
import net.tomatentum.marinara.checks.CheckMethodIdentifier;
import net.tomatentum.marinara.checks.CheckMethodIdentifier.CheckMethodType;
import net.tomatentum.marinara.checks.InteractionCheckMethod.InteractionCheckMethodFactory;

public class InteractionCheckContainer extends LoneMethodContainer<CheckMethodIdentifier, CheckExecutionContext> {
    
    public InteractionCheckContainer() {
        super(new ReflectedMethodFactoryImpl<>());
        super.factory()
            .addFactory(new InteractionCheckMethodFactory(this, CheckMethodType.PRE))
            .addFactory(new InteractionCheckMethodFactory(this, CheckMethodType.POST));
    }

}
