package org.example.model;

public abstract class ObjectIdentifier {
    private String objectIdentifier;

    public ObjectIdentifier(String objectIdentifier) {
        this.objectIdentifier = objectIdentifier;
    }

    public String getObjectIdentifier() {
        return objectIdentifier;
    }
}
