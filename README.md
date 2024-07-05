# Project Giggle Gazette API

Welcome to Project Giggle Gazette API! This project is a personal newspaper web app built using Spring Boot (3.3.1), following a microservices architecture. The backend consists of several services that work together to handle user data, article management, and service discovery.

---

## Project Structure

Project Giggle Gazette is organized into five main services:

1. **User Service**: Manages user data, roles, profiles, and permissions.
2. **Article Service**: Manages articles, categories, comments, images, and subcategories.
3. **Service Registry (Eureka Server)**: Registers and discovers services.
4. **Config Server**: Manages configuration for all services.
5. **API Gateway**: Handles routing and security for service endpoints.

---

## Services Overview

### 1. User Service
The User Service handles user-related operations and profile management. It exposes the following endpoints:

- **User Endpoints**:
    - `GET /users`: Retrieve all users.
    - `GET /users/{id}`: Retrieve a user by ID.
    - `GET /users/email/{email}`: Retrieve a user by Email.
    - `POST /users`: Create a new user.
    - `PUT /users/{id}`: Update a user by ID.
    - `DELETE /users/{id}`: Delete a user by ID.
- **Role Endpoints**:
    - `GET /roles`: Retrieve all roles.
    - `GET /roles/{id}`: Retrieve a role by ID.
    - `POST /roles`: Create a new role.
    - `PUT /roles/{id}`: Update a role by ID.
    - `DELETE /roles/{id}`: Delete a role by ID.
- **Profile Endpoints**:
    - `GET /profiles`: Retrieve all profiles.
    - `GET /profiles/{id}`: Retrieve a profile by ID.
    - `POST /profiles`: Create a new profile.
    - `PUT /profiles/{id}`: Update a profile by ID.
    - `DELETE /profiles/{id}`: Delete a profile by ID.
- **Permission Endpoints**:
    - `GET /permissions`: Retrieve all permissions.
    - `GET /permissions/{id}`: Retrieve a permission by ID.
    - `POST /permissions`: Create a new permission.
    - `PUT /permissions/{id}`: Update a permission by ID.
    - `DELETE /permissions/{id}`: Delete a permission by ID.

#### Models
- **User**: Stores user details.
- **Role**: Defines roles for users.
- **Profile**: Stores personal information of users.
- **Permission**: Manages user permissions.

---

### 2. Article Service
The Article Service manages articles, categories, comments, images, and subcategories. It exposes the following endpoints:

- **Article Endpoints**:
    - `GET /articles`: Retrieve all articles.
    - `GET /articles/{id}`: Retrieve an article by ID.
    - `POST /articles`: Create a new article.
    - `PUT /articles/{id}`: Update an article by ID.
    - `DELETE /articles/{id}`: Delete an article by ID.
- **Category Endpoints**:
    - `GET /categories`: Retrieve all categories.
    - `GET /categories/{id}`: Retrieve a category by ID.
    - `POST /categories`: Create a new category.
    - `PUT /categories/{id}`: Update a category by ID.
    - `DELETE /categories/{id}`: Delete a category by ID.
- **Comment Endpoints**:
    - `GET /comments`: Retrieve all comments.
    - `GET /comments/{id}`: Retrieve a comment by ID.
    - `GET /comments/article/{articleId}`: Retrieve comments by article ID.
    - `POST /comments`: Create a new comment.
    - `PUT /comments/{id}`: Update a comment by ID.
    - `DELETE /comments/{id}`: Delete a comment by ID.
- **Image Endpoints**:
    - `GET /images`: Retrieve all images.
    - `GET /images/{id}`: Retrieve an image by ID.
    - `GET /images/article/{articleId}`: Retrieve images by article ID.
    - `POST /images`: Upload a new image.
    - `PUT /images/{id}`: Update an image by ID.
    - `DELETE /images/{id}`: Delete an image by ID.
- **SubCategory Endpoints**:
    - `GET /subcategories`: Retrieve all subcategories.
    - `GET /subcategories/{id}`: Retrieve a subcategory by ID.
    - `POST /subcategories`: Create a new subcategory.
    - `PUT /subcategories/{id}`: Update a subcategory by ID.
    - `DELETE /subcategories/{id}`: Delete a subcategory by ID.

#### Models
- **Article**: Represents newspaper articles.
- **Category**: Organizes articles into categories.
- **Comment**: Manages user comments on articles.
- **Image**: Stores images related to articles.
- **SubCategory**: Further organizes articles within categories.

---

### 3. Service Registry (Eureka Server)
The Eureka Server is responsible for registering and discovering services. All services register themselves with Eureka, allowing for easy service discovery and load balancing.

---

### 4. Config Server
The Config Server handles external configurations for all services. This ensures that configuration changes can be managed centrally and applied dynamically across the entire system.

---

### 5. API Gateway
The API Gateway (Spring Cloud Gateway) routes requests to the appropriate services and handles security. It serves as the single entry point for all client requests.

- **Server Port**: `8060`

---

## Security
Project Giggle Gazette will use JWT tokens for authentication in future updates. Users will need to log in to receive a JWT token, which must be included in the `Authorization` header as `Bearer <token>` for subsequent requests to protected endpoints.

---

## Accessing Endpoints

Users can access all endpoints through the API Gateway using `localhost` and port `8060`. The API Gateway handles routing and security for service endpoints, making it the single entry point for all client requests.

---

## Getting Started

### Prerequisites
- Java 17
- Spring Boot 3.3.1
- Eureka Server
- Spring Cloud Gateway
- MongoDB (for storing user and article data)

### Running the Project
1. **Clone the repository**:
    ```bash
    git clone <repository-url>
    ```

2. **Start the Eureka Server**:
    ```bash
    cd service-registry
    ./mvnw spring-boot:run
    ```

3. **Start the Config Server**:
    ```bash
    cd config-server
    ./mvnw spring-boot:run
    ```

4. **Start the User Service**:
    ```bash
    cd user-service
    ./mvnw spring-boot:run
    ```

5. **Start the Article Service**:
    ```bash
    cd article-service
    ./mvnw spring-boot:run
    ```

6. **Start the API Gateway**:
    ```bash
    cd api-gateway
    ./mvnw spring-boot:run
    ```

---

## Contributing
Contributions are welcome! If you have suggestions for improvements, feel free to open an issue or create a pull request. Please ensure your code follows the project's coding standards and includes appropriate tests.

### Steps to Contribute
1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes.
4. Commit your changes (`git commit -m 'Add new feature'`).
5. Push to the branch (`git push origin feature-branch`).
6. Open a pull request.

---

## License
Project Giggle Gazette is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

---