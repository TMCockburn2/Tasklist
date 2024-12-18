# Android Task list App
## Running the app
The app should have no issues loading from emulator or device. The app should load in at the main task screen.
This project is a starting point in your Android interview journey with DoorDash. At the first Phone screen stage you will start off by adding the initial functionality here and continue adding to it throughout the entire interview process. So keep the progress saved as you move forward.

A few key points to in the project:
1. UI implementation
    * Jetpack compose is used for the user interfaces. Majority of the composables are in the various UI screen files, with some logic in MainActivity.kt. This is mainly 
    * for setting up the drawer container and top bar.
    * There are 3 screens, task screen, deleted items and settings. Settings is blank as I was going to initially use it to toggle dark mode, but figured it was better to utilize the phone settings for that
    * 
2. Dependency injection
    * I used Dagger-Hilt for some dependency injection for the view model and room database. Ultimately if there were network connections involved, I'd probably make a separate
    * network module. With the various screen traversals, I didn't want there to be duplicates of view models or dbs, as room dbs can be quite heavy
3. Decisions/trade offs
   * For the sake of time, I had to mitigate a few features I would've liked to add (unfortunately I was pretty limited in the 5 day range) animations, placeholder
   * API and supporting client/repository classes, etc. The most important one would be a pop up window prompting the user to enter the task and description; that way the textfields aren't taking up
   * space on the screen. I had some issues with the coroutine dependencies for room, so I ‘bypassed’ them by making the db calls in the 
   * dao regular functions rather than suspend. This resulted in needing to call viewmodelscope.launch(dispatchers.IO) in order to do the db calls in a separate thread (within view model methods). 
   * In regular cases, all of the db calls would’ve been suspend functions. I decided to use a drawer to navigate between the current task list and deleted items. Initially I had planned on making 
   * two separate tables for current and deleted items, but I wanted to simplify it to just a single table. Therefore, I added a boolean flag in the db so the UI and view model can manipulate the data as needed, 
   * rather than having to create and reference a second table. Using livedata, it’s easy to “delete” them and restore them as it updates instantly.






