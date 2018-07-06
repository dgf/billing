package org.aplatanao.billing.provider;

import javax.validation.MessageInterpolator;
import java.util.Locale;

public class LocaleSpecificMessageInterpolator implements MessageInterpolator {

    private final MessageInterpolator defaultInterpolator;

    public LocaleSpecificMessageInterpolator(MessageInterpolator interpolator) {
        this.defaultInterpolator = interpolator;
    }

    @Override
    public String interpolate(String message, Context context) {
        return defaultInterpolator.interpolate(message, context, LocaleThreadLocal.get());
    }

    @Override
    public String interpolate(String message, Context context, Locale locale) {
        return defaultInterpolator.interpolate(message, context, locale);
    }
}