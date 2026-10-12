package se1.ws26.tests.uebung2;

import org.hbrs.se1.ws26.exercises.uebung2.ConcreteMember;
import org.hbrs.se1.ws26.exercises.uebung2.Container;
import org.hbrs.se1.ws26.exercises.uebung2.ContainerException;
import org.hbrs.se1.ws26.exercises.uebung2.Member;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ContainerTest {
    private Container container;
    private Member member1;
    private Member member2;

    @Test
    public void containerTest() {
        container = new Container();
        member1 = new ConcreteMember(0);
        member2 = new ConcreteMember(1);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        assertEquals(0, container.size());

        assertDoesNotThrow(() -> container.addMember(member1));
        assertDoesNotThrow(() -> container.addMember(member2));

        String expected = "Member (ID = 0)" + System.lineSeparator() + "Member (ID = 1)" + System.lineSeparator();
        container.dump();

        assertEquals(expected, output.toString());
        output.reset();

        assertThrows(ContainerException.class, () -> container.addMember(member1));

        assertEquals(2, container.size());

        assertEquals("Member-Objekt mit der ID " + "2" + " existiert nicht!", container.deleteMember(2));
        assertEquals("Member-Objekt erfolgreich entfernt!", container.deleteMember(1));

        container.deleteMember(0);

        assertEquals("Member-Objekt mit der ID " + "0" + " existiert nicht!", container.deleteMember(0));

        container.dump();

        assertEquals("", output.toString());
    }
}
