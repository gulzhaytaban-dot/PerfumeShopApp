# Perfume Shop App

A shopping app for one category: perfume. Users can browse perfumes, view details, and add them to favorites/cart.

## Group Members
- Ahemeili Nuerjuma
- Aisha Zholdybayeva
- Talshyn Altynbekkyzy
- Lashyn

## Main Features
- Perfume list with search
- Perfume detail screen (navigation with perfumeId)
- Favorites / cart screen
- MVVM architecture with ViewModel + StateFlow
- Navigation Compose
- Home screen: 2-column grid of perfume cards (PerfumeCard, HomeScreen) with data from PerfumeViewModel (StateFlow)
- Cart screen: add to cart, quantity +/-, remove item, total price (CartViewModel with StateFlow)
- Real perfume data from DummyJSON API (Retrofit + PerfumeRepository)

## Tech
Kotlin, Jetpack Compose, MVVM, Navigation Compose,  Retrofit, Gson

## Local storage (Room)
- `CartEntity` — cart row (perfume data + quantity)
- `CartDao` — insert, getAll (Flow), getById, updateQuantity, delete, clear
- `AppDatabase` — Room database `perfume_shop.db`
- `CartRepository` — maps entities to `CartItem` and exposes `items: Flow`
