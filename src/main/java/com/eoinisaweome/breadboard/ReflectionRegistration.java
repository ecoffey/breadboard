package com.eoinisaweome.breadboard;

import java.util.*;

public class ReflectionRegistration extends BaseRegistration {

    public ReflectionRegistration(Class concreteClass, LifetimeScopeTypes scopeType, Set<Class> services) {
        super(services, concreteClass, scopeType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T resolve(Resolver resolver) {
        return (T)(new ReflectionActivator(resolver).resolve(this));
    }
}
