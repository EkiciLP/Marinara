package net.tomatentum.marinara.wrapper;

import net.tomatentum.marinara.structure.data.ButtonStructureData;

public interface ComponentStructureConverter<B extends Object> {

    B convertButton(ButtonStructureData data);
    
}
