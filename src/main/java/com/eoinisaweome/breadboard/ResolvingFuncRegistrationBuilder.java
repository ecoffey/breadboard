package com.eoinisaweome.breadboard;

import java.util.Set;

public class ResolvingFuncRegistrationBuilder<T> extends BaseRegistrationBuilder {

    private final Class<T> concreteClass;
    private final ResolvingFunc resolvingFunc;

    public ResolvingFuncRegistrationBuilder(Class<T> concreteClass, ResolvingFunc resolvingFunc) {
        this.concreteClass = concreteClass;
        this.resolvingFunc = resolvingFunc;
    }

    public ResolvingFuncRegistration build() {
        return new ResolvingFuncRegistration(resolvingFunc, services, concreteClass, scopeType);
    }
}
