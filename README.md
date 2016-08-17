# CurioMobile-Android-App

Copyright (c) | 2016 | Shuang Wu | University of Waterloo 

1.OS: OS X El capitan
2.JDK: JAVA SE 1.8
3.IDE: Android Studio Marshmallow 6.0 
The layout is designed for Nexus 6 

This is an anroid app which serves as the mobile version of the website http://test.crowdcurio.com/
The app will perform URL requests to the CrowdCurioto get info shown on the website and display it in native phone interface.The request is a GET request, it will be a JSON array which got returned to server.

1)Description:
		
Couple things that need more explanation:

1.The detailed view contains “BRIEF” which is the short description of the project, the “ABOUT” which is the longer description, the “RESEARCH QUESTION” is the contribution questions and finally the “TEAM” info.

2. The master list view contains all the current projects, there are several projects from the api url which doesn’t have name, My decision is to still show them but name them as [un-named project]

3. I implemented the advanced search, so when user inputs, it has to click on the search button in order to search. The search might take a little while. Also, the clear button will return the entire list of projects. When you search some word, and then click into the project, when you return back to master view, I let the master list reload so it will show the newest entire list without the filter.There is a button on top of the refresh button, this is the button for choosing search field. Without choosing, the default field is the title of the project, if you click on the button, there will be a dialog popping up asking you for choice. When you choose that field, then search, it will search that particular field. (The search field resets when switching orientation.)


4.I basically just modify the template code provided by master/detailed template, so I didn’t refactor any names such as dummy item dummy content and so on… Also, since the detailed view has a collapsing top bar, so my header image will disappear when I scroll down and will appear again when i scroll to the top again. Jeff said it is fine:)

5. Loading Indicator: it shows up in the beginning of the app, when we get into detailed view, when we get back to master view, when we switch orientation, when we press refresh for both views.

	   
4)license and credit
1. http://www.flaticon.com/free-icon/circular-arrow_109450#term=REFRESH&page=1&position=20
2. https://developer.android.com/reference/java/net/HttpURLConnection.html
https://developer.android.com/reference/android/os/AsyncTask.html
http://stackoverflow.com/questions/22461663/convert-inputstream-to-jsonobject
For my GET request I used the code from the links Jeff provided in the project description.
3.for loading indicator i looked at this link :http://stackoverflow.com/questions/9814821/show-progressdialog-android.  For runnable code, i looked at this :http://stackoverflow.com/questions/27899091/android-show-dialog-when-runnable-with-progress-dialog-finishes
4.For getting the string from search view, I looked at this: http://stackoverflow.com/questions/20712247/how-to-get-input-from-searchview-to-textview
5. http://www.flaticon.com/free-icon/search_115766#term=search&page=1&position=62