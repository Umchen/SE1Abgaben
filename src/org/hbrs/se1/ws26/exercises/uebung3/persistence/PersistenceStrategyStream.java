package org.hbrs.se1.ws26.exercises.uebung3.persistence;
import org.hbrs.se1.ws26.solutions.uebung2.Member;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersistenceStrategyStream<E> implements PersistenceStrategy<E> {

    // URL of file, in which the objects are stored
    private String location = "objects.ser";

    public PersistenceStrategyStream(String location) {
        this.location = location;
    }
    public PersistenceStrategyStream() {}

    public String getLocation() {
        return location;
    }

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
    public void save(List<E> member) throws PersistenceException  {
        try (FileOutputStream fos = new FileOutputStream(location);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(member);
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
    public List<E> load() throws PersistenceException  {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(location))) {
            Object o = ois.readObject();

            if (!(o instanceof List<?> list)) {
                throw new PersistenceException(PersistenceException.ExceptionType.TypeMisMatch,
                        "Das gespeicherte Objekt ist keine Liste!"
                );
            }
            return (List<E>) o;

        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException(
                    PersistenceException.ExceptionType.ConnectionNotAvailable,
                    "File Location konnte nicht gefunden werden!"
            );
        }
    }
}
