package com.eoinisaweome.breadboard;

public interface BuilderOptions {
    <T> BuilderOptions as(Class<T> clazz);

    BuilderOptions instancePerDependency();

    BuilderOptions singleInstance();

    BuilderOptions instancePerLifetimeScope();
}
