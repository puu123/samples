
package org.glassfish.jersey.jetty.internal;

import org.glassfish.jersey.internal.l10n.Localizable;
import org.glassfish.jersey.internal.l10n.LocalizableMessageFactory;
import org.glassfish.jersey.internal.l10n.Localizer;


/**
 * Defines string formatting method for each constant in the resource file
 * 
 */
public final class LocalizationMessages {

    private final static LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("org.glassfish.jersey.jetty.internal.localization");
    private final static Localizer localizer = new Localizer();

    public static Localizable localizableEXCEPTION_SENDING_ERROR_RESPONSE(Object arg0, Object arg1) {
        return messageFactory.getMessage("exception.sending.error.response", arg0, arg1);
    }

    /**
     * I/O exception occurred while sending "{0}/{1}" error response.
     * 
     */
    public static String EXCEPTION_SENDING_ERROR_RESPONSE(Object arg0, Object arg1) {
        return localizer.localize(localizableEXCEPTION_SENDING_ERROR_RESPONSE(arg0, arg1));
    }

    public static Localizable localizableUNABLE_TO_CLOSE_RESPONSE() {
        return messageFactory.getMessage("unable.to.close.response");
    }

    /**
     * Unable to close response output
     * 
     */
    public static String UNABLE_TO_CLOSE_RESPONSE() {
        return localizer.localize(localizableUNABLE_TO_CLOSE_RESPONSE());
    }

}
