## 1 • Project Purpose

**Symbalyze** is a mobile symbol identification app whose chief goal is to **showcase how well chosen software architecture patterns can tame a real AI workflow**. Rather than aiming for commercial release, the project was built as a teaching artifact: users can photograph, draw, or describe an unfamiliar symbol and receive an identification, but the real lesson is how the codebase stays clean, scalable, and maintainable by applying textbook patterns.

---

## 2 • How It Works & Tech Stack

A user begins by uploading an image, sketching a rough outline, or typing a description; that prompt is normalised and posted to a **Blackboard forum**, where three independent experts (an ML classifier, a web scraping service, and a keyword matcher) run in parallel, each proposing a result. The forum refines these partial answers into one ranked match, which is returned to the Android client and saved in a history store for logged in users. The solution therefore spans an **Android front end written in Kotlin**, a Spring Boot backend that hosts the Blackboard and adapters, TensorFlow Lite (pluggable) for the ML expert, a lightweight Python scraper service, and a relational database plus object storage for user data and images.

---

## 3 • Architecture Choices

Symbalyze combines **Blackboard** (for concurrent expert reasoning), **MVC** (cleanly separating Android views, controllers, and domain models), and **Repository** (centralising account and history data). Blackboard lets new knowledge sources be added or swapped with no ripple effects, MVC enables UI changes without touching business logic, and Repository safeguards data integrity and simplifies scaling. Alternative styles such as Pipe and Filter, Broker, PAC, and Event Driven were analysed and rejected because they added latency or complexity that the parallel expert workflow does not need. The resulting stack meets the design goals of accuracy, fast response, and future extensibility while adhering to SOLID, DRY, and KISS principles.
