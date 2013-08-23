package com.eoinisaweome.breadboard;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ReflectionActivator {
    private final Resolver resolver;

    public ReflectionActivator(Resolver resolver) {
        this.resolver = resolver;
    }

    public Object resolve(Registration registration) {
        Class concreteClass = registration.getConcreteClass();

        Constructor[] constructors = concreteClass.getConstructors();

        Arrays.sort(constructors, new Comparator<Constructor>() {
            @Override
            public int compare(Constructor o1, Constructor o2) {
                int o1Len = o1.getParameterTypes().length;
                int o2Len = o2.getParameterTypes().length;

                return o1Len < o2Len
                        ? -1
                        : (o2Len > o1Len
                        ? 1
                        : 0);
            }
        });

        Constructor constructor = constructors[constructors.length - 1];

        Class[] parameterTypes = constructor.getParameterTypes();

        List<Object> params = new ArrayList<>(parameterTypes.length);

        for (Class paramType : parameterTypes) {
            params.add(resolver.resolve(paramType));
        }

        return build(constructor, params);
    }

    private Object build(Constructor constructor, List<Object> params) {
        try {
            return constructor.newInstance(params.toArray());
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;
    }
}
