# FinanTeq Zadanie Rekrutacyjne
## Wykorzystane technologie:
1. JAVA - język programowania w środowisku Intellij IDEA,
2. Framework Spring, wraz z Spring Boot,
3. JPA : Hibernate,
4. JUnit - do testowania aplikacji,
5. MySQL - relacyjna baza danych.
6. Maven - narzędzie automatyzujące budowę
7. Postman - aplikacja do testowania metod restowych

### Działanie aplikacji:
Aplikacja wykonana w stylu REST bez frontendu. Umożliwia ona prostą obsługę konta użytkownika, na zasadzie konta bankowego. Użytkownik posiada na swoim koncie środki pieniężne w różnych walutach. Na potrzeby zadania rekrutacyjnego są to złotówki (PLN), dolary amerykańskie (USD), Euro (EUR) oraz funty brytyjskie(GBP). Podstawową walutą są złotówki, użytkownik może sprzedawać i kupować waluty w oparciu o walutę podstawową PLN po aktualnym kursie pobranym z NBP.

Możliwe komendy restowe:
 1. /account/{id} - wyświetla stan konta użytkownika oraz jego dane. (GET)
 2. /sell/PLN/{waluta która kupujemy}/{ilość waluty} - umożliwia sprzedaż waluty po aktualnym kursie NBP. (PUT)
 3. /buy/PLN/{kupowana waluta}/{ilość sprzedawanych zł} - umożliwia kupno konkretnej waluty. (PUT)

### Zabezpieczenie aplikacji
Brak możliwości sprzedanie danej waluty jeśli jej użytkownik nie posiada. Brak możliwość zakupienia innej waluty niż 3 podane w zadaniu, jest możliwość rozbudowy o kolejne waluty, bez konieczności dużych zmian w kodzie. Należy dodać, tylko zmienne, lub kolejne warunki w pętli case. Podczas transakcji sprawdzane w nagłówku jest id użytkownika, z którego konta wykonywane są transakcje. zmiana stanu konta (pobranie pieniędzy oraz dodanie waluty) wykonywane są jednocześnie.
