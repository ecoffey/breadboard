package com.eoinisaweome.breadboard;

import java.util.HashSet;
import java.util.Set;

public class BaseRegistrationBuilder implements BuilderOptions {
    protected final Set<Class> services = new HashSet<Class>();
    protected LifetimeScopeTypes scopeType = LifetimeScopeTypes.instancePerDependency;

    @Override
    public <T> BuilderOptions as(Class<T> clazz) {
        services.add(clazz);
        return this;
    }

    @Override
    public BuilderOptions instancePerDependency() {
        scopeType = LifetimeScopeTypes.instancePerDependency;
        return this;
    }

    @Override
    public BuilderOptions singleInstance() {
        scopeType = LifetimeScopeTypes.singleInstance;
        return this;
    }

    @Override
    public BuilderOptions instancePerLifetimeScope() {
        scopeType = LifetimeScopeTypes.instancePerLifetimeScope;
        return this;
    }
}
