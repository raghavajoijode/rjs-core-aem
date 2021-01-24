package org.subra.aem.rjs.core.component.models;

import org.subra.aem.rjs.core.component.base.SubraComponent;

public interface Jumbotron extends SubraComponent {

    default String getHeading() {
        throw new UnsupportedOperationException();
    }

    default String getText() {
        throw new UnsupportedOperationException();
    }

}
