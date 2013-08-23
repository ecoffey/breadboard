package com.eoinisaweome.breadboard;

import com.google.common.base.Function;

public class ResolvingFuncRegistrationBuilder<T> extends BaseRegistrationBuilder {

    private final Class<T> concreteClass;
    private final Function<Resolver, Object> resolvingFunc;

    public ResolvingFuncRegistrationBuilder(Class<T> concreteClass, Function<Resolver, Object> resolvingFunc) {
        this.concreteClass = concreteClass;
        this.resolvingFunc = resolvingFunc;
    }

    public ResolvingFuncRegistration build() {
        return new ResolvingFuncRegistration(resolvingFunc, services, concreteClass, scopeType);
    }
}
