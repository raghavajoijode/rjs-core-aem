package org.subra.aem.rjs.core.mailer.models;

import org.subra.aem.rjs.core.mailer.Template;

import java.util.List;

/**
 * @author Raghava Joijode
 */
public interface TemplateListModel {

    default List<Template> getTemplates() {
        throw new UnsupportedOperationException();
    }

}
