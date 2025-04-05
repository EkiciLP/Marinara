package net.tomatentum.marinara.registry;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.slf4j.Logger;

import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.methods.InteractionMethod;
import net.tomatentum.marinara.util.LoggerUtil;

public class InteractionEntry {

    public static InteractionEntry findEntry(Set<InteractionEntry> entries, InteractionIdentifier identifier) {
        Optional<InteractionEntry> oentry = entries.stream()
            .filter(i -> i.identifier().equals(identifier))
            .findFirst();

        InteractionEntry entry = oentry.orElse(new InteractionEntry(identifier));
        if (oentry.isEmpty()) entries.add(entry);
        return entry;
    }

    private InteractionIdentifier identifier;
    private Set<InteractionMethod> methods;

    private Logger logger = LoggerUtil.getLogger(getClass());

    public InteractionEntry(InteractionIdentifier identifier) {
        this.identifier = identifier;
        this.methods = new HashSet<>();
    }

    public InteractionEntry addMethod(InteractionMethod method) {
        InteractionIdentifier midentifier = method.identifier();

        if (!this.identifier().equals(midentifier))
            throw new IllegalArgumentException("Method's identifier did not equal the entry's identifier");

        this.methods.add(method);
        InteractionIdentifier.tryAddDescriptions(midentifier, midentifier);
        logger.debug("Added method {} to entry {}", method.method().getName(), this.identifier);
        return this;
    }

    public Object[] runAll(Object context) {
        return this.methods.stream()
            .map(x -> {
                logger.debug("Running Method {} from {} with context {}", x, this, context);
                return x.run(context);
            })
            .flatMap(o -> o instanceof Object[] oArray ? Arrays.stream(oArray) : Stream.<Object>of(o))
            .toArray();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof InteractionEntry))
            return false;
        InteractionEntry other = (InteractionEntry) obj;
        return other.identifier().equals(identifier());
    }

    @Override
    public int hashCode() {
        return this.identifier().hashCode();
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
