# OnARoll

OnAroll is a Firebase powered dice game built for Android

# Game Overview

  - Two Players. Two Buttons. One Dice. 
  - Each player rolls the dice, if the dice is not equal to one, the number rolled gets added to the users current score. 
  - If the player rolls a one, the current score is set to 0 and the next player gets to roll. 
  - If the player doesn’t roll a one and the player presses the “hold” button, the current score gets added to the players overall score and the other player gets to roll.
  -   The first person to reach a score of 100 wins.

### Tech

OnARoll uses a number of open source projects to work properly:

* [Firebase] - Realtime backend-as-a-service provided by Google
* [FirebaseUI] - Optimized UI components for Firebase
* [MaterialDialogs] - A beautiful, fluid, and customizable dialogs API. 
* [ButterKnife] - Field and method binding for Android views 
* [EventBus] - EventBus is a publish/subscribe event bus for Android and Java.
* [Glide] - An image loading and caching library for Android
OnARoll itself is open source as well, with a [public repository][roll] on GitHub.

### Installation

OnARoll has a minSDKVersion of 21

To run, just clone the project in a local development environment.

### Todos

 - Upload to Google Play Store

License
----

MIT

[roll]: <https://github.com/TheVanillaBean/OnARoll>