package de.tech26.robotfactory.domain;

public enum RobotPartType {

    FACE,
    MATERIAL,
    ARMS,
    MOBILITY;

    @Override
    public String toString() {
        return this.name();
    }
}
