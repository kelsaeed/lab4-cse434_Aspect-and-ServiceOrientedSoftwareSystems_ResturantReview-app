```markdown
# CeView - Lab 4 (CSE434)

A mini project for **Aspect- and Service-Oriented Software Systems (CSE434)** - Spring 2025  
This lab implements **rate limiting**, **locking**, and **caching** aspects using Redis within a Spring Boot application.

## Lab Requirements Implemented

- Rate limiting with Aspect + Redis
- Locking with Aspect + Redis (timeout simulation via multiple requests)
- Caching database queries with Redis
- Bonus: Custom `CacheAspect` for selected endpoints

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/your-username/lab4-cse434-redis-aspects.git
cd lab4-cse434-redis-aspects
```

### Start PostgreSQL and Redis with Docker

```bash
docker-compose up -d
```

### Run the Spring Boot Application

```bash
mvn spring-boot:run
```

Application will be available at:  
**http://localhost:8080**

---

## Demonstrations

- ✅ Rate limiting tested with repeated requests via Postman
- ✅ Locking tested with delayed method simulation and multiple threads
- ✅ Caching tested with repeated fetches of the same data
- ✅ Screenshots provided in `/screenshots/lab4/`

---

## Technologies Used

- Java 23
- Spring Boot 3.4.4
- Redis 7
- PostgreSQL 16
- Docker and Docker Compose
- Maven
- IntelliJ IDEA
- Postman
