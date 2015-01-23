Android API Level: 20
Emulator setup:
Device: Nexus 5
Target: Android 4.4.2 - API Level 19
CPU/ABI: ARM (armeabi-v7a)
Skin: WVGA800
Every other settings leave at default.
File containing flights should be named flightData.csv
File containing clients should be named clientData.csv
File containing admins should be named adminData.csv
Upload files (except password.txt) should be pushed into /data/data/b07.PIII.featuredflights/app_UPLOAD_HERE
Login information for first time use:
Username: admin@hotmail.com
Password: password
password.txt should be pushed to /data/data/b07.PIII.featuredflights/app_Data
password.txt is in the format UserType,email,password where UserType should be replaced with either Admin or Client, depending on the account type. It is case sensitive. email is the email of the client and is used as the username. password is what the password of the account should be.
Login with the username and password given above. Once you login, you will be at General Options. In general options, you will have Admin Options at the top right button. Over here, you have the option to upload all files into the system. Please do this to use other users!
General Options has 3 options, all can be used by anyone. Edit info is straight forward to use. Search Flight will take you to another activity where you can input the given information to search for flights or itinerary. When searched for itinerary, you can click on them to book them. View Booked Flights will give you a list of all flights the current user has booked.
Please follow all the format given in the app, such as picking a departure date, should be in yyyy-mm-dd. We have provided the format within the app.
