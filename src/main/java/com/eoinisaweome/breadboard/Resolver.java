package com.eoinisaweome.breadboard;

public interface Resolver {
    <T> T resolve(Class<T> clazz);
}

