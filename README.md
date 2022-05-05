# Encryptor
This demo app displays a list of cryptocurrencies with sorting function.

<img width="360" height="760" src="https://user-images.githubusercontent.com/18652094/166979790-aa3e5145-8155-4bf5-83af-2e82927e7c65.gif"/>

Initially the list is populated by reading and parsing the input.json file from assets. Changes in Room DB are observed and the data flow is unidirectional. This ensures each component (viewmodel, repository) is isolated and therefore testable independent of the UI. In addition, unit tests and instrumented tests are also written.

## Tech stacks used
- Kotlin
- Coroutines
- Flow
- MVVM with Clean Architecture
- Jetpack
  - Hilt
  - Lifecycle
  - Recyclerview
  - Room
  - Viewbinding
- Gson
- Mockito
