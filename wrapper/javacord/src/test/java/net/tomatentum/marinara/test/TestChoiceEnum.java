package net.tomatentum.marinara.test;

import net.tomatentum.marinara.interaction.commands.ChoiceValueProvider;

public enum TestChoiceEnum implements ChoiceValueProvider<String> {
    TestValue("testValue"),
    FooBar("fooBar"),
    Spongebob("spongebob");

    private String value;

    private TestChoiceEnum(String value) {
        this.value = value;
    }
    @Override
    public String getChoiceValue() {
        return value;
    }
    
}
