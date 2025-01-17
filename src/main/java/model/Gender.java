package model;

public enum Gender {

    MALE("Male"), FAMALE("Famale"), OTHER("Other");

    private String name;

    private Gender(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
