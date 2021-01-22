package org.subra.aem.rjs.core.commons.constants;

/**
 * @author Raghava Joijode
 */
public enum CookieType {

    ACCOUNT_ID("subraAccountId"), AUTH_KEY("subraAuthKey");

    private final String name;

    private CookieType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
