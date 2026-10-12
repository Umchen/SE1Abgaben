package org.hbrs.se1.ws26.exercises.uebung1.control;

public class TranslatorFactory {
    public static Translator create(String countryCode) {
        if (countryCode.equalsIgnoreCase("de")) {
            return new GermanTranslator();
        } else {
            return null;
        }
    }
}
