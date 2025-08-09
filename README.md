# Spring Boot URL Shortener Service

This is a complete URL shortener service built with Spring Boot. It provides an API to convert a long URL into a unique, short code and redirects users from the short URL to the original long URL.

This project is built using a professional 3-tier architecture: Controller, Service, and Repository.

## Technologies Used

* **Framework**: Spring Boot 3
* **Language**: Java 21
* **Data Persistence**: Spring Data JPA
* **Database**: MySQL
* **Build Tool**: Maven

## Project Setup and Running

### 1. Prerequisites

* Java 17 or later installed.
* Maven installed.
* MySQL Server running.

### 2. Clone the Repository

```bash
git clone [https://github.com/your-username/your-repo-name.git](https://github.com/your-username/your-repo-name.git)
cd your-repo-name
```

### 3. Configure the Database

This project uses MySQL.

* The application will automatically create a database named `url_shortener_db` if it doesn't exist.
* You must provide your MySQL password. It is highly recommended to use environment variables for this.

Open `src/main/resources/application.properties` and ensure the password line is set to read from an environment variable:

```properties
spring.datasource.password=${DB_PASSWORD}
```

Then, set the environment variable in your terminal before running the application:

* **macOS/Linux:**

    ```bash
    export DB_PASSWORD="your_mysql_password"
    ```

* **Windows (CMD):**

    ```bash
    set DB_PASSWORD=your_mysql_password
    ```

### 4. Run the Application

You can run the application using the Maven wrapper:

```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`.

## API Endpoints

### 1. Create a Short URL

* **URL:** `/api/v1/shorten`
* **Method:** `POST`
* **Description:** Takes a long URL and returns a generated short URL.

### 2. Redirect to Long URL

* **URL:** `/{shortCode}`
* **Method:** `GET`
* **Description:** Redirects to the original long URL associated with the `shortCode`.

## Testing with Postman

This API was tested using **Postman**. Here’s how you can test the endpoints:

### 1. Test: Create a Short URL

1.  **Method**: `POST`
2.  **URL**: `http://localhost:8080/api/v1/shorten`
3.  **Headers**:
    * `Content-Type`: `application/json`
4.  **Body**: Go to the `Body` tab, select `raw`, and choose `JSON`. Enter the following:

    ```json
    {
        "longUrl": "[https://www.geeksforgeeks.org/introduction-to-spring-boot/](https://www.geeksforgeeks.org/introduction-to-spring-boot/)"
    }
    ```

5.  **Send Request**.
6.  **Expected Response**:
    * **Status**: `200 OK`
    * **Body (Text)**: A short URL, for example: `http://localhost:8080/fG7hTz1`

### 2. Test: Redirect

1.  **Copy** the short URL from the response of the previous test (e.g., `http://localhost:8080/fG7hTz1`).
2.  **Open your web browser** (Chrome, Firefox, etc.).
3.  **Paste** the short URL into the address bar and press Enter.
4.  **Expected Result**: The browser should instantly redirect you to the original long URL (`https://www.geeksforgeeks.org/introduction-to-spring-boot/`).
