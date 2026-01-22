The Apple Stand Project
======================

Overview
--------
This project contains:
- Android mobile app: `appleMachinery`
- Spring Boot backend: `demo`

The backend serves a REST API consumed by the Android app. All sensitive credentials are kept local for safety.

Project Structure
-----------------
apple-stand-project/
├── appleMachinery/         ← Android Studio project
├── demo/                   ← Spring Boot backend
│   ├── src/main/resources/application.sample.properties  ← Sample config, safe for GitHub
│   ├── .env                 ← Local environment variables (ignored by Git)
├── README.txt
└── .gitignore

Backend Setup (Spring Boot)
---------------------------

1. Copy the sample config
   In the `demo` folder:

   cd demo
   cp src/main/resources/application.sample.properties src/main/resources/application.properties

2. Set your credentials
   Edit `application.properties` or create a `.env` file in `demo/`:

   Example `.env`:

       DB_URL=jdbc:mysql://localhost:3306/mydb
       DB_USERNAME=root
       DB_PASSWORD=mysecret

   Reference them in `application.properties`:

       spring.datasource.url=${DB_URL}
       spring.datasource.username=${DB_USERNAME}
       spring.datasource.password=${DB_PASSWORD}

3. Run the backend:

       ./mvnw spring-boot:run

   - The server should start at http://localhost:8080
   - Make sure your database or H2 (for demo) is running.

Optional: H2 Demo Database
---------------------------
For a fully public-safe demo without a real database, replace `application.properties` with:

    spring.datasource.url=jdbc:h2:mem:testdb
    spring.datasource.driverClassName=org.h2.Driver
    spring.datasource.username=sa
    spring.datasource.password=
    spring.h2.console.enabled=true

This allows anyone to run the backend immediately without secrets.

Android App Setup
-----------------
1. Open `appleMachinery/` in Android Studio
2. Update the API base URL if needed (`http://10.0.2.2:8080` for emulator)
3. Run the app on an emulator or physical device
   

Running the Whole Project
-------------------------
1. Start the backend (`demo` Spring Boot)
2. Start the Android app (`appleMachinery`) in emulator or device
3. Enjoy the app connecting to your local backend API

Technologies
------------
- Android Studio (Java)
- Spring Boot
- REST API
- MySQL / H2 (for demo)
