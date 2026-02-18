# My Personal Project

## Acetylsalicylic Acid Tracker

The application I intend to design will be an application that will track the amount of acetylsalicylic acid you have consumed within a specific time frame based on your inputted body weight. A need for an application that tracks your acetylsalicylic acid intake has proven vital due to the wide range of medication that contain acetylsalicylic acid and recent studies showing the death rate attributed to NSAID/aspirin use between **21.0 and 24.8 cases/million people**, respectively, or *15.3 deaths/100,000 NSAID/aspirin users*. Additionally, up to one-third of all NSAID/aspirin deaths can be attributed to low-dose aspirin use further highling a need for this application. The application can be used by a wide range of younger teens to older acetylsalicylic acid users and will aim to help control users' intake and prevent health complications. The widescale applications and potential impact of this project drives my interest for this project.

## User Stories


I want to be able to add my own medication to the list of medication I am perscribed specifying the amount of acetylsalicylic acid it contains. 
I want to be able to view the medications I have added to the app and their acetylsalicylic acid amount.
I want to be able input my weight so the application will know how when I have take too much acetylsalicylic acid.
I want to be able to view the amount of acetylsalicylic acid I have taken in a certain time period based of my weight
I want to be warned when I am nearing a dangerous amount of acetylsalicylic acid consumed.
If a particular amount of time has passed (eg, a day), I want to be able to reset my medication, the amount of acetylsalicylic acid I have consumed, and my current status.

I want to be able to save my User with Medication List to file.
I want to be able to load a previous User with medication list from a file at the beggining of running the app or at anytime after that. 

Instructions for End User

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by creating a user and typing in a medication anme and ASAamount and pressing the add medication button at the bottom.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by, once you have added medication to the list, you can select sort by name length accending to order your medication list in accending name length order
- You can generate the another required action related to the user story "adding multiple Xs to a Y" by, once you have added medication to the list, you can select sort by name length decending to order your medication list in decending name length order
- You can locate my visual component by starting the app and it will be displayed on the wlecome screen.
- You can save the state of my application by creating a usr and pressing save user.
- You can reload the state of my application by either pressing load user at the start of the application or creating a user then pressing load user on the main menu panel. 

Phase 4: Task 2
Event Log:
Fri Mar 28 13:33:48 PDT 2025
Event log cleared.
Fri Mar 28 13:33:48 PDT 2025
Ty's Medication List Cleared.
Fri Mar 28 13:33:49 PDT 2025
Tylenol (10 mg), Added to Ty.

Phase 4: Task 3
I found an improvement in the User class when designing the UML Class diagram. I would refactor the addMedication method in the User class so that the association between the Medication and TrackerGUI class is removed. Because there is the same association between medication and user, and medication and TrackerGUI, I would, instead of creating the medication in the GUI class, I would pass the medication parameters into the add medication method in the user class so that a new medication could be instantiated in just the User class. Additionally, I would maybe add a method and field to the user class that deals with the user status so that the Status class is removed. Currently, I feel there isn't very much need for it to be represented as its own class so adding a method that woud calculate its status would help simplifying the code. 