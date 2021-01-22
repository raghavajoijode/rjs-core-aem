package org.subra.aem.rjs.core.commons.constants;

/**
 * @author Raghava Joijode
 */
public enum UserMapperService {

    EMAIL_SERVICE("subra-email-service"),
    ADMIN_SERVICE("subra-admin-service");

    private String value;

    private UserMapperService(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
