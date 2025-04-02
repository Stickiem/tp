package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersonsTestSet2 {

    public static final Person TRAN_KHOI_NGUYEN = new PersonBuilder().withName("Tran Khoi Nguyen")
            .withAddress("NUS, COM1, Block 6, #03-05").withEmail("khoi.nguyen123@gmail.com")
            .withPhone("94351253").withTags("NUS", "COM1").withOneSocial("@khoi_nguyen").build();
    public static final Person TRISTAN_NGUYEN = new PersonBuilder().withName("Tristan Nguyen")
            .withAddress("Marina Bay Sands, Singapore").withEmail("tristan.nguyen456@marinabay.com")
            .withPhone("98765432").withTags("MarinaBay").withOneSocial("@tristan_nguyen").build();
    public static final Person PHUNG_KHANH_LINH = new PersonBuilder().withName("Phung Khanh Linh")
            .withPhone("95352563").withEmail("phung.linh@nusmail.com").withAddress("NUS, COM1, Block 3, #04-14")
            .withTags("NUS", "COM1").withOneSocial("@phung_linh").build();
    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("Ho Chi Minh City, Vietnam").withEmail("alice.pauline@vietnammail.com")
            .withPhone("94351253").withTags("Vietnam").withOneSocial("@alice_pauline").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("NUS, COM2, Block 5, #02-25").withEmail("benson.m@webmail.com")
            .withPhone("98765432").withTags("owesMoney", "friends", "NUS", "COM2")
            .withOneSocial("@benson_meier").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz.carl@hello.com").withAddress("Berlin, Germany")
            .withOneSocial("@carl_kurz").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withPhone("87652533").withEmail("daniel.meier@outlook.com").withAddress("NUS, COM2, Block 2, #01-18")
            .withTags("friends", "NUS", "COM2").withOneSocial("@daniel_meier").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("elle.m@nusmail.com").withAddress("NUS, COM1, Block 6, #07-11")
            .withOneSocial("@elle_meyer").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("fiona.kunz@gmail.com").withAddress("Zurich, Switzerland")
            .withOneSocial("@fiona_kunz").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("george.best@webmail.com").withAddress("NUS, COM1, Block 9, #02-16")
            .withOneSocial("@george_best").build();
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("hoon.m@outlook.com").withAddress("Marina Bay Sands, Singapore")
            .withOneSocial("@hoon_meier").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("ida.mueller@hellomail.com").withAddress("Hanoi, Vietnam").withOneSocial("@ida_mueller").build();

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(TRAN_KHOI_NGUYEN, TRISTAN_NGUYEN, PHUNG_KHANH_LINH, ALICE, BENSON,
                CARL, DANIEL, ELLE, FIONA, GEORGE, HOON, IDA));
    }

    public static String getAliceId() {
        return ALICE.getId();
    }

    public static String getBensonId() {
        return BENSON.getId();
    }

    public static String getCarlId() {
        return CARL.getId();
    }

    public static String getDanielId() {
        return DANIEL.getId();
    }

    public static String getElleId() {
        return ELLE.getId();
    }

    public static String getFionaId() {
        return FIONA.getId();
    }

    public static String getGeorgeId() {
        return GEORGE.getId();
    }

    public static String getHoonId() {
        return HOON.getId();
    }

    public static String getIdaId() {
        return IDA.getId();
    }
}
