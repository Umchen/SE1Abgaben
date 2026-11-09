package org.hbrs.se1.ws26.exercises.uebung4.prototype.improvements.controller;

import org.hbrs.se1.ws26.exercises.uebung4.prototype.improvements.model.Container;
import org.hbrs.se1.ws26.exercises.uebung4.prototype.improvements.model.persistence.PersistenceStrategyStream;

public class Main {

	 /** Start-Methoden zum Starten des Programms
	 * (hier koennen ggf. weitere Initialisierungsarbeiten gemacht werden spaeter)
      */
    public static void main (String[] args) throws Exception {
        Container con = Container.getInstance();
        con.setPersistenceStrategy( new PersistenceStrategyStream() );
        InputDialog dialog = new InputDialog();
        dialog.startEingabe();
    }
}
