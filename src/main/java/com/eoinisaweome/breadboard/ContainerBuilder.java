package com.eoinisaweome.breadboard;

import com.google.common.base.Function;

import java.util.LinkedList;
import java.util.List;

interface RegistrationBuilderCallback {
    void apply(Registry registry);
}

public class ContainerBuilder {
    private final List<RegistrationBuilderCallback> registrationsCallbacks = new LinkedList<RegistrationBuilderCallback>();

    public <T> BuilderOptions register(Class<T> concreteClass) {
        final ReflectionRegistrationBuilder rb = new ReflectionRegistrationBuilder(concreteClass);

        registrationsCallbacks.add(new RegistrationBuilderCallback() {
            @Override
            public void apply(Registry registry) {
                ReflectionRegistration r = rb.build();
                registry.register(r);
            }
        });

        return rb;
    }

    public <T> BuilderOptions register(Class<T> concreteClass, Function<Resolver, Object> resolvingFunc) {
        final ResolvingFuncRegistrationBuilder<T> rfb = new ResolvingFuncRegistrationBuilder<>(concreteClass, resolvingFunc);

        registrationsCallbacks.add(new RegistrationBuilderCallback() {
            @Override
            public void apply(Registry registry) {
                ResolvingFuncRegistration r = rfb.build();
                registry.register(r);
            }
        });

        return rfb;
    }

    public Container build() {
        Registry registry = new SimpleRegistry();

        for (RegistrationBuilderCallback callback : registrationsCallbacks) {
            callback.apply(registry);
        }

        return new Container(registry);
    }
}
