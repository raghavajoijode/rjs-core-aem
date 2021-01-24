package org.subra.aem.rjs.core.commons.constants;

/**
 * @author Raghava Joijode
 */
public enum UserMapperService {

    EMAIL_SERVICE("rjs-email-service"),
    ADMIN_SERVICE("rjs-admin-service");

    private final String value;

    UserMapperService(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
