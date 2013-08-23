package com.eoinisaweome.breadboard;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SimpleRegistry implements Registry {
    private final List<Registration> registrations = new LinkedList<>();
    private final Map<Class, Registration> classToRegistration = new HashMap<>();

    @Override
    public void register(Registration registration) {
        registrations.add(registration);

        for (Class service : registration.getServices()) {
            addRegistration(service, registration);
        }

        addRegistration(registration.getConcreteClass(), registration);
    }

    @Override
    public <T> Registration findRegistrationByClass(Class<T> clazz) {
        return classToRegistration.get(clazz);
    }

    private <T> void addRegistration(Class<T> clazz, Registration registration) {
        if (classToRegistration.containsKey(clazz)) {
            throw new RuntimeException(clazz + " is already mapped");
        }

        classToRegistration.put(clazz, registration);
    }
}
