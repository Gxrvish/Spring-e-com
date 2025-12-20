package com.ecommerce.sb_ecom.common.exception;

/**
 * Custom exception to handle cases where a requested resource is not found.
 * <p>
 * This exception provides detailed information about the missing resource,
 * including the resource name, the field used to search for it, and the
 * specific
 * value of the field that was not found.
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 * 
 * <pre>
 * throw new ResourceNotFoundException("User", "id", 123L);
 * </pre>
 *
 * <p>
 * This will produce an error message like:
 * </p>
 * 
 * <pre>
 * User not found with id: 123
 * </pre>
 */
public class ResourceNotFoundException extends RuntimeException {

    private final String resourceName;
    private final String searchField;
    private final String searchValue;
    private final Long searchId;

    /**
     * Creates a new {@code ResourceNotFoundException} for a resource searched by a
     * string field value.
     *
     * @param resourceName the name of the resource that was not found (e.g.,
     *                     "User")
     * @param searchField  the field used to search (e.g., "email")
     * @param searchValue  the field value that was not found (e.g.,
     *                     "john@example.com")
     */
    public ResourceNotFoundException(String resourceName, String searchField, String searchValue) {
        super(String.format("%s not found with %s: '%s'", resourceName, searchField, searchValue));
        this.resourceName = resourceName;
        this.searchField = searchField;
        this.searchValue = searchValue;
        this.searchId = null;
    }

    /**
     * Creates a new {@code ResourceNotFoundException} for a resource searched by a
     * numeric field value.
     *
     * @param resourceName the name of the resource that was not found (e.g.,
     *                     "Product")
     * @param searchField  the field used to search (e.g., "id")
     * @param searchId     the numeric value of the field that was not found (e.g.,
     *                     42)
     */
    public ResourceNotFoundException(String resourceName, String searchField, Long searchId) {
        super(String.format("%s not found with %s: %d", resourceName, searchField, searchId));
        this.resourceName = resourceName;
        this.searchField = searchField;
        this.searchValue = null;
        this.searchId = searchId;
    }

    // Getters
    public String getResourceName() {
        return resourceName;
    }

    public String getSearchField() {
        return searchField;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public Long getSearchId() {
        return searchId;
    }
}
