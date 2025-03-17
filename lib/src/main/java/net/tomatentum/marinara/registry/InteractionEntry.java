package net.tomatentum.marinara.registry;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;

import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.methods.InteractionMethod;
import net.tomatentum.marinara.util.LoggerUtil;

public class InteractionEntry {
    private InteractionIdentifier identifier;
    private Set<InteractionMethod> methods;

    private Logger logger = LoggerUtil.getLogger(getClass());

    public InteractionEntry(InteractionIdentifier identifier) {
        this.identifier = identifier;
        this.methods = new HashSet<>();
    }

    public InteractionEntry addMethod(InteractionMethod method) {
        if (!method.identifier().equals(this.identifier))
            throw new IllegalArgumentException("Method's identifier did not match the entry's identifier");

        this.methods.add(method);
        InteractionIdentifier.tryAddDescriptions(identifier, method.identifier());
        return this;
    }

    public void runAll(Object context) {
        this.methods.stream().forEach(x -> {
            logger.debug("Running Method {} from {} with context {}", x.toString(), this.toString(), context.toString());
            x.run(context);
        });
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof InteractionEntry))
            return false;
        InteractionEntry other = (InteractionEntry) obj;
        return other.identifier().equals(identifier());
    }

    @Override
    public String toString() {
        return "InteractionEntry(%s)".formatted(identifier().toString());
    }

    public InteractionType type() {
        return identifier().type();
    }

    public InteractionIdentifier identifier() {
        return identifier;
    }

    public Set<InteractionMethod> methods() {
        return methods;
    }

}
