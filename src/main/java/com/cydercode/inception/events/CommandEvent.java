package com.cydercode.inception.events;

public class CommandEvent extends Event {

    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "CommandEvent{" +
                "command='" + command + '\'' +
                '}';
    }
}
