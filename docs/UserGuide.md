---
layout: page
title: User Guide
---

INcontact is a **desktop app for managing business contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, INcontact can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2425S2-CS2103T-T09-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to INcontact.

Format: `add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [s/SOCIAL]... [t/TAG]...`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 s/@social1 s/social2 t/Investor`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`
* `add n/contact with name only`

### Listing all persons : `list`

Shows a list of all persons in INcontact.

Format: `list`

### Editing a person : `edit`

Edits an existing person in INcontact.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Locating persons by phone: `findphone`

Finds persons whose phone number contain any of the given keywords.

Format: `findphone KEYWORD [MORE_KEYWORDS]`

* Only the phone number is searched.

Examples:
* `findphone 123 456`

### Locating persons by email: `findemail`

Finds persons whose email contain any of the given keywords.

Format: `findemail KEYWORD [MORE_KEYWORDS]`

* Only the email is searched.

Examples:
* `findemail example1@example.com`

### Locating persons by address: `findaddress`

Finds persons whose address contain any of the given keywords.

Format: `findaddress KEYWORD [MORE_KEYWORDS]`

* Only the address is searched.

Examples:
* `findaddress street avenue park`

### Locating persons by social: `findsocial`

Finds persons whose social media handle contain any of the given keywords.

Format: `findsocial KEYWORD [MORE_KEYWORDS]`

* Only the social media handle is searched.

Examples:
* `findsocial facebook twitter linkedin`

### Deleting a person : `delete`

Deletes the specified person from INcontact.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in INcontact.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from INcontact.

Format: `clear`

### Adding a relationship: `addRelationship`

Adds a relationship to INcontact.

Format: `addRelationship u/USER_1_ID u/USER_2_ID n/RELATIONSHIP_NAME [t/TAG]…`

Example:
* `addRelationship u/12345678 u/87654321 n/Business Partner t/Investment`

### Deleting a relationship: `deleteRelationship`

Deletes a relationship from INcontact.

Format: `deleteRelationship u/USER_1_ID u/USER_2_ID n/RELATIONSHIP_NAME`

Example:
* `deleteRelationship u/12345678 u/87654321 n/Business Partner`

### Adding an event: `addEvent`

Adds an event to INcontact.

Format: `addEvent en/EVENT_NAME d/DATE [l/LOCATION] [desc/DESCRIPTION] [t/TAG]…`

Examples:
* `addEvent en/Annual Investor Meetup d/2025-03-15 l/Singapore desc/Networking session for investors t/Finance`
* `addEvent en/Tech Conference d/2025-06-20`

### Deleting an event: `deleteEvent`

Deletes an event from INcontact.

Format: `deleteEvent u/EVENT_ID`

Example:
* `deleteEvent u/98765432`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, INcontact will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the INcontact to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous INcontact home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action                  | Format, Examples                                                                                                                                                                                                            |
|-------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**                 | `add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [s/SOCIAL]... [t/TAG]...` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 s/@social1 s/social2 /t investor t/friend t/colleague` |
| **Clear**               | `clear`                                                                                                                                                                                                                     |
| **Delete**              | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                                                                         |
| **Edit**                | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                                                                                  |
| **Find**                | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`                                                                                                                                                                  |
| **Find by Phone**       | `findphone KEYWORD [MORE_KEYWORDS]`                                                                                                                                                                                         |
| **Find by Email**       | `findemail KEYWORD [MORE_KEYWORDS]`                                                                                                                                                                                         |
| **Find by Address**     | `findaddress KEYWORD [MORE_KEYWORDS]`                                                                                                                                                                                       |
| **Find by Social**      | `findsocial KEYWORD [MORE_KEYWORDS]`                                                                                                                                                                                        |
| **List**                | `list`                                                                                                                                                                                                                      |
| **Add Relationship**    | `addRelationship u/USER_1_ID u/USER_2_ID n/RELATIONSHIP_NAME [t/TAG]…`                                                                                                                                                      |
| **Delete Relationship** | `deleteRelationship u/USER_1_ID u/USER_2_ID n/RELATIONSHIP_NAME`                                                                                                                                                            |
| **Add Event**           | `addEvent en/EVENT_NAME d/DATE [l/LOCATION] [desc/DESCRIPTION] [t/TAG]`                                                                                                                                                     |
| **Delete Event**        | `deleteEvent u/EVENT_ID`                                                                                                                                                                                                    |
| **Help**                | `help`                                                                                                                                                                                                                      |
