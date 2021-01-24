package org.subra.aem.rjs.core.mailer.models;

import org.subra.aem.rjs.core.mailer.Template;

import java.util.List;

/**
 * @author Raghava Joijode
 */
public interface TemplateModel {

    default Template getTemplate() {
        throw new UnsupportedOperationException();
    }

    default String getMessage() {
        throw new UnsupportedOperationException();
    }

    default List<String> getLookUpKeys() {
        throw new UnsupportedOperationException();
    }

}
