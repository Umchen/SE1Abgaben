package se1.ws26.tests.uebung1;

import org.hbrs.se1.ws26.exercises.uebung1.control.GermanTranslator;
import org.hbrs.se1.ws26.exercises.uebung1.control.Translator;
import org.hbrs.se1.ws26.exercises.uebung1.control.TranslatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class GermanTranslatorTest {
    Translator translator = TranslatorFactory.create("de");

    @BeforeEach
    public void setUp() {
        translator = TranslatorFactory.create("de");
    }

    //Aequivalenzklassen
    @ParameterizedTest
    @CsvSource({
            "1, eins",
            "2, zwei",
            "3, drei",
            "4, vier",
            "5, fünf",
            "6, sechs",
            "7, sieben",
            "8, acht",
            "9, neun",
            "11, elf",
            "12, zwölf",
    })
    void einsBisZwoelf(int input, String expected) {
        assertEquals(expected, translator.translateNumber(input));
    }
    @Test
    void testSonderfallSechzehn() {
        String expected = "sechzehn";
        String actual = translator.translateNumber(16);
        assertEquals(expected, actual);
    }
    @Test
    void testSonderfallSiebzehn() {
        String expected = "siebzehn";
        String actual = translator.translateNumber(17);
        assertEquals(expected, actual);
    }

    @Test
    void testRegularSubZwanzig() {
        String expected = "vierzehn";
        String actual = translator.translateNumber(14);
        assertEquals(expected, actual);
    }

    @Test
    void regulars() {
        String expected = "fünfunddreißig";
        String actual = translator.translateNumber(35);
    }

    @ParameterizedTest
    @CsvSource({
            "10, zehn",
            "20, zwanzig",
            "30, dreißig",
            "40, vierzig",
            "50, fünfzig",
            "60, sechzig",
            "70, siebzig",
            "80, achtzig",
            "90, neunzig",
            "100, einhundert",
    })
    void testZehner(int input, String expected) {
        assertEquals(expected, translator.translateNumber(input));
    }

    //Grenzwerte
    @Test
    void grenzwertNull() {
        assertThrows(IllegalArgumentException.class, () -> translator.translateNumber(0));
    }
    @Test
    void grenzwertEins() {
        String expected = "eins";
        String actual = translator.translateNumber(1);
        assertEquals(expected, actual);
    }

    @Test
    void grenzwertHunderteins() {
        assertThrows(IllegalArgumentException.class, () -> translator.translateNumber(101));
    }

    @Test
    void grenzwertDreizehn() {
        String expected = "dreizehn";
        String actual = translator.translateNumber(13);
        assertEquals(expected, actual);
    }

    @Test
    void grenzwertUndSonderfallEinsund() {
        String expected = "einundzwanzig";
        String actual = translator.translateNumber(21);
        assertEquals(expected,actual);
    }

    @Test
    void datumKorrekt() {
        String format = "dd.MM.yyyy";
        GermanTranslator dateTest = new GermanTranslator();
        String expected = LocalDate.now().format(DateTimeFormatter.ofPattern(format));
        dateTest.setDate(format);
        String actual = dateTest.getDate();
        assertEquals(expected, actual);

    }
}