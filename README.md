# Rick-n-Morty
![Rick & Morty](/docs/banner.png)

This is a repository of app for showing the first page of Rick & Morty character (20 items). Thanks to [The Rick and Morty API](https://rickandmortyapi.com/) for providing the API

# Features
**Rick'n Morty** is a List-Detail app that contains 2 screen, List Character & Detail Character. Users can view 20 characters and open the detail by choose one of them. It will display the detailed information of the selected character like species, status, gender, etc.

## Tech stack & Open-source library
- Min SDK 24
- Kotlin based, Coroutines + Flow for asynchronous
- StateFlow
- Hilt
- Architecture
  - MVVM, Model View ViewModel
  - Clean Architecture (Presenter/View - Domain - Data)
- Retrofit2 & OkHttp3
- Gson
- Glide
- Testing
  - Espresso (UI Test)
  - MockK (mock for unit test)

## Architecture
**Rick'n Morty** is based on the Model View ViewModel architecture and Clean Architecture, which follows the [Guide to app architecture](https://developer.android.com/topic/architecture#modern-app-architecture).
![Typical App Architecture](https://developer.android.com/static/topic/libraries/architecture/images/mad-arch-overview.png)
The architecture of **Rick'n Morty** is composed by two layers, the *UI Layer* and *Data Layer*. But it also can be added with additional layer called *Domain Layer* to simplify and reuse the interactions between the UI and Data Layers. 
![Architecture Overview](/docs/architecture_overview.png)
