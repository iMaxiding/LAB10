# Projekt: System Logowania (Spring Security MVC)

Projekt zrealizowany w ramach laboratoriów (Zadanie 1 - Część A).
Aplikacja umożliwia rejestrację i logowanie użytkowników z wykorzystaniem bezpiecznych sesji i szyfrowania haseł.

##  Technologie
* Java 17
* Spring Boot 3
* Spring Security (Authentication & Authorization)
* Thymeleaf (Silnik widoków HTML)
* SQLite (Baza danych)

##  Jak uruchomić
1. Sklonuj repozytorium
2. Otwórz w IntelliJ IDEA
3. Uruchom klasę `Project1Application`
4. Wejdź na stronę: `http://localhost:8080/login`

##  Funkcjonalności
* Rejestracja użytkowników (hasła są hashowane BCrypt)
* Logowanie formularzem (Form Login)
* Zabezpieczone podstrony (dostęp tylko dla zalogowanych)
* Obsługa błędów logowania


* dodać env i wkleić DB_URL=jdbc:sqlite:database.db
