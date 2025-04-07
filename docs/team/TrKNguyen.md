---
layout: page
title: Tran Khoi Nguyen's Project Portfolio Page
---

### Project: INContact

INcontact is a desktop address book application used for managing business contacts and their relationships. The user interacts with it using a CLI, and it has a GUI created with JavaFX.

Given below are my contributions to the project.

* **New Feature**: Added multiple search functionalities to find contacts by different attributes.
  * What it does: Allows users to search for contacts by name, phone, email, address, social media, tags, and relationships using different commands.
  * Justification: These features improve the product significantly because a user can quickly find specific contacts based on various attributes without having to manually search through the entire list.
  * Highlights: This enhancement required implementing multiple predicates for different search criteria and designing a unified approach to handle various search parameters.
  * Credits: Built upon the existing search architecture while extending it for multiple attributes.

* **New Feature**: Added Sort command to organize contacts in a specified order.
  * **What it does**:
    * The `Sort` command allows users to sort their contacts by one or more attributes, such as **name**, **phone**, **email**, **address**, **tags**, or **socials**.
    * Users can choose to sort in **ascending** or **descending** order using the `-r` flag.
    * Example: `sort -r name phone` sorts contacts by **name** in descending order, followed by **phone** in ascending order.
  * **Justification**: This feature enhances the user experience by providing the ability to organize and retrieve contacts efficiently based on the most relevant attributes.
  * **Highlights**:
    * **Comparator Implementation**: Created custom comparators for each attribute, ensuring that the sorting is case-insensitive and handles various data types like strings (name, phone, email) and collections (tags, socials).
    * **Multiple Fields Sorting**: The feature supports sorting by multiple fields simultaneously. For example, users can sort first by **name**, then by **phone**, and so on.
    * **Reverse Order**: The `-r` flag enables sorting in **reverse** order (i.e., descending), adding flexibility to the sorting mechanism.
    * **Integration**: Integrated the sorting functionality into the existing model with the `Model` interface, ensuring that the sorted list is reflected in the UI.

* **New Feature**: Added Redo and RedoList commands.
  * What it does:
    * **Redo**: Allows users to reverse the effects of previous undo commands.
    * **RedoList**: Displays all commands that can be redone, helping users review past actions.
  * Justification: These features provide users with more control over their actions, allowing them to recover from accidental undos and manage command history more effectively.
  * Highlights:
    * Created a **CommandHistory** system to maintain a session-based history of executed commands.
    * The `redoList` command displays the available commands from the current session.
    * The `redo N` command allows users to re-execute a specific command from the history by providing an index.


* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s2.github.io/tp-dashboard/?search=&sort=totalCommits%20dsc&sortWithin=totalCommits%20dsc&timeframe=commit&mergegroup=&groupSelect=groupByAuthors&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-02-21&tabOpen=true&tabType=authorship&tabAuthor=TrKNguyen&tabRepo=AY2425S2-CS2103T-T09-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Test Code**: Created comprehensive test cases for new features.
  * Added tests for various predicates (Address, Email, Phone, Social, Tag, Name, Relationship)
  * Added tests for command parsers and commands (FindAddress, FindEmail, FindPhone, FindSocial, FindTag, FindRelationship, Sort, Redo, RedoList)
  * Achieved high test coverage for implemented features.

* **Enhancements to existing features**:
  * Updated search functionality to enable more flexible searching
  * Modified command parsers to support new commands

* **Documentation**:
  * User Guide:
    * Added documentation for search features (find by name, phone, email, address, social, tag, relationship)
    * Added documentation for sort command
    * Added documentation for redo and redoList commands
  * Developer Guide:
    * Added implementation details and sequence diagrams for search features
    * Added implementation details and sequence diagrams for sort command
    * Added implementation details and sequence diagrams for redo and redoList commands
    * Added use cases and MSS (Main Success Scenario) for all my implemented features

* **Project management**:
  * Managed pull requests and code reviews
  * Fixed issues and bugs identified during development
  * Merged updates from team members
  
* **Technical Contributions**:
  * Designed and implemented the command history system for redo, redoList functionality
  * Created multiple predicate classes for different search criteria
  * Designed UML diagrams for all my implemented features

* **Community**:
  * PRs reviewed: #209, #190, #186, #165, #164, #163, #143, #135, #132, #119, #118, #117, #89, #86, #47, #26, #16, ...
  * Contributed to issue resolution and bug fixing
  * Collaborated with team members on feature integration, fixed several serious bugs together including search and sort on null field
  * Brainstormed several features including search, sort, command history system, redo command, find relationship, import, export, timestamp tracking

* **Reuse Declaration**:
  * I asked ChatGPT to provide some corner cases to test for finding features. 

