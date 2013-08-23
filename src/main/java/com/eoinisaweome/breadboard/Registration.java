package com.eoinisaweome.breadboard;

import java.util.Set;

public interface Registration {
    Class getConcreteClass();

    Set<Class> getServices();

    LifetimeScopeTypes getScopeType();

    <T> T resolve(Resolver resolver);
}
