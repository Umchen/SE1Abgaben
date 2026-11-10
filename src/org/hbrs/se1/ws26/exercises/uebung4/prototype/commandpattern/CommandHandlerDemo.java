package org.hbrs.se1.ws26.exercises.uebung4.prototype.commandpattern;

import java.util.Scanner;

/**
 * Command Pattern (Kapitel 6)
 */
public class CommandHandlerDemo {

	public void startEingabe()  {
		String strInput = null;

		// Initialisierung des Eingabe-View
		Scanner scanner = new Scanner( System.in );

		while ( true ) {
			// Print the prompt
			System.out.print("> ");

			// Naechster Befehl
			strInput = scanner.nextLine();

			// Extrahiert ein Array aus der Eingabe
			String[] strings = strInput.split(" ");
			String nextCommand = strings[0]; // das Commmando

			// KEINE IF-Anweisung oder Switch-Case-Statement mit N Zeilen
			// Anforderung: Auswahl des "Kommandos" in EINER Zeile


		}

	}

}
