package com.eoinisaweome.breadboard;

import com.google.common.base.Function;

public class Runner {

    public interface AThing {
        void sayHi();

        void sayHi(int value);
    }

    public static class AThingImpl implements AThing {
        private static int counter = 0;

        private final int value = counter++;

        public void sayHi() {
            sayHi(value);
        }

        public void sayHi(int value) {
            System.out.println("OH HAI " + value);
        }
    }

    public static class DoStuff {
        private static int counter = 0;

        private final int value = counter++;
        private final AThing aThing;

        public DoStuff(AThing aThing) {

            this.aThing = aThing;
        }

        public void doStuff() {
            aThing.sayHi(value);
        }
    }

    public static void main(String... args) {
        ContainerBuilder builder = new ContainerBuilder();

        builder.register(AThingImpl.class)
                .as(AThing.class)
                .singleInstance();

        builder.register(DoStuff.class, new Function<Resolver, Object>() {
            @Override
            public Object apply(Resolver r) {
                return new DoStuff(r.resolve(AThing.class));
            }
        });

        Container container = builder.build();

        try (LifetimeScope scope = container.beginLifetimeScope()) {
            AThing aThing = scope.resolve(AThing.class);
            DoStuff doStuff = scope.resolve(DoStuff.class);

            doStuff.doStuff();

            aThing.sayHi();

            try (LifetimeScope scope2 = scope.beginLifetimeScope()) {
                AThing aThing2 = scope2.resolve(AThing.class);
                DoStuff doStuff2 = scope2.resolve(DoStuff.class);

                doStuff2.doStuff();

                aThing2.sayHi();
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
