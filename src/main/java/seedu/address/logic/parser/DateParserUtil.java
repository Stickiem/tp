package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.zoho.hawking.HawkingTimeParser;
import com.zoho.hawking.datetimeparser.configuration.HawkingConfiguration;
import com.zoho.hawking.language.english.model.DateRange;
import com.zoho.hawking.language.english.model.DatesFound;
import com.zoho.hawking.language.english.model.ParserOutput;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains a utility method used for parsing dates.
 */
public class DateParserUtil {

    private static final HawkingTimeParser HAWKING_TIME_PARSER;

    private static final HawkingConfiguration HAWKING_CONFIGURATION;

    private static final String HAWKING_LANGUAGE;

    static {
        HAWKING_TIME_PARSER = new HawkingTimeParser();

        HAWKING_CONFIGURATION = new HawkingConfiguration();

        HAWKING_LANGUAGE = "eng";

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

        try {
            // First try to parse as ISO date-time
            return LocalDateTime.parse(date);
        } catch (Exception e) {
            // If that fails, try natural language parsing
            try {
                Date referenceDate = new Date();
                DatesFound datesFound = HAWKING_TIME_PARSER.parse(
                        date,
                        referenceDate,
                        HAWKING_CONFIGURATION,
                        HAWKING_LANGUAGE
                );

                DateTime jodaDateTime = getJodaDateTime(date, datesFound);

                return LocalDateTime.parse(jodaDateTime.toString(), DateTimeFormatter.ISO_DATE_TIME);
            } catch (Exception ex) {
                throw new ParseException("Unable to parse date/time: " + date);
            }
        }
    }

    /**
     * Converts the {@code DatesFound} object into a {@code DateTime} object.
     * @param date The date string to be parsed.
     * @param datesFound The {@code DatesFound} object containing the parsed date information.
     * @return The {@code DateTime} object parsed from the {@code DatesFound} object.
     * @throws ParseException If the {@code DatesFound} object does not contain any parsed date information.
     */
    private static DateTime getJodaDateTime(String date, DatesFound datesFound) throws ParseException {
        List<ParserOutput> parserOutputs = datesFound.getParserOutputs();

        if (parserOutputs.isEmpty()) {
            throw new ParseException("Unable to parse date/time: " + date);
        }

        ParserOutput parserOutput = parserOutputs.get(0);
        DateRange hawkingDateRange = parserOutput.getDateRange();
        DateTime jodaDateTime = hawkingDateRange.getEnd();

        if (jodaDateTime == null) {
            jodaDateTime = hawkingDateRange.getStart();
        }
        return jodaDateTime;
    }
}
