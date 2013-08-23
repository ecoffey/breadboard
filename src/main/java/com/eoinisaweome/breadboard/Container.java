package com.eoinisaweome.breadboard;


import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;

public class Container implements LifetimeScope {
    private final Registry registry;

    private final Map<Registration, Object> singleInstances;
    private final Map<Registration, Object> instancesPerLifetimeScope = Maps.newHashMap();
    private final Set<AutoCloseable> autoCloseables = Sets.newHashSet();
    private final Set<Registration> watchFor = Sets.newHashSet();

    public Container(Registry registry) {
        this.registry = registry;
        this.singleInstances = Maps.newHashMap();
    }

    public Container(Registry registry, Map<Registration, Object> singleInstances) {
        this.registry = registry;
        this.singleInstances = singleInstances;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T resolve(Class<T> clazz) {
        Registration registration = registry.findRegistrationByClass(clazz);

        if (registration == null) {
            throw new RuntimeException("Can not locate registration for " + clazz);
        }

        if (watchFor.contains(registration)) {
            throw new RuntimeException("Cycle detected");
        }

        try {
            watchFor.add(registration);

            T value;

            switch (registration.getScopeType()) {
                case singleInstance:
                    value = resolveFromCache(singleInstances, registration);
                    break;
                case instancePerLifetimeScope:
                    value = resolveFromCache(instancesPerLifetimeScope, registration);
                    break;
                case instancePerDependency:
                    value = resolveFrom(registration);
                    break;
                default:
                    throw new RuntimeException("Unknown enum value");
            }

            return value;
        } finally {
            watchFor.remove(registration);
        }
    }

    @Override
    public LifetimeScope beginLifetimeScope() {
        return new Container(registry, singleInstances);
    }

    @Override
    public void close() throws Exception {
        Map<AutoCloseable, Exception> thrownErrors = Maps.newHashMap();

        for (AutoCloseable autoCloseable : autoCloseables) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                thrownErrors.put(autoCloseable, e);
            }
        }

        if (thrownErrors.size() > 0) {
            throw new RuntimeException("Caught exceptions when disposing scope");
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T resolveFromCache(Map<Registration, Object> cache, Registration registration) {
        if (cache.containsKey(registration)) {
            return (T) cache.get(registration);
        }

        T value = resolveFrom(registration);
        cache.put(registration, value);

        return value;
    }

    private <T> T resolveFrom(Registration registration) {
        T value = registration.resolve(this);

        rememberIfAutoCloseable(value);
        return value;
    }

    private void rememberIfAutoCloseable(Object value) {
        if (AutoCloseable.class.isAssignableFrom(value.getClass())) {
            autoCloseables.add((AutoCloseable) value);
        }
    }
}