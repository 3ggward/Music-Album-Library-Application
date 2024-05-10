# Music Album library App

## What will the application do?
This application will act as a **music album library/cataloger**, allowing users to log the albums they have listened to, or own, to their personal library. You can enter information about the albums, like album name, artist, genre, and date released. Possible features include rating albums on a scale from 1 to 5 stars or marking down albums as your *favourites*.

## Who will use it?
This application is useful for anyone who enjoys music, and wish to track the albums they have listened to, wish to listen to, or perhaps physically own, and rate them. 

## Why is this project of interest to you?
Music has always been a core interest of mine, and I would like to see to be able to track and rate all the albums I have listened to, and keep a list want to listen to. This library app will allow me to do so.

## User Stories

- As a user, I want to be able to add an album to my library, and specify the name, artist, genre, and date released
- As a user, I want to be able to edit the name, artist, genre, and date released of an album from my library
- As a user, I want to be able to remove an album from my library
- As a user, I want to be able to view the list of albums in my library
- As a user, I want to be able to mark an album as one of my *favourites*
- As a user, I want to be able to select an album in my library and rate it on a scale of one to five stars
- As a user, I want to be able to save my album catalogue to file (if I so choose)
- As a user, I want to be able to be able to load my album catalogue from file (if I so choose)

## Instructions for Grader

- You can generate the first required action related to the user story "adding multiple albums to my library", by typing the name, artist, date, and genre of the album you want to record in the respective textboxes at the bottom of the screen, then press the add button.
- You can generate the second required action related to the user story "viewing albums in my library", by adding albums, then selecting the albums on your library pane on the left side of the screen
- You can edit and view the details of the selected album on the right side of the screen, displaying info of selected album, and allowing you to edit by typing what you want the change to be and pressing the respective edit button.
- You can locate my visual component by toggling an album as a favourite, which will then display a star on the info pane for the favourited album
- You can save the state of my application by pressing the save button at the bottom of the screen
- You can reload the state of my application by pressing the load button at the bottom of the screen

### Phase 4: Task 2
- Wed Nov 29 21:02:36 PST 2023 
- catalogue, myCatalogue, was created.
- Wed Nov 29 21:02:52 PST 2023
- album, In Rainbows, was created. 
- Wed Nov 29 21:02:52 PST 2023 
- album, In Rainbows, was added to myCatalogue. 
- Wed Nov 29 21:03:20 PST 2023 
- album, For Lovars, was created. 
- Wed Nov 29 21:03:20 PST 2023 
- album, For Lovars, was added to myCatalogue. 
- Wed Nov 29 21:03:24 PST 2023 
- album, In Rainbows, set artist to Radiohead 
- Wed Nov 29 21:03:28 PST 2023 
- album, In Rainbows, was rated 5 stars. 
- Wed Nov 29 21:03:30 PST 2023 
- album, In Rainbows, was set as favourite. 
- Wed Nov 29 21:03:34 PST 2023 
- album, For Lovars, set name to For Lovers 
- Wed Nov 29 21:03:37 PST 2023 
- album, For Lovers, changed date to 2/11/2004 
- Wed Nov 29 21:03:42 PST 2023 
- album, For Lovers, set genre to Shibuya-kei 
- Wed Nov 29 21:03:43 PST 2023 
- album, For Lovers, was rated 4 stars. 
- Wed Nov 29 21:03:57 PST 2023 
- album, For Lovers, was removed from myCatalogue.

### Phase 4: Task 3
- If I had more time to work on this project, I would work on implementing better exception handling throughout, but specifically with the methods concerning date. Currently, for my date setting methods, for year, month, and day, if an invalid number (too large of a day/month, or invalid month/day), or type value other than an integer is inputted, an exception will be thrown. Since making these wrong inputs is quite easy, handling these exceptions within the gui and album methods would be beneficial to the quality of using the application.
