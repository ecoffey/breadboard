package com.eoinisaweome.breadboard;

public class ReflectionRegistrationBuilder extends BaseRegistrationBuilder {
    private final Class concreteClass;

    public ReflectionRegistrationBuilder(Class concreteClass) {
        this.concreteClass = concreteClass;
    }

    ReflectionRegistration build() {
        return new ReflectionRegistration(concreteClass, scopeType, services);
    }
}
