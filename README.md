# Travel Booking System

A **Travel Booking System** with user and admin functionalities built with **Spring Boot (Backend)** and **Angular (Frontend)**. The system uses **JWT authentication**, **role-based access control**, and **Swagger** for API documentation.

---

## **Features**
- User and Admin login with **JWT authentication**
- Role-based access control
- Admin dashboard to manage bookings
- Users can book 1-day trips to multiple destinations
- API documentation with **Swagger**
- Angular frontend connected via proxy server

---

## **Technologies Used**
- **Backend:** Java, Spring Boot, Spring Security, JWT, MySQL
- **Frontend:** Angular
- **Documentation:** Swagger
- **Authentication:** JWT tokens with role-based authorization

---

## **Models**

### **User**
| Field        | Type   | Description               |
|--------------|--------|---------------------------|
| email        | String | User’s email              |
| passwordHash | String | Hashed password           |
| createdAt    | Date   | Account creation date     |
| role         | Enum   | `user` or `admin`         |

### **Profile**
| Field       | Type   | Description                |
|-------------|--------|----------------------------|
| firstname   | String | First name                 |
| lastname    | String | Last name                  |
| aadhar      | String | Unique identification      |
| phonenumber | String | Phone number               |
| city        | String | City                       |

### **Booking**
| Field        | Type   | Description                           |
|--------------|--------|---------------------------------------|
| destination  | String | Ooty, Shimoga, Goa, Mysuru           |
| rate         | Double | Price of the trip                     |
| bookingDate  | Date   | Date of booking                       |
| people       | Int    | Number of people                       |
| status       | Enum   | `pending`, `confirmed`, `cancelled`, `completed` |

---

## **Authentication & APIs**
- Admin login: `/api/login/admin`
- User login: `/api/login/user`
- Role-based access implemented using **Security Filter Chain**
- Swagger UI: `/swagger-ui.html`

---

## **Frontend**
- Built with Angular
- Connected to backend via **proxy server**  
  ```bash
  ng serve --proxy-config proxy.conf.json
  ```
- Admin dashboard accessible only with JWT containing admin role

---

## **Available Trips**
- Ooty
- Shimoga
- Goa
- Mysuru

---

## Setup Instructions
**Backend**
1. Clone the repository and cd folder-name:
```bash
git clone <repo-url>
cd <folder-name>
```
2. Configure `application.properties` with your database credentials.
3. Build and run the Spring Boot application:
```bash
cd backend
mvn spring-boot:run
```
4. Access Swagger UI: `http://localhost:8080/swagger-ui.html`

**Frontend**
1. Navigate to the frontend folder:
```bash
cd frontend
```
2. Install dependencies:
```bash
npm install
```
3. Run the Angular app with proxy:
```bash
ng serve --proxy-config proxy.conf.json
```
4. Open in browser: `http://localhost:4200`

---