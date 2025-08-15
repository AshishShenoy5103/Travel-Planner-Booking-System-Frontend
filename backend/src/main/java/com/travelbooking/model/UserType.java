package com.travelbooking.model;

// Two types of user's ADMIN and USER

public enum UserType {
    ADMIN("ADMIN"), USER("USER");

    private final String type;

    UserType(String string) {
        this.type = string;
    }

    @Override
    public String toString() {
        return type;
    }
}
