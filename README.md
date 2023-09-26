# Dictio
An android app made as a dictionary for learning languages.

# The idea behind the app
Since I'm studying Italian, I have a small dictionary in which I write new<br>
words that I'm supposed to learn, so I thought it would be useful if I had a<br>
phone app on which I could translate them, and add them to the dictionary<br>
right away. Boom Dictio was invented. As the name suggests it's a shorter<br>
version of the word Dictionary.<br>
<hr>

# App's possibilities
The app offers predefined empty language dictionaries which have the translate<br>
feature. Those languages can't be deleted. On the other hand we have the freedom<br>
to define our own custom dictionaries. You can choose a name, picture and you can<br>
also delete them, but they don't have the translation functionality. Inside of<br>
dictionaries you will find that you can add new foreign words and their translation<br>
as well as delete them. For the predefined languages you can translate a word and add<br>
it right away to the dictionary.<br>
<hr>

# Setting up the translator
I've used libretranslate API for my app because I haven't found any free online API's<br>
for translation(or I haven't searched long enough >:) ).
To setup libretranslate you're gonna have to download it from their github page:<br>
https://github.com/LibreTranslate/LibreTranslate<br>
and then install it using python:<br><br>
pip install libretranslate<br><br>
I had to manually find the folder of the installation and run the API using:<br><br>
python main.py<br><br>
where it did some downloads and the API was up and running on http://localhost:5000/translate<br>
<hr>

# The App itself
The icon

![icon](pictures/icon.png)

Splash Screen

![SplashScreen](pictures/1..png)

Dictionaries screen

![Dictionaries](pictures/2..png)

Adding a custom dictionary

![Custom dictionary adding](pictures/3..png)

Custom dictionaries page

![Custom dictionary page](pictures/4..png)

Translating a word in a predefined language

![Translation](pictures/5..png)

Adding a term to the dictionary

![Term adding](pictures/6..png)

Term added

![Term added](pictures/7..png)

Terms page

![Terms page](pictures/8..png)

Searching for a term

![Search](pictures/9..png)

A custom dictionaries term screen

![Custom dictionary](pictures/10..png)

Deleting a custom dictionary

![Custom dictionary deletion](pictures/13..png)

Confirmation of deletion for the custom dictionary

![Custom dictionary deletion confirmation](pictures/11..png)

Confirmation of deletion for a term

![Term deletion](pictures/12..png)
