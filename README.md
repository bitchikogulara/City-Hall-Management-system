# City Hall Citizen Management System

This project was built as a final project by **Bitchiko Gularashvili** for the **2024–2025 Spring Semester Java course** at **Université d'Évry Val d'Essonne (Université Paris-Saclay)**, in a class led by **Dr. Tarek Melliti**.

## Overview

This is a desktop application built in **Java (Swing GUI)** that simulates the operations of a city hall for managing citizens. The system supports:

- Citizen registration (births)
- Marriages and divorces
- Death registration
- Searching citizens by ID or name
- Viewing all registered citizens in tabular format
- ...

## Features

- Follows **MVC architecture** for clean separation of logic
- Uses **Java Swing** for the GUI interface
- Real-time search and filtering by citizen name
- Dropdown-based date selection for improved user experience
- Full support for data relationships: parent-child, spouse, marital status
- ...

## Project Structure

```
├── Model/
│   ├── Birth.java
│   ├── Citizen.java
│   ├── CityHall.java
│   ├── Death.java
│   ├── Divorce.java
│   ├── Female.java
│   ├── Male.java
│   └── Marriage.java

├── View/
│   ├── AddCitizenForm.java
│   ├── BirthForm.java
│   ├── DeathForm.java
│   ├── DivorceForm.java
│   ├── MarriageForm.java
│   ├── ViewCitizensForm.java
│   ├── SearchByIDForm.java
│   ├── SearchByNameForm.java
│   └── SearchCitizenMenu.java

├── Controller/
│   └── Main.java
```

## Final Notes

This project was a great way to apply what I’ve learned in Java so far. It combines data modeling, GUI development, and real-world logic in a single application.

I’m considering improving and expanding this project in the future—possibly adding data saving/loading, report generation, or more advanced validation. If you have suggestions or would like to contribute, feel free to reach out or open a pull request. I’d really appreciate any feedback or ideas!
