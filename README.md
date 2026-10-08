# NBP Computer Converter

Aplikacja webowa napisana w języku **Java 21** z wykorzystaniem **Spring Boot**.

Aplikacja przelicza koszty zakupu komputerów z USD na PLN na podstawie kursów walut pobieranych z REST API Narodowego Banku Polskiego (NBP).

Po przeliczeniu dane są zapisywane w bazie danych **H2** oraz w pliku **XML**. Aplikacja udostępnia również REST API umożliwiające wyszukiwanie komputerów oraz sortowanie wyników.

---

## Technologie

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- REST API
- NBP REST API
- Jackson
- XML

---

## Dane wejściowe

Firma zakupiła trzy komputery:

| Komputer | Cena USD | Data przewalutowania |
|---|---:|---|
| ACER Aspire | 345 USD | 2026-07-03 |
| DELL Latitude | 543 USD | 2026-07-12 |
| HP Victus | 346 USD | 2026-07-15 |

Łączna wartość zakupu:

**1234 USD**

---

## NBP API

Aplikacja korzysta z REST API Narodowego Banku Polskiego:

https://api.nbp.pl

Do pobrania kursu USD wykorzystywany jest endpoint:

```text
GET https://api.nbp.pl/api/exchangerates/rates/a/usd/{data}?format=json
```

Przykład:

```text
https://api.nbp.pl/api/exchangerates/rates/a/usd/2026-07-03?format=json
```

Aplikacja pobiera średni kurs USD (`mid`) dla podanej daty.

Jeżeli dla podanej daty nie ma dostępnego kursu, np. ze względu na weekend, aplikacja wyszukuje ostatni dostępny kurs z poprzedniego dnia.

---

## Przeliczanie USD na PLN

Koszt komputera w PLN jest obliczany według wzoru:

```text
koszt_PLN = koszt_USD × kurs_USD
```

Do przechowywania wartości pieniężnych wykorzystywany jest typ `BigDecimal`.

---

## Baza danych

Aplikacja wykorzystuje bazę danych **H2** oraz **Spring Data JPA**.

Tabela zawiera:

| Pole | Opis |
|---|---|
| `name` | nazwa komputera |
| `accountingDate` | data księgowania |
| `usdCost` | koszt w USD |
| `plnCost` | koszt w PLN |

---

## Plik XML

Po zapisaniu danych do bazy aplikacja generuje plik:

```text
faktura.xml
```

Przykładowa struktura pliku:

```xml
<faktura>
    <komputer>
        <nazwa>ACER Aspire</nazwa>
        <data_ksiegowania>2026-07-03</data_ksiegowania>
        <koszt_USD>345.00</koszt_USD>
        <koszt_PLN>1290.99</koszt_PLN>
    </komputer>
    <komputer>
        <nazwa>DELL Latitude</nazwa>
        <data_ksiegowania>2026-07-12</data_ksiegowania>
        <koszt_USD>543.00</koszt_USD>
        <koszt_PLN>2064.76</koszt_PLN>
    </komputer>
    <komputer>
        <nazwa>HP Victus</nazwa>
        <data_ksiegowania>2026-07-15</data_ksiegowania>
        <koszt_USD>346.00</koszt_USD>
        <koszt_PLN>1310.61</koszt_PLN>
    </komputer>
</faktura>
```

Wartości `koszt_PLN` są zależne od kursów pobranych z API NBP.

---

# REST API

Po uruchomieniu aplikacji endpoint dostępny jest pod adresem:

```text
http://localhost:8080/computers
```

Endpoint zwraca dane w formacie JSON.

---

# Przykładowe zapytania

## 1. Pobranie wszystkich komputerów

### Request

```http
GET http://localhost:8080/computers
```

### Przykładowa odpowiedź

```json
[
    {
        "name": "ACER Aspire",
        "accountingDate": "2026-07-03",
        "usdCost": 345.00,
        "plnCost": 1290.99
    },
    {
        "name": "DELL Latitude",
        "accountingDate": "2026-07-12",
        "usdCost": 543.00,
        "plnCost": 2064.76
    },
    {
        "name": "HP Victus",
        "accountingDate": "2026-07-15",
        "usdCost": 346.00,
        "plnCost": 1310.61
    }
]
```

---

## 2. Wyszukiwanie po nazwie

Można podać pełną nazwę lub tylko jej fragment.

### Request

```http
GET http://localhost:8080/computers?name=acer
```

### Przykładowa odpowiedź

```json
[
    {
        "name": "ACER Aspire",
        "accountingDate": "2026-07-03",
        "usdCost": 345.00,
        "plnCost": 1290.99
    }
]
```

