package org.hbrs.se1.ws26.exercises.uebung1.control;

import java.util.Objects;

public class GermanTranslator implements Translator {

	public String date = null;

	private final String[] translatedToTwelve = {"eins","zwei","drei","vier","fünf","sechs","sieben","acht","neun",
			"zehn","elf","zwölf"};
	private final String[] translatedZehner = {"zwanzig","dreißig","vierzig","fünfzig","sechzig","siebzig",
			"achtzig","neunzig"};

	/**
	 * Methode zur Übersetzung einer Zahl in eine String-Repraesentation
	 */
	 public String translateNumber(int number) {
		// [ihr Source Code aus Übung 1-2]
		 if (number < 1 | number > 100) {
			 setDate("dd.MM.yyyy");
			 throw new IllegalArgumentException(
					 "Uebersetzung der Zahl " + number + " nicht moeglich ("
					 + printInfo()
			 );
		 }

		if (number == 17) {return "siebzehn";}
		if (number == 16) {return "sechzehn";}
		if (number == 100) {return "einhundert";}
		if (number < 13) {return translateSubThirteen(number);}
		if (number < 20) {return translatedSubTwenty(number);}
		return translatedSubHundred(number);
	}
	private String translateSubThirteen(int number) {
		 return translatedToTwelve[number - 1];
	}

	private String translatedSubTwenty(int number) {
		 int einerstelle = number % 10;
		 return translatedToTwelve[einerstelle - 1] + translatedToTwelve[9];
	}
	private String translatedSubHundred(int number) {
		 int einerstelle = number % 10;
		 int zehnerstelle = (number - einerstelle) / 10;
		 if (einerstelle == 0) {
			 return translatedZehner[zehnerstelle - 2];
		 }

		 String translatedEiner = translatedToTwelve[einerstelle - 1];
		 if (einerstelle == 1) {
			 translatedEiner = translatedEiner.substring(0, 3);
		 }

		 return translatedEiner + "und" + translatedZehner[zehnerstelle - 2];
	}


	/**
	 * Objektmethode der Klasse GermanTranslator zur Ausgabe einer Info.
	 */
	String printInfo(){
		return "GermanTranslator v1.9, erzeugt am " + this.date ;
	}

	/**
	 * Setzen des Datums, wann der Uebersetzer erzeugt wurde (Format: dd.MM.yyyy (Beispiel: "20.08.2026"))
	 * Das Datum sollte system-intern durch eine Factory-Klasse gesetzt werden und nicht von externen View-Klassen
	 * Technisch sollte einfach das "heutige" Datum gesetzt werden.
	 */
	public void setDate(String format) {
		date = DateFactory.currentDate(format);
	}

	/**
	 * Auslesen des gesetzten Datums
	 * @return
	 */
	public String getDate() {
		return date;
	}
}
