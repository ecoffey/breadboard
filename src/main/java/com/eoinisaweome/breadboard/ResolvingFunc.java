package com.eoinisaweome.breadboard;

public interface ResolvingFunc {
    <T> T apply(Resolver r);
}
