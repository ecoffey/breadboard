package com.eoinisaweome.breadboard;

import java.util.Set;

public class ResolvingFuncRegistration extends BaseRegistration {
    private final ResolvingFunc resolvingFunc;

    public ResolvingFuncRegistration(ResolvingFunc resolvingFunc, Set<Class> services, Class concreteClass, LifetimeScopeTypes scopeType) {
        super(services, concreteClass, scopeType);
        this.resolvingFunc = resolvingFunc;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T resolve(Resolver resolver) {
        return resolvingFunc.apply(resolver);
    }
}
