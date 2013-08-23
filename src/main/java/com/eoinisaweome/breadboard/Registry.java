package com.eoinisaweome.breadboard;

public interface Registry {
    void register(Registration registration);

    <T> Registration findRegistrationByClass(Class<T> clazz);
}