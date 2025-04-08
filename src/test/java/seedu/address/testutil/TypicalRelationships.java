package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.relationship.Relationship;

/**
 * A utility class containing a list of {@code Relationship} objects to be used in tests.
 */
public class TypicalRelationships {

    public static final Relationship ALICE_BEFRIENDS_BENSON = new RelationshipBuilder()
            .withUser1Id(TypicalPersons.getAliceId())
            .withUser2Id(TypicalPersons.getBensonId())
            .withForwardName("Friends with")
            .withReverseName("Friends with")
            .withTags("close")
            .build();

    public static final Relationship BENSON_MENTORS_CARL = new RelationshipBuilder()
            .withUser1Id(TypicalPersons.getBensonId())
            .withUser2Id(TypicalPersons.getCarlId())
            .withForwardName("Mentors")
            .withReverseName("Mentored by")
            .withTags("work")
            .build();

    public static final Relationship CARL_WORKS_WITH_DANIEL = new RelationshipBuilder()
            .withUser1Id(TypicalPersons.getCarlId())
            .withUser2Id(TypicalPersons.getDanielId())
            .withForwardName("Works with")
            .withReverseName("Works with")
            .withTags("colleague")
            .build();

    private TypicalRelationships() {} // prevents instantiation

    public static List<Relationship> getTypicalRelationships() {
        return new ArrayList<>(Arrays.asList(
                ALICE_BEFRIENDS_BENSON,
                BENSON_MENTORS_CARL,
                CARL_WORKS_WITH_DANIEL
        ));
    }
}
