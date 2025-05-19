# City Hall Citizen Management System

This project was built as a final project by **Bitchiko Gularashvili** for the **2024–2025 Spring Semester Java course** at **Université d'Évry Val d'Essonne (Université Paris-Saclay)**, in a class led by **Dr. Tarek Melliti**.

## Overview

This is a desktop application built in **Java (Swing GUI)** that simulates the operations of a city hall for managing citizens. The system supports:

- Citizen registration (births)
- Marriages and divorces
- Death registration
- Searching citizens by ID or name
- Viewing all registered citizens in a table
- Data persistence using Java serialization

## Features

- Follows **MVC architecture** for clean separation of logic
- Built using **Java Swing** for GUI interface
- Real-time search and filtering by citizen name
- Dropdown-based date selection for all date inputs
- Validation: names, dates, ID relationships, age requirements (e.g. +18 for marriage)
- Persistent storage with automatic loading/saving (`cityhall.ser`)
- Extensible and modular structure

## Project Structure

```
CITY_HALL_MANAGEMENT/
├── app/
│   └── Main.java
│
├── controller/
│   ├── AddCitizenController.java
│   ├── BirthController.java
│   ├── DeathController.java
│   ├── DivorceController.java
│   ├── MarriageController.java
│   ├── SearchByIDController.java
│   ├── SearchByNameController.java
│   ├── SearchCitizenMenuController.java
│   └── ViewCitizensController.java
│
├── model/
│   ├── Birth.java
│   ├── Citizen.java
│   ├── CityHall.java
│   ├── Death.java
│   ├── Divorce.java
│   ├── Female.java
│   ├── Male.java
│   ├── Marriage.java
│   ├── UpdateListener.java
│   └── util/
│       └── CityHallStorage.java
│
├── view/
│   ├── AddCitizenForm.java
│   ├── BirthForm.java
│   ├── DeathForm.java
│   ├── DivorceForm.java
│   ├── MarriageForm.java
│   ├── SearchByIDForm.java
│   ├── SearchByNameForm.java
│   ├── SearchCitizenMenu.java
│   └── ViewCitizensForm.java
│
└── cityhall.ser
```

## How It Works

- On startup, the app checks for `cityhall.ser` to load existing data.
- If not found, it initializes an empty `CityHall` instance.
- When the application closes, all data is serialized and saved automatically.
- The GUI allows users to interact with data through forms and menus.
- All changes to citizens (birth, death, marriage, divorce) are reflected live in the view.

## Running the Project

You can run the application by executing `Main.java` in the `app` package.

## Final Notes

This project was a great way to apply what I’ve learned in Java so far. It combines data modeling, GUI development, file I/O, and real-world logic into a single coherent application.

I’m considering improving and expanding this project in the future—possibly adding report generation, or more advanced validation. If you have suggestions or would like to contribute, feel free to reach out or open a pull request. I’d really appreciate any feedback or ideas!
