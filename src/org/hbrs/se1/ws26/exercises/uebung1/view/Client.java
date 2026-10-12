package org.hbrs.se1.ws26.exercises.uebung1.view;

import org.hbrs.se1.ws26.exercises.uebung1.control.*;

public class Client {

	/**
	 * Methode zur Ausgabe einer Zahl auf der Console
	 * (auch bezeichnet als CLI, Terminal)
	 * Verwendung des Design Pattern: TODO (GoF, Kapitel 6)
	 * Problem: TODO
	 * Lösung: TODO
	 *
	 */
		 void display( int aNumber ){
			// In dieser Methode soll die Methode translateNumber
			// mit dem übergegebenen Wert der Variable aNumber
			// aufgerufen werden.
			//
			// Strenge Implementierung (nur) gegen das Interface Translator gewuenscht!

			 Translator translator = TranslatorFactory.create("de");
             assert translator != null;

             System.out.println("Das Ergebnis der Berechnung: " +
					translator.translateNumber(aNumber)  );

		 }
}





