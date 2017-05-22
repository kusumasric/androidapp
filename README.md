```html
<h1>ALERT APP</h1>
<br>
<p>
The <q>Alert Application</q> provides user the ability to view the current temperature,set alerts depending upon weather,location,time.
This application gathers location information using GPS sensor from a background service which runs continuously. Application interacts with Open weather API to get current weather information using that location information.

Application also has Sign in and Sign up pages for the users to log in and store information in android SQLite database.

</p>

<p>
<u>Features Implemented</u><br>
<ol>
  <li>Signup/SignIn page and User Authentication</li>
  <li>Users can configure Rules</li>
  <li>Display the configured Rules</li>	
  <li>Fire Notifications</li>	
</ol> 
<br></p>
<p><u>Enhanced Features</u><br></p>
<ol>
  <li>Ability to store user settings in cloud.</li>
  <li>Improve the way the App Authenticates.</li>  
</ol> 

<p><u>Major Components Used</u></p>
<ol>
   <li>Used Material Design to display Rules.</li>
   <li>Used Background service to get the location, weather information and to fire Notifications.</li>	
   <li>Used SQLite Database to store User, rules. </li>	

</ol>
<p><u> Installation</u></p>

<ol>
  <li>Download the application.</li>
  <li>Give the permission for using GPS, internet.</li>
</ol> <br>
<p><u>Usage </u></p> 
<img src="https://raw.githubusercontent.com/kusumasric/androidapp/master/screens/Signup.png" width="200" height="300"/>
		

<ol>
  <li>
  <p> Sign up or Sign in page<br><br>User can login to the application with their name and password.</p>
  <p>
  <img src="https://raw.githubusercontent.com/kusumasric/androidapp/master/screens/Signup.png" width="200" height="300"/>
  </p>
  </li>
	<li>
		<p>
		Home page
		<br><br>
		The current temperature and location are displayed at the top.<br>
		Notification Rules are displayed below.

		</p>
		<p >
        	<img src="https://raw.githubusercontent.com/kusumasric/androidapp/master/screens/listrules.png" width="200" height="300"/>

		</p>
	</li>


	<li>
		<p>
		Adding Rules<br><br>

		On clicking the FAB at the bottom right corner you can add rules.<br>
		To configure rules based on time, location or weather we can select a condition.

		</p>

		<p>
        	<img src="https://raw.githubusercontent.com/kusumasric/androidapp/master/screens/select condition.png" width="200" height="300"/>

		</p>
	</li>

	<li>
		<p>
		To configure Rule based on Time <br><br>

		Select the Time from dropdown menu.<br>
		Select Time and date for a Rule.

		</p>
		<p>
      		<img src="https://raw.githubusercontent.com/kusumasric/androidapp/master/screens/datetime.png" width="200" height="300"/>

		</p>
	</li>

	<li>
		<p>
		To configure Rules based on Location<br><br>

		Select the Location from dropdown menu.<br>
		Select the Location for a Rule.

		</p>
		<p>
        	<img src="https://raw.githubusercontent.com/kusumasric/androidapp/master/screens/location.png" width="200" height="300"/>

		</p>
	</li>


	<li>
		<p>
		To configure Rules based on Weather<br><br>

		Select the minimum temperature and maximm temperature values<br>
		</p>
		<p>
        	<img src="https://raw.githubusercontent.com/kusumasric/androidapp/master/screens/weather.png" width="200" height="300"/>

		</p>
	</li>

	<li>
		<p>
		Rules in List View<br><br>

		All the Rules are displayed below in a list.<br>
		</p>
		<p >
        	<img src="https://raw.githubusercontent.com/kusumasric/androidapp/master/screens/listrules.png" width="200" height="300"/>
		</p>
	</li>



	<li>
		<p>
		Notifications <br><br>

		Notifications gets displayed when specified condition gets satisfied.

		</p>
		<p >
       		<img src="https://raw.githubusercontent.com/kusumasric/androidapp/master/screens/messages.png" width="200" height="300"/>

		</p>
	</li>
</ol>
```
