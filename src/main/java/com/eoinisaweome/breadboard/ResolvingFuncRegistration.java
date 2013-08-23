package com.eoinisaweome.breadboard;

import com.google.common.base.Function;

import java.util.Set;

public class ResolvingFuncRegistration extends BaseRegistration {
    private final Function<Resolver, Object> resolvingFunc;

    public ResolvingFuncRegistration(Function<Resolver, Object> resolvingFunc, Set<Class> services, Class concreteClass, LifetimeScopeTypes scopeType) {
        super(services, concreteClass, scopeType);
        this.resolvingFunc = resolvingFunc;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T resolve(Resolver resolver) {
        return (T)resolvingFunc.apply(resolver);
    }
}
