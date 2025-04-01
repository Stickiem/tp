package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.joda.time.DateTime;

import com.zoho.hawking.HawkingTimeParser;
import com.zoho.hawking.datetimeparser.configuration.HawkingConfiguration;
import com.zoho.hawking.language.english.model.DateRange;
import com.zoho.hawking.language.english.model.DatesFound;
import com.zoho.hawking.language.english.model.ParserOutput;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Social;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    private static final HawkingTimeParser HAWKING_TIME_PARSER;

    private static final HawkingConfiguration HAWKING_CONFIGURATION;

    private static final String HAWKING_LANGUAGE = "eng";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String social} into a {@code Social}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Social parseSocial(String social) throws ParseException {
        String trimmedSocial = social.trim();
        return new Social(trimmedSocial);
    }

    /**
     * Parses {@code Collection<String> socials} into a {@code Set<Social>}.
     */
    public static Set<Social> parseSocials(Collection<String> socials) throws ParseException {
        requireNonNull(socials);
        final Set<Social> socialSet = new HashSet<>();
        for (String socialName : socials) {
            socialSet.add(parseSocial(socialName));
        }
        return socialSet;
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    // ========= Event Parsing Methods =========

    /**
     * Returns true if all the specified {@code prefixes} have non-empty values in the given {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        requireNonNull(argumentMultimap);
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses {@code eventName} into a trimmed {@code String}.
     *
     * @throws ParseException if the trimmed event name is empty.
     */
    public static String parseEventName(String eventName) throws ParseException {
        requireNonNull(eventName);
        String trimmedName = eventName.trim();
        if (trimmedName.isEmpty()) {
            throw new ParseException("Event name cannot be empty.");
        }
        return trimmedName;
    }

    static {
        HAWKING_TIME_PARSER = new HawkingTimeParser();

        HAWKING_CONFIGURATION = new HawkingConfiguration();

        ZoneId systemDefaultTimeZone = ZoneId.systemDefault();
        String timeZoneId = systemDefaultTimeZone.getId();
        HAWKING_CONFIGURATION.setTimeZone(timeZoneId);

        // only parse one date
        HAWKING_CONFIGURATION.setMaxParseDate(1);

        // dummy sentence to load parser model into RAM first for faster parsing later
        String inputSentence = "what are you doing tomorrow?";

        Date referenceDate = new Date();

        HAWKING_TIME_PARSER.parse(
                inputSentence,
                referenceDate,
                HAWKING_CONFIGURATION,
                HAWKING_LANGUAGE
        );
    }

    /**
     * Parses {@code date} string into a {@code LocalDateTime} object using natural language date parsing.
     * Examples of valid {@code date}s:
     * - "tomorrow at 2pm"
     * - "next monday at 3:30pm"
     * - "in 3 days"
     * - "next week"
     * - Standard date/time formats are still supported
     *
     * @param date The {@code date} string to be parsed.
     * @return The {@code LocalDateTime} object parsed from the {@code date} string.
     * @throws ParseException If the {@code date} string cannot be parsed into a {@code LocalDateTime} object.
     */
    public static LocalDateTime parseDate(String date) throws ParseException {
        requireNonNull(date);

        Date referenceDate = new Date();

        DatesFound datesFound = HAWKING_TIME_PARSER.parse(
                date,
                referenceDate,
                HAWKING_CONFIGURATION,
                HAWKING_LANGUAGE
        );

        List<ParserOutput> parserOutputs = datesFound.getParserOutputs();

        if (parserOutputs.isEmpty()) {
            throw new ParseException("Unable to parse date/time: " + date);
        }

        ParserOutput parserOutput = parserOutputs.get(0);

        DateRange hawkingDateRange = parserOutput.getDateRange();

        // If the date is in the future,
        // start = now
        // end = the date
        DateTime jodaDateTime = hawkingDateRange.getEnd();

        // If the date is in the past,
        // start = the date
        // end = null
        if (jodaDateTime == null) {
            jodaDateTime = hawkingDateRange.getStart();
        }

        // Hawking gives a joda DateTime object, but we want a java LocalDateTime object,

        // so we convert it to its string representation,
        String dateTimeString = jodaDateTime.toString();

        // and convert that into a java LocalDateTime object
        LocalDateTime javaLocalDateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME);

        return javaLocalDateTime;
    }

    // ========= Contact Parsing Methods =========

    /**
     * Parses {@code Collection<String> contacts} into a {@code List<Person>}.
     * Each contact string is interpreted as a person's name, and a {@code Person} object is created with
     * default values for phone, email, address, socials, and tags.
     *
     * @throws ParseException if any of the contact strings are invalid.
     */
    public static List<Person> parseContacts(Collection<String> contacts) throws ParseException {
        requireNonNull(contacts);
        List<Person> contactList = new ArrayList<>();
        for (String contact : contacts) {
            contactList.add(parseContact(contact));
        }
        return contactList;
    }

    /**
     * Parses a single {@code String contact} into a {@code Person} object using the contact name.
     * Default values are used for phone, email, address, socials, and tags.
     *
     * @throws ParseException if the contact string is invalid.
     */
    public static Person parseContact(String contact) throws ParseException {
        requireNonNull(contact);
        String trimmedContact = contact.trim();
        if (trimmedContact.isEmpty()) {
            throw new ParseException("Contact name cannot be empty.");
        }
        Name name = parseName(trimmedContact);
        // Create default dummy values for the remaining fields.
        Phone defaultPhone = new Phone("00000000");
        Email defaultEmail = new Email("unknown@example.com");
        Address defaultAddress = new Address("Unknown");
        Set<Social> socials = new HashSet<>();
        Set<Tag> tags = new HashSet<>();
        return new Person(name, defaultPhone, defaultEmail, defaultAddress, socials, tags);
    }
}
