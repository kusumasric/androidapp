
ALERT APP
=========

The “Alert Application” provides user the ability to view the current temperature,set alerts depending upon weather,location,time. This application gathers location information using GPS sensor from a background service which runs continuously. Application interacts with Open weather API to get current weather information using that location information. Application also has Sign in and Sign up pages for the users to log in and store information in android SQLite database.

<u>Features Implemented</u>

1.  Signup/SignIn page and User Authentication
2.  Users can configure Rules
3.  Display the configured Rules
4.  Fire Notifications

<u>Enhanced Features</u>

1.  Ability to store user settings in cloud.
2.  Improve the way the App Authenticates.

<u>Major Components Used</u>

1.  Used Material Design to display Rules.
2.  Used Background service to get the location, weather information and to fire Notifications.
3.  Used SQLite Database to store User, rules.

<u> Installation</u>

1.  Download the application.
2.  Give the permission for using GPS, internet.

<u>Usage </u>

1.  Sign up or Sign in page
    User can login to the application with their name and password.

    <img src="https://raw.githubusercontent.com/kusumasric/androidapp/master/screens/Signup.png" width="200" height="300" />

2.  Home page
    The current temperature and location are displayed at the top.
    Notification Rules are displayed below.

    <img src="https://raw.githubusercontent.com/kusumasric/androidapp/master/screens/listrules.png" width="200" height="300" />

3.  Adding Rules
    On clicking the FAB at the bottom right corner you can add rules.
    To configure rules based on time, location or weather we can select a condition.

    <img src="https://raw.githubusercontent.com/kusumasric/androidapp/master/screens/select%20condition.png" width="200" height="300" />

4.  To configure Rule based on Time
    Select the Time from dropdown menu.
    Select Time and date for a Rule.

    <img src="https://raw.githubusercontent.com/kusumasric/androidapp/master/screens/datetime.png" width="200" height="300" />

5.  To configure Rules based on Location
    Select the Location from dropdown menu.
    Select the Location for a Rule.

    <img src="https://raw.githubusercontent.com/kusumasric/androidapp/master/screens/location.png" width="200" height="300" />
