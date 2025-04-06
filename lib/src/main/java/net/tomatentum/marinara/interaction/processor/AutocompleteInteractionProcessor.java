package net.tomatentum.marinara.interaction.processor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.ident.SlashCommandIdentifier;
import net.tomatentum.marinara.registry.InteractionEntry;
import net.tomatentum.marinara.wrapper.IdentifierProvider;

public class AutocompleteInteractionProcessor extends InteractionMethodProcessor {

    private Marinara marinara;

    public AutocompleteInteractionProcessor(Marinara marinara, IdentifierProvider provider) {
        super(provider, Set.of(InteractionType.AUTOCOMPLETE));
        this.marinara = marinara;
    }

    @Override
    public void processInteraction(Object context, InteractionIdentifier identifier) {
        Optional<InteractionEntry> entry = this.marinara.getRegistry().findFor(convertToCommandIdentifier(identifier));
        if (entry.isPresent() && entry.get().identifier() instanceof SlashCommandIdentifier sIdent) {
            List<String> autocompleteRefs = Arrays.asList(this.marinara.getWrapper().getContextObjectProvider()
                .getAutocompleteFocusedOption(context).getAutocompleteRefs(sIdent.options()));
            List<Object> results = this.marinara.getRegistry().interactions().stream()
                .filter(e -> e.type().equals(InteractionType.AUTOCOMPLETE))
                .filter(e -> autocompleteRefs.contains(e.identifier().name()))
                .map(e -> e.runAll(context))
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .toList();
            if (!results.isEmpty())
                marinara.getWrapper().respondAutocomplete(context, results);
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