---

## 3. Wyszukiwanie po fragmencie nazwy

Przykład wyszukania komputerów zawierających fragment `asp`:

### Request

```http
GET http://localhost:8080/computers?name=asp
```

### Przykładowy wynik

```json
[
    {
        "name": "ACER Aspire",
        "accountingDate": "2026-07-03",
        "usdCost": 345.00,
        "plnCost": 1290.99
    }
]
```

Inny przykład:

```http
GET http://localhost:8080/computers?name=lat
```

Wynik:

```json
[
    {
        "name": "DELL Latitude",
        "accountingDate": "2026-07-12",
        "usdCost": 543.00,
        "plnCost": 2064.76
    }
]
```

---

## 4. Wyszukiwanie po dacie księgowania

### Request

```http
GET http://localhost:8080/computers?accountingDate=2026-07-03
```

### Przykładowa odpowiedź

```json
[
    {
        "name": "ACER Aspire",
        "accountingDate": "2026-07-03",
        "usdCost": 345.00,
        "plnCost": 1290.99
    }
]
```

---

## 5. Sortowanie po nazwie rosnąco

### Request

```http
GET http://localhost:8080/computers?sort=name
```

### Wynik

```text
ACER Aspire
DELL Latitude
HP Victus
```

---

## 6. Sortowanie po nazwie malejąco

### Request

```http
GET http://localhost:8080/computers?sort=name,desc
```

### Wynik

```text
HP Victus
DELL Latitude
ACER Aspire
```

---

## 7. Sortowanie po dacie rosnąco

### Request

```http
GET http://localhost:8080/computers?sort=accountingDate
```

### Wynik

```text
ACER Aspire     2026-07-03
DELL Latitude   2026-07-12
HP Victus       2026-07-15
```

---

## 8. Sortowanie po dacie malejąco

### Request

```http
GET http://localhost:8080/computers?sort=accountingDate,desc
```

### Wynik

```text
HP Victus       2026-07-15
DELL Latitude   2026-07-12
ACER Aspire     2026-07-03
```

---

## 9. Wyszukiwanie i sortowanie jednocześnie

Można połączyć wyszukiwanie po nazwie z sortowaniem.

### Request

```http
GET http://localhost:8080/computers?name=a&sort=name,desc
```

Aplikacja:

1. wyszukuje komputery, których nazwa zawiera `a`,
2. następnie sortuje wyniki malejąco po nazwie.

---

## 10. Wyszukiwanie po nazwie i dacie

### Request

```http
GET http://localhost:8080/computers?name=acer&accountingDate=2026-07-03
```

### Przykładowa odpowiedź

```json
[
    {
        "name": "ACER Aspire",
        "accountingDate": "2026-07-03",
        "usdCost": 345.00,
        "plnCost": 1290.99
    }
]
```

---

## 11. Wyszukiwanie, filtrowanie i sortowanie

Wszystkie dostępne parametry można połączyć.

### Request

```http
GET http://localhost:8080/computers?name=a&accountingDate=2026-07-03&sort=name,desc
```

Zapytanie:

- wyszukuje komputery zawierające `a` w nazwie,
- ogranicza wyniki do daty `2026-07-03`,
- sortuje wyniki po nazwie malejąco.

---

# Parametry endpointu

Endpoint:

```text
GET /computers
```

obsługuje następujące parametry:

| Parametr | Opis | Przykład |
|---|---|---|
| `name` | fragment nazwy komputera | `name=acer` |
| `accountingDate` | data księgowania | `accountingDate=2026-07-03` |
| `sort` | pole i kierunek sortowania | `sort=name,desc` |

### Dozwolone pola sortowania

```text
name
accountingDate
```

### Kierunki sortowania

```text
asc
desc
```

Jeżeli kierunek nie zostanie podany, domyślnie stosowane jest sortowanie rosnące.

---

# Uruchomienie aplikacji

## Wymagania

- Java 21
- Maven
- IntelliJ IDEA lub inne środowisko obsługujące projekty Maven

## IntelliJ IDEA

1. Otwórz projekt w IntelliJ IDEA.
2. Ustaw Java 21 jako Project SDK.
3. Załaduj zależności Maven.
4. Uruchom główną klasę aplikacji Spring Boot.

Po uruchomieniu aplikacja będzie dostępna pod adresem:

```text
http://localhost:8080
```

Endpoint komputerów:

```text
http://localhost:8080/computers
```

## Maven

Aplikację można uruchomić również z terminala:

```bash
mvn spring-boot:run
```