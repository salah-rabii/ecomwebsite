package com.myecommerce.exception;

public class NotFoundException extends RuntimeException {
    private final String resourceName;
    private final Long resourceId;

    public NotFoundException(String resourceName, Long resourceId) {
        super(String.format("%s non trouvé(e) avec l'ID: %d", resourceName, resourceId));
        this.resourceName = resourceName;
        this.resourceId = resourceId;
    }

    // Getters
    public String getResourceName() { return resourceName; }
    public Long getResourceId() { return resourceId; }
}