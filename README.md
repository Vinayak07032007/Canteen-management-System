# 🍽️ CANTEEN MANAGEMENT SYSTEM  
[![Java](https://img.shields.io/badge/Java-17-blue?logo=java)](https://www.oracle.com/java/)  
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql)](https://www.postgresql.org/)  
[![Maven](https://img.shields.io/badge/Maven-3.8.6-orange?logo=apachemaven)](https://maven.apache.org/)  
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

---

The *CANTEEN MANAGEMENT SYSTEM* is a *console-based Java application* using **JDBC** and **PostgreSQL**.  
It enables users to manage menu items and orders through a simple and intuitive **menu-driven interface**.  
It supports *multi-threading* and real-time *order placement and tracking*.

---

# ✨ FEATURES

- 🍜 *ADD MENU ITEM*: Insert new food items with price and stock.
- 📋 *VIEW MENU*: Display all items in the canteen with their prices and stock.
- 🛒 *PLACE ORDER*: Students can place food orders.
- 📦 *VIEW ORDERS*: Shows order history with quantities and total price.
- ⏳ *BACKGROUND THREAD*: Simulates backend tasks like order processing.

---

# 🛠 TECHNOLOGIES USED

- **Core Java**: Console application, OOP, and multithreading.  
- **JDBC (Java Database Connectivity)**: For interaction with PostgreSQL.  
- **PostgreSQL**: For storing menu and order data.  
- **Maven (optional)**: For dependency and build management.

---

# 📦 DEPENDENCIES

If you're using Maven, include the following dependency:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.6.0</version>
</dependency>
```


---


# 🖥 PREREQUISITES

**Make sure the following tools are installed:**

-**✅ Java JDK 17+**
-**✅ PostgreSQL 15+**
-**✅ Maven 3.8+ (optional if you manage dependencies manually)**

---


# 🗄 DATABASE SETUP
**Create a PostgreSQL database named canteen with the following tables:**

```sql

CREATE TABLE menus (
    fooditem VARCHAR(100) PRIMARY KEY,
    price NUMERIC,
    stock INT
);

CREATE TABLE foodorders (
    order_id SERIAL PRIMARY KEY,
    student_name VARCHAR(200) NOT NULL,
    item_name VARCHAR(300) NOT NULL,
    quantity INT NOT NULL,
    total_price NUMERIC NOT NULL
);

```
---

# 📂 PROJECT STRUCTURE

```bash
com/vinayak/
├── DBConnection.java       # Manages database connection
├── MenuItem.java           # Contains menu logic
├── Order.java              # Handles ordering logic
├── Task.java               # Simulates background thread
└── App.java                # Main class with menu UI
```

---

# 🚀 HOW TO RUN
-*Clone this repository or download the files.*

-*Open the project in your IDE (IntelliJ, Eclipse, or VS Code).*

-*Update the database credentials in DBConnection.java:*

```java

private static final String URL = "jdbc:postgresql://localhost:5432/canteen";
private static final String USER = "postgres";
private static final String PASSWORD = "your_password";
```


---

# Compile and run:

```bash

javac App.java
java App
Use the on-screen menu to manage the canteen system!
```
---

# 📬 CONTACT
-**Developer:** Vinayak Sonawane
-**Email:** vinayaksonawane377@gmail.com
-**GitHub:** Vinayak07032007

---

# 📷 SCREENSHOT


<img width="1920" height="1080" alt="Screenshot (123)" src="https://github.com/user-attachments/assets/ae9cdb4c-9b9a-4d92-82b3-06dd033c48e2" />

