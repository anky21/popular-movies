The Popular Movies App
========

This app allows users to browse movies that are sorted by **Now Playing**, **Popularity** and **Ratings**. Users can also have a collection of movies that they marked as **Favourite**.

Pre-requisites
--------------

- Android SDK v23
- Android Build Tools v23.0.2
- Android Support Repository v24.2.1

Getting Started
---------------

This project uses the Gradle build system. To build this project, use the
"gradlew build" command or use "Import Project" in Android Studio.

### The Movie Database API Key is required.

In order for this app to function properly as of December 16th, 2016 an API key for themoviedb.org must be included with the build.

Please obtain a key via the following [instructions](https://www.themoviedb.org/faq/api), and include the unique key for the build by replacing the following line in '[USER_HOME]/.app/build.gradle'

`it.buildConfigField 'String', 'OPEN_MOVIE_API_KEY', "\"API_KEY\""`
