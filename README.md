# Android-Motors
A small application written using JetPack Compose to fetch Cars and display them on the UI.


## Architecture
The project uses an MVVM approach with the Repository Pattern

UI -> ViewModel -> Repository -> API

As this is a very basic application, the Repository in this instance is of one type where it calls the Retrofit API (and does not fetch any local data).

This project uses Jetpack Compose - addmitedly this is my very first attempt at it and it most likely is not optimised. Furthermore, the API has been written by myself in Ruby and uploaded on to Heroku because the provided API was not working. I simply mimicked the data structure and added motors on to it.

## Testing
At the moment, basic Unit Testing has been added to for the Repository but further work can be added to add more coverage to the Repo, i.e. When the API fails due to an IO/HttpException and when the API simply returns 0 motors.

## Improvements
- Add more Unit/UI tests
- Remove LiveData state within the ViewModel and use the state provided by JetpackCompose.
- Add a loading progress bar and couple that with the Loading sealed class.
- Add a more failsafe approach to the API and a generic extension function which takes an expected API result and returns either a success or a failure. This can be re-used across all API requests.
