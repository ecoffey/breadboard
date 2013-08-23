package com.eoinisaweome.breadboard;

import java.util.Set;

public abstract class BaseRegistration implements Registration {
    protected final Class concreteClass;
    protected final Set<Class> services;
    protected final LifetimeScopeTypes scopeType;

    public BaseRegistration(Set<Class> services, Class concreteClass, LifetimeScopeTypes scopeType) {
        this.services = services;
        this.concreteClass = concreteClass;
        this.scopeType = scopeType;
    }

    @Override
    public Class getConcreteClass() {
        return concreteClass;
    }

    @Override
    public Set<Class> getServices() {
        return services;
    }

    @Override
    public LifetimeScopeTypes getScopeType() {
        return scopeType;
    }
}
