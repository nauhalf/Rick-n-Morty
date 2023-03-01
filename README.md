# Rick-n-Morty
![Rick & Morty](/docs/banner.webp)

This is a repository of app for showing the first page of Rick & Morty character (20 items). Thanks to [The Rick and Morty API](https://rickandmortyapi.com/) for providing the API

# Features
**Rick n Morty** is a List-Detail app that contains 2 screen, List Character & Detail Character. Users can view 20 characters and open the detail by choose one of them. It will display the detailed information of the selected character like species, status, gender, etc.

## Tech stack & Open-source library
- Min SDK 24
- Kotlin based, Coroutines + Flow for asynchronous
- StateFlow
- Hilt
- Architecture
  - MVVM, Model View ViewModel
  - Clean Architecture (View - Domain - Data)
- Retrofit2 & OkHttp3
- Gson
- Glide
- Testing
  - Espresso (UI Test)
  - MockK (mock for unit test)
