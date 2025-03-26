package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.checks.AppliedCheck;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.annotation.AutoComplete;
import net.tomatentum.marinara.interaction.annotation.Button;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;
import net.tomatentum.marinara.interaction.components.methods.ButtonInteractionMethod;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.parser.AnnotationParser;
import net.tomatentum.marinara.parser.InteractionCheckParser;
import net.tomatentum.marinara.util.ReflectedMethod;

public abstract class InteractionMethod extends ReflectedMethod {

    public static InteractionMethod create(Method method, InteractionHandler handler, Marinara marinara) {
        if (method.isAnnotationPresent(AutoComplete.class))
            return new AutoCompleteInteractionMethod(method, handler, marinara);
        if (method.isAnnotationPresent(SlashCommand.class) || method.isAnnotationPresent(SubCommand.class))
            return new SlashCommandInteractionMethod(method, handler, marinara);
        if (method.isAnnotationPresent(Button.class))
            return new ButtonInteractionMethod(method, handler, marinara);
        return null;
    }

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
    public AnnotationParser[] provideParsers() {
        return new AnnotationParser[] { 
            new InteractionCheckParser(method(), appliedChecks::add, marinara.getCheckRegistry())
        };
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

}
