package org.subra.aem.rjs.core.jcr.constants;

/**
 * @author Raghava Joijode
 */
public enum JcrFileNames {

    DEFAULT_TEXT_FILE("file.txt"), CONFIG_NODE("config");

    private String value;

    private JcrFileNames(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
