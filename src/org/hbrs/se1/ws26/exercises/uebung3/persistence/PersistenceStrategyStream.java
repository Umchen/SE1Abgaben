package org.hbrs.se1.ws26.exercises.uebung3.persistence;
import org.hbrs.se1.ws26.solutions.uebung2.Member;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersistenceStrategyStream<Member> implements PersistenceStrategy<Member> {

    // URL of file, in which the objects are stored
    private String location = "objects.ser";

    // Backdoor method used only for testing purposes, if the location should be changed in a Unit-Test
    // Example: Location is a directory (Streams do not like directories, so try this out ;-)!
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    /**
     * Method for saving a list of Member-objects to a disk (HDD)
     * Look-up in Google for further help! Good source:
     * https://www.digitalocean.com/community/tutorials/objectoutputstream-java-write-object-file
     * (Last Access: Oct, 12th 2026)
     */
    public void save(List<Member> member) throws PersistenceException  {
        try {
            FileOutputStream fos = new FileOutputStream(location);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(member);
            oos.close();
            fos.close();
        } catch (IOException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable,
                    "File Location konnte nicht gefunden werden!");
        }
    }

    @Override
    /**
     * Method for loading a list of Member-objects from a disk (HDD)
     * Some coding examples come for free :-)
     * Take also a look at the import statements above ;-!
     */
    public List<Member> load() throws PersistenceException  {
        // Some Coding hints ;-)

        // ObjectInputStream ois = null;
        // FileInputStream fis = null;
        // List<...> newListe =  null;
        //
        // Initiating the Stream (can also be moved to method openConnection()... ;-)
        // fis = new FileInputStream( " a location to a file" );

        // Tipp: Use a directory (ends with "/") to implement a negative test case ;-)
        // ois = new ObjectInputStream(fis);

        // Reading and extracting the list (try .. catch ommitted here)
        // Object obj = ois.readObject();

        // if (obj instanceof List<?>) {
        //       newListe = (List) obj;
        // return newListe

        // and finally close the streams
        List<Member> memberList = new ArrayList<>();
        ObjectInputStream ois = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(location);
            ois = new ObjectInputStream(fis);
        } catch (IOException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable,
                    "File Location konnte nicht gefunden werden!");
        }

        try {
            Object o = ois.readObject();

            if (o instanceof List<?>) {
                memberList = (List<Member>) o;
            } else {
                throw new PersistenceException(PersistenceException.ExceptionType.TypeMisMatch, "Keine Liste vorhanden!");
            }
            ois.close();
            fis.close();
        } catch (ClassNotFoundException | IOException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.TypeMisMatch,
                    "List ist keine List<Member> !");
        }
        return memberList;
    }
}
