# Event Booking System

A full-stack Event Booking System developed using **Spring Boot**, **React**, **MongoDB**, and **JWT Authentication**. The system allows users to browse and book events while providing administrators with tools to manage events, bookings, and view dashboard reports.

---

## Features

### User
- User Registration
- User Login with JWT Authentication
- Browse Available Events
- Search and Filter Events
- Book Events
- View My Bookings
- Cancel Bookings
- Responsive User Interface

### Administrator
- Secure Admin Login
- Dashboard with Statistics
- View Total Users
- View Total Events
- View Total Bookings
- View Booking Reports (Charts)
- Create Events
- Update Events
- Delete Events
- View All Bookings
- Manage User Bookings

---

## Technology Stack

### Frontend
- React.js
- React Router DOM
- Axios
- Bootstrap 5
- Chart.js
- React ChartJS 2

### Backend
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- Maven

### Database
- MySQL

---

## Project Structure

```
project_java
│
├── event_booking/               # Spring Boot Backend
│
├── event_booking_frontend/      # React Frontend
│
└── README.md
```

---

## Installation

### 1. Clone Repository

```bash
git clone https://github.com/hanifsaipulbahri-cpu/event-booking-system.git
```

```bash
cd event-booking-system
```

---

## Backend Setup

Go to backend folder

```bash
cd event_booking
```

Configure MySQL inside

```
src/main/resources/application.properties
```

Example

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/event_booking
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
```

Run the application

```bash
mvn spring-boot:run
```

Backend runs on

```
http://localhost:8080
```

---

## Frontend Setup

Open another terminal

```bash
cd event_booking_frontend
```

Install dependencies

```bash
npm install
```

Run React

```bash
npm start
```

Frontend runs on

```
http://localhost:3000
```

---

## Authentication

JWT Token Authentication is implemented.

After login, the frontend stores the JWT token and automatically attaches it to protected API requests.

---

## API Endpoints

### Authentication

| Method | Endpoint |
|---------|----------|
| POST | /api/auth/register |
| POST | /api/auth/login |

### Events

| Method | Endpoint |
|---------|----------|
| GET | /api/events |
| POST | /api/admin/events |
| PUT | /api/admin/events/{id} |
| DELETE | /api/admin/events/{id} |

### Bookings

| Method | Endpoint |
|---------|----------|
| POST | /api/bookings |
| GET | /api/bookings/my |
| DELETE | /api/bookings/{id} |

### Admin

| Method | Endpoint |
|---------|----------|
| GET | /api/admin/bookings |
| GET | /api/admin/report |

---

## Screenshots

Add screenshots inside

```
screenshots/
```

Example

```
screenshots/
│
├── login.png
├── register.png
├── events.png
├── bookings.png
├── admin-dashboard.png
├── manage-events.png
├── manage-bookings.png
```

---

## Future Improvements

- Email Notifications
- Payment Gateway Integration
- QR Code Ticket Generation
- Event Categories
- User Profile Management
- Password Reset
- Event Image Upload
- Seat Selection

---

## Author

**Mohammad Hanif Saipulbahri**

Bachelor of Computer Engineering

---

## License

This project was developed for academic purposes as a university capstone project.
