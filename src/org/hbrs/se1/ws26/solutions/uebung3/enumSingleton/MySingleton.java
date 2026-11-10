package org.hbrs.se1.ws26.solutions.uebung3.enumSingleton;

/**
 * Variante mit enum, die in vielen Quellen als die beste Lösung angesehen wird
 * Der Nachteil: Verwendung eines enums ;-)
 * Mehr Infos: https://www.baeldung.com/a-guide-to-java-enums
 *
 */
public enum MySingleton {
    INSTANCE;

    int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
