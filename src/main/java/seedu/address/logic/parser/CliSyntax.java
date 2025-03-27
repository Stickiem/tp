package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands.
 */
public class CliSyntax {
    // Person prefixes.
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_SOCIAL = new Prefix("s/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    // Event prefixes.
    public static final Prefix PREFIX_EVENT_NAME = new Prefix("en/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("desc/");
    public static final Prefix PREFIX_CONTACT = new Prefix("c/");

    // Relationship prefixes.
    public static final Prefix PREFIX_USERID = new Prefix("u/");
    public static final Prefix PREFIX_FORWARD_RELATIONSHIP_NAME = new Prefix("fn/");
    public static final Prefix PREFIX_REVERSE_RELATIONSHIP_NAME = new Prefix("rn/");
}
