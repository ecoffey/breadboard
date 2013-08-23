package com.eoinisaweome.breadboard;

public interface LifetimeScope extends Resolver, AutoCloseable {
    LifetimeScope beginLifetimeScope();
}
