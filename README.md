# Individual-Project-Rushabh-Runwal

# 🧾 Log Parser and Aggregator CLI Tool

## 📌 Objective

This project is a command-line Java application designed to parse a `.txt` log file containing various types of log entries—**APM Logs**, **Application Logs**, and **Request Logs**—and produce aggregated output for each type in separate JSON files. The system is built to be **extensible** and **robust**, capable of handling new log types and formats in the future.

---

## 🛠️ Features

- 🔍 **Log Classification**: Automatically detects log type from line contents (APM, Application, Request).
- 📊 **Aggregated Metrics**:
  - **APM Logs**: Aggregates by metric key to produce min, max, median, and average values.
  - **Application Logs**: Counts log messages by severity level (INFO, ERROR, DEBUG, WARNING).
  - **Request Logs**:
    - Aggregates response times with percentiles (50th, 90th, 95th, 99th).
    - Counts HTTP status codes grouped by category (2XX, 4XX, 5XX).
- ❌ **Fault Tolerance**: Ignores malformed or irrelevant lines.
- 🔄 **Extensible**: Designed to easily support new log types and file formats in future.

---

## 🖥️ How to Run

### 🔧 Prerequisites

- Java 17+
- Maven (for building and running tests)

### 📂 Directory Structure

```
project-root/
│
├── src/
│   ├── main/java/...         # Java source files
│   └── test/java/...         # JUnit tests
│
├── input.txt                 # Sample log file
├── apm.json                  # Output file for APM logs
├── application.json          # Output file for Application logs
├── request.json              # Output file for Request logs
├── README.md
└── pom.xml
```

### 🚀 Running the App

```bash
java -jar target/log-parser.jar --file input.txt
```

> 💡 Replace `log-parser.jar` with the actual JAR filename.

---

## 📂 Output Files

The application generates three JSON files in the root directory:

- `apm.json` — Aggregated APM metrics (CPU, memory, disk, etc.)
- `application.json` — Log count by severity (INFO, ERROR, etc.)
- `request.json` — API response time stats and status code counts

Each output file is created even if no matching logs are present.

---

## 🧪 Testing

Run unit tests using Maven:

```bash
mvn test
```

JUnit tests validate parsing, classification, and aggregation logic for each log type.

---

## 🧠 Design Overview

### 👇 Log Types Handled

| Log Type        | Detected by              | Key Fields Used                                 |
|------------------|--------------------------|--------------------------------------------------|
| APM Log          | `metric=...`             | `metric`, `value`                                |
| Application Log  | `level=INFO/ERROR/...`   | `level`                                          |
| Request Log      | `request_method=...`     | `request_url`, `response_time_ms`, `response_status` |

### 🏗️ Design Patterns Used

- **Strategy Pattern** – For flexible log parsing strategies
- **Factory Pattern** – For instantiating log handler objects
- **Singleton Pattern** – (Optional) For shared JSON writing utilities

Refer to the `Part I` PDF for class diagram and in-depth explanations.

---

## 📅 Deliverables

| Part    | Description                                                  | 
|---------|--------------------------------------------------------------|
| Part I  | Problem statement, design patterns, class diagram (PDF)      | 
| Part II | Java implementation with unit tests and output JSON files    | 

---

## 👨‍💻 Author

**Name:** Rushabh Gautan Runwal
**email:** rushabhgautam.runwal@sjsu.edu
**Course:** Individual Project - CMPE 202
**Due Date:** May 11, 2025

---

## ✅ Future Improvements

- Add support for more log types (e.g., Security, Audit)
- Accept additional formats (e.g., CSV, JSON logs)
- Enable log filtering by host, date, or severity
