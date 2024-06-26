package com.andinolabs.demo.stateengine;

public interface Transitionable {

    String getState();

    void updateState(String newState);

}
