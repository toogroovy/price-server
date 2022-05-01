package com.elshipper.notificationapi.domain;

public enum AssetType {
    CRYPTO("CRYPTO"),
    STOCK("STOCK"),
    FOREX("FOREX");

    private final String type;

    AssetType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "AssetType{" +
                "type='" + type + '\'' +
                '}';
    }
}