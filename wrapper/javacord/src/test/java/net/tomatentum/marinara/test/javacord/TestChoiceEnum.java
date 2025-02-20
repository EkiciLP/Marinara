package net.tomatentum.marinara.test.javacord;

import net.tomatentum.marinara.interaction.commands.choice.ChoiceValueProvider;

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
