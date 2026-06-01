# Softwaredesign & -kwaliteit

## Casusopdracht Bioscoop Deel 2 - Testen

Academie voor Technologie en Innovatie x | Avans Hogeschool

---

## INLEIDING

In deze casusopdracht over een bioscoopsysteem ontwikkelen we stap voor stap een steeds complexer systeem voor de aanschaf van bioscoopkaartjes. In het eerste deel van de opdracht heb je een basissysteem ontwikkelt met enkele klassen en een stuk functionaliteit voor het bepalen van prijzen en de exportfunctionaliteit. Voor het gemak hieronder nogmaals het klassendiagram.

In deze opdracht ga je automatisch testen toevoegen aan je code.

### Klassendiagram

**Order**
- `-orderId` : int
- `-StudentOrder` : boolean
- `-OrderNumber` : int, StudentOrder : boolean

`getOrder(id)` : int
`addSeatReservation(MovieTicket)` : void
`calculatePrice()` : double
`getReportExportFormat()` : void
`getReport(ExportFormat)` : void

**MovieTicket**
- `-seatNr` : int
- `-seatNr` : int
- `-isPremium` : boolean

`+MovieTicket(movieScreening : MovieScreening, isPremium/Reservation : boolean, seatFlow : int, seatNr : int)`
`+getPrice()` : double
`+isEmpty()` : boolean

**ScreeningExportFormat**
- `PLAINTEXT`
- `JSON`

**MovieScreening**
- `-dateAndTime` : LocalDateTime
- `-pricePerSeat` : double

`+MovieScreening(movie : Movie, dateAndTime : LocalDateTime, pricePerSeat : double)`
`+getPrice(pricePerSeat)` : double
`+toString()` : String

**Movie**
- `-title` : string

`+MovieTitle(title : String)`
`+addScreening(screening : MovieScreening)` : void
`+toString()` : String

---

## OPDRACHT

Je bedenkt test cases volgens de **path testing methode (met graaf)** voor de prijsberekening en programmeert deze. Laat zien hoe je aan de test cases komt. 

### Vereisten:

1. **Test Cases**: Bepaal test cases met path testing methode
2. **Graaf**: Toon je graaf voor de controleflow
3. **Documentatie**: Voeg een tekst- of Word-document toe waarin je kort beschrijft hoe je jouw set van testcases hebt bepaald met bijbehorende graaf
4. **Repository**: Neem jouw uitwerking op in Azure DevOps/GitHub/... (je bent vrij in de keuze)
5. **CI/CD Pipeline**: Maak een development pipeline die automatisch de unit testen runt
6. **Code Quality**: Integreer SonarCloud in je project en zorg dat de metrieken zoals testcoverage en cyclomatische complexiteit inzichtelijk worden

### Projectwerk:

Je werkt wederom in groepen **(4 studenten)**.