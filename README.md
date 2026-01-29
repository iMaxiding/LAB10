# Projekt: 
Projekt jest backendową aplikacją webową stworzoną w technologii Spring Boot, której celem jest demonstracja działania protokołu HTTP, uwierzytelniania i autoryzacji użytkowników oraz podstawowych mechanizmów bezpieczeństwa aplikacji internetowych.
Aplikacja udostępnia API umożliwiające rejestrację i logowanie użytkowników oraz zarządzanie zasobami np. notatkami. 
Dostęp do danych jest ograniczony do uwierzytelnionego użytkownika, zgodnie z zasadami kontroli dostępu.

## Struktura projektu
Projekt posiada następującą strukturę logiczną:

- controller – obsługa żądań HTTP oraz nawigacji pomiędzy widokami (Spring MVC)
- service – logika biznesowa aplikacji
- repository – komunikacja z bazą danych
- model – encje JPA reprezentujące strukturę bazy danych
- dto – obiekty transferowe wykorzystywane w formularzach i walidacji danych
- security – konfiguracja Spring Security, uwierzytelnianie sesyjne i autoryzacja

##  Struktura projektu
* Java 17
* Spring Boot 
* Spring Security (Authentication & Authorization)
* SQLite (Baza danych)

##  Jak uruchomić
1. Sklonuj repozytorium
2. Otwórz w IntelliJ IDEA
3. Utwórz plik `.env` na podstawie `.env.example` 
4. Uruchom klasę `Project1Application`
5. Wejdź na stronę: `https://localhost:8443/login`

##  W projekcie zaimplementowano:

- rejestrację i logowanie użytkowników,
- uwierzytelnianie sesyjne przy użyciu Spring Security,
- autoryzację dostępu do zasobów użytkownika,
- zarządzanie notatkami przypisanymi do zalogowanego użytkownika,
- migracje bazy danych (Flyway),
- podstawowe zabezpieczenia aplikacji webowej,
- testy jednostkowe i integracyjne,
- konfigurację CI w GitHub Actions.

