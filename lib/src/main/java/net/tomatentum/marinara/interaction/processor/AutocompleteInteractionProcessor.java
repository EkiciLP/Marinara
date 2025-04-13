package net.tomatentum.marinara.interaction.processor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import net.tomatentum.cutin.container.MethodContainer;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.ident.SlashCommandIdentifier;
import net.tomatentum.marinara.wrapper.IdentifierProvider;
import net.tomatentum.marinara.wrapper.LibraryWrapper;

public class AutocompleteInteractionProcessor extends InteractionMethodProcessor {

    private LibraryWrapper wrapper;

    public AutocompleteInteractionProcessor(LibraryWrapper wrapper, IdentifierProvider provider) {
        super(provider, Set.of(InteractionType.AUTOCOMPLETE));
        this.wrapper = wrapper;
    }

    @Override
    public void processInteraction(Object context, MethodContainer<InteractionIdentifier, Object> container, InteractionIdentifier identifier) {
        Optional<InteractionIdentifier> oIdent = container.identifiers().stream()
            .filter(i -> convertToCommandIdentifier(identifier).equals(i))
            .findFirst();
        if (oIdent.isPresent() && oIdent.get() instanceof SlashCommandIdentifier sIdent) {
            List<String> autocompleteRefs = Arrays.asList(this.wrapper.getContextObjectProvider()
                .getAutocompleteFocusedOption(context).getAutocompleteRefs(sIdent.options()));
            List<Object> results = container.methods().stream()
                .filter(m -> m.identifier().type().equals(InteractionType.AUTOCOMPLETE))
                .filter(m -> autocompleteRefs.contains(m.identifier().name()))
                .map(m -> m.run(context))
                .filter(Objects::nonNull)
                .toList();
            if (!results.isEmpty())
                this.wrapper.respondAutocomplete(context, results);
        }
    }

    private InteractionIdentifier convertToCommandIdentifier(InteractionIdentifier identifier) {
        if (Objects.isNull(identifier))
            return null;
        return InteractionIdentifier.builder()
            .type(InteractionType.COMMAND)
            .name(identifier.name())
            .parent(convertToCommandIdentifier(identifier.parent()))
            .build();
    }
    
}
