package com.cydercode.inception.controller;

import com.google.common.base.MoreObjects;

import java.util.List;

public class Command {

    private String action;
    private List<String> parameters;

    public Command(String action, List<String> parameters) {
        this.action = action;
        this.parameters = parameters;
    }

    public String getAction() {
        return action;
    }

    public List<String> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("action", action)
                .add("parameters", parameters)
                .toString();
    }
}
