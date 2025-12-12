# Bus Ticket Booking System

> Một hệ thống đặt vé xe buýt hiện đại, được xây dựng với Spring Boot 3.4.4, MySQL, RabbitMQ và công nghệ web responsive.

## 📋 Mục Lục
- [Giới thiệu](#giới-thiệu)
- [Tính năng chính](#tính-năng-chính)
- [Cấu trúc dự án](#cấu-trúc-dự-án)
- [Yêu cầu hệ thống](#yêu-cầu-hệ-thống)
- [Cài đặt](#cài-đặt)
- [Khởi chạy](#khởi-chạy)
- [Công nghệ sử dụng](#công-nghệ-sử-dụng)
- [API Documentation](#api-documentation)
- [Cấu hình](#cấu-hình)
- [Dữ liệu Test](#dữ-liệu-test)
- [Docker](#docker)
- [Đóng góp](#đóng-góp)
- [License](#license)

## 🎯 Giới thiệu

Bus Ticket Booking System là một nền tảng toàn diện cho việc quản lý và đặt vé xe buýt. Hệ thống cung cấp giao diện người dùng thân thiện, backend mạnh mẽ với microservices architecture, và quản lý cơ sở dữ liệu tập trung.

## ✨ Tính năng chính

### 👥 Cho người dùng
- ✅ Đăng ký và đăng nhập tài khoản
- ✅ Tìm kiếm và đặt vé xe buýt
- ✅ Xem lịch trình chuyến xe
- ✅ Quản lý hồ sơ cá nhân
- ✅ Thanh toán trực tuyến an toàn
- ✅ Lịch sử đặt vé

### 🛠️ Cho quản trị viên
- ✅ Quản lý người dùng
- ✅ Quản lý xe buýt và tuyến đường
- ✅ Quản lý vé và giá cả
- ✅ Thống kê và báo cáo
- ✅ Quản lý nhân viên

## 📁 Cấu trúc dự án

```
BusTicketBooking/
│
├── 📂 Client/                          # Frontend - Giao diện người dùng
│   ├── 📂 assets/
│   │   ├── 📂 css/                     # Stylesheet
│   │   │   ├── header.css
│   │   │   ├── login.css
│   │   │   ├── payment.css
│   │   │   ├── profile.css
│   │   │   ├── register.css
│   │   │   ├── schedule.css
│   │   │   ├── state.css
│   │   │   ├── styles.css
│   │   │   └── 📂 admin/               # Stylesheet cho trang admin
│   │   ├── 📂 imgs/                    # Hình ảnh, logo
│   │   └── 📂 js/                      # JavaScript scripts
│   │       ├── authorize.js            # Xác thực và phân quyền
│   │       ├── cusor.js                # Hiệu ứng con trỏ
│   │       ├── handlemessage.js        # Xử lý thông báo
│   │       ├── jsquery.js              # jQuery utilities
│   │       ├── payment.js              # Xử lý thanh toán
│   │       ├── profile.js              # Quản lý hồ sơ
│   │       ├── provinces.js            # Dữ liệu tỉnh thành
│   │       ├── scroll.js               # Scroll effects
│   │       └── 📂 admin/               # Scripts quản trị
│   │
│   └── 📂 resources/
│       ├── 📂 admin/                   # Trang quản trị
│       │   ├── admin.html
│       │   ├── busmanagement.html      # Quản lý xe
│       │   ├── dashboard.html          # Bảng điều khiển
│       │   ├── login.html
│       │   ├── ticketmanagement.html   # Quản lý vé
│       │   └── usermanagement.html     # Quản lý người dùng
│       │
│       └── 📂 layouts/                 # Các trang chính
│           ├── index.html              # Trang chủ
│           ├── introduce.html          # Giới thiệu
│           ├── login.html              # Đăng nhập
│           ├── profile.html            # Hồ sơ người dùng
│           └── schedule.html           # Xem lịch trình
│
├── 📂 DataFaker/                       # Công cụ tạo dữ liệu test
│   ├── operator_facker.py              # Tạo dữ liệu nhà cung cấp
│   ├── trip_facker.py                  # Tạo dữ liệu chuyến xe
│   └── __pycache__/
│
├── 📂 MainServer/                      # Backend - Server chính (Spring Boot)
│   ├── 📄 pom.xml                      # Maven configuration
│   ├── 📄 Dockerfile                   # Docker image definition
│   ├── 📄 mvnw                         # Maven wrapper (Unix)
│   ├── 📄 mvnw.cmd                     # Maven wrapper (Windows)
│   │
│   ├── 📂 src/
│   │   ├── 📂 main/
│   │   │   ├── 📂 java/
│   │   │   │   └── 📂 com/project/busticket/
│   │   │   │       ├── 📂 config/        # Cấu hình (Security, WebSocket, etc)
│   │   │   │       ├── 📂 controller/    # REST Controllers
│   │   │   │       ├── 📂 service/       # Business Logic
│   │   │   │       ├── 📂 repository/    # Data Access Layer (JPA)
│   │   │   │       ├── 📂 entity/        # JPA Entities / Models
│   │   │   │       ├── 📂 dto/           # Data Transfer Objects
│   │   │   │       ├── 📂 mapper/        # MapStruct Mappers
│   │   │   │       ├── 📂 exception/     # Custom Exceptions
│   │   │   │       ├── 📂 filter/        # Security Filters
│   │   │   │       ├── 📂 middleware/    # Middleware Components
│   │   │   │       ├── 📂 util/          # Utility Classes
│   │   │   │       └── BusticketApplication.java  # Entry Point
│   │   │   │
│   │   │   └── 📂 resources/
│   │   │       ├── 📄 application.yml   # Application configuration
│   │   │       ├── 📂 static/           # Static files (CSS, JS, imgs)
│   │   │       └── 📂 templates/        # Thymeleaf templates
│   │   │
│   │   └── 📂 test/
│   │       ├── 📂 java/
│   │       │   └── 📂 com/project/busticket/
│   │       │       └── BusticketApplicationTests.java
│   │       └── 📂 resources/
│   │
│   └── 📂 target/                      # Build output
│       ├── 📂 classes/
│       ├── 📂 generated-sources/
│       ├── 📂 test-classes/
│       ├── busticket-0.0.1-SNAPSHOT.jar
│       └── ...
│
├── 📂 SubServer/                       # Backend - Server phụ (Spring Boot)
│   ├── 📄 pom.xml                      # Maven configuration
│   ├── 📄 Dockerfile                   # Docker image definition
│   ├── 📄 mvnw                         # Maven wrapper (Unix)
│   ├── 📄 mvnw.cmd                     # Maven wrapper (Windows)
│   │
│   ├── 📂 src/                         # Tương tự MainServer
│   │   ├── 📂 main/
│   │   └── 📂 test/
│   │
│   └── 📂 target/                      # Build output
│
├── 📂 mysql_replication/               # Cấu hình MySQL Master-Slave
│   ├── 📄 docker-compose.yml           # Docker Compose configuration
│   ├── 📄 master.cnf                   # MySQL Master configuration
│   │
│   ├── 📂 mysql-master/                # Master database data
│   │   ├── binlog.*                    # Binary logs
│   │   ├── mysql-bin.*                 # MySQL binary logs
│   │   ├── auto.cnf
│   │   ├── ibdata1
│   │   ├── ibtmp1
│   │   ├── 📂 #innodb_redo/
│   │   ├── 📂 #innodb_temp/
│   │   ├── 📂 busticket/               # Database: busticket
│   │   └── 📂 mysql/
│   │
│   └── 📂 mysql-slave/ (tương tự)      # Slave database data
│
├── 📂 target/                          # Root level build output
│   ├── 📂 generated-sources/
│   └── 📂 generated-test-sources/
│
└── 📄 README.md                        # File này
```

## 📋 Yêu cầu hệ thống

### Hardware
- **CPU**: Intel Core i5 trở lên hoặc tương đương
- **RAM**: Tối thiểu 8GB
- **Disk**: 20GB dung lượng trống

### Software
- **Java**: JDK 21 trở lên
- **Maven**: 3.8.0 trở lên
- **MySQL**: 8.0 trở lên
- **RabbitMQ**: 3.12 trở lên (optional)
- **Docker**: 20.10+ (nếu sử dụng containerization)
- **Node.js**: 18+ (nếu cần build frontend assets)

## 🚀 Cài đặt

### 1. Clone Repository
```bash
git clone https://github.com/yourusername/BusTicketBooking.git
cd BusTicketBooking
```

### 2. Cài đặt Java và Maven
```bash
# Kiểm tra Java
java -version

# Kiểm tra Maven
mvn -version
```

### 3. Cài đặt MySQL
```bash
# Sử dụng Docker (khuyến nghị)
cd mysql_replication
docker-compose up -d

# Hoặc cài đặt MySQL thủ công
# Windows: Download từ https://dev.mysql.com/downloads/mysql/
# macOS: brew install mysql
# Linux: apt-get install mysql-server
```

### 4. Cấu hình Database

```sql
-- Kết nối đến MySQL
mysql -u root -p

-- Tạo database
CREATE DATABASE busticket DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Chọn database
USE busticket;

-- Import schema (nếu có file schema.sql)
source path/to/schema.sql;
```

### 5. Tạo dữ liệu Test (tuỳ chọn)

```bash
cd DataFaker
pip install -r requirements.txt
python operator_facker.py
python trip_facker.py
```

### 6. Build MainServer
```bash
cd MainServer
mvn clean install
```

### 7. Build SubServer
```bash
cd SubServer
mvn clean install
```

## ▶️ Khởi chạy

### Chạy MainServer
```bash
cd MainServer

# Sử dụng Maven
mvn spring-boot:run

# Hoặc chạy JAR trực tiếp
java -jar target/busticket-0.0.1-SNAPSHOT.jar
```

**MainServer sẽ chạy tại**: `http://localhost:8080`

### Chạy SubServer
```bash
cd SubServer

# Sử dụng Maven
mvn spring-boot:run

# Hoặc chạy JAR trực tiếp
java -jar target/busticket-0.0.1-SNAPSHOT.jar --server.port=8081
```

**SubServer sẽ chạy tại**: `http://localhost:8081`

### Truy cập Frontend
```
http://localhost:8080
```

### Truy cập Admin Panel
```
http://localhost:8080/admin/dashboard.html
```

## 🛠️ Công nghệ sử dụng

### Backend
| Công nghệ | Phiên bản | Mục đích |
|-----------|----------|---------|
| **Spring Boot** | 3.4.4 | Framework chính |
| **Spring Data JPA** | Latest | ORM và database access |
| **Spring WebSocket** | Latest | Real-time communication |
| **Spring Security** | Latest | Xác thực và phân quyền |
| **Spring OAuth2** | Latest | OAuth2 resource server |
| **Lombok** | 1.18.30 | Giảm boilerplate code |
| **MapStruct** | 1.5.5 | DTO mapping |
| **RabbitMQ** | 5.21.0 | Message broker |
| **Caffeine** | Latest | In-memory caching |
| **JWT (Nimbus)** | 9.33 | Token authentication |

### Database
| Công nghệ | Phiên bản | Mục đích |
|-----------|----------|---------|
| **MySQL** | 8.0+ | Primary Database |
| **MySQL Connector/J** | Latest | JDBC Driver |
| **Master-Slave Replication** | Built-in | High Availability |

### Frontend
| Công nghệ | Mục đích |
|-----------|---------|
| **HTML5** | Markup |
| **CSS3** | Styling & Responsive Design |
| **JavaScript/jQuery** | Interactivity |
| **Thymeleaf** | Server-side templating |

### DevOps & Deployment
| Công nghệ | Mục đích |
|-----------|---------|
| **Docker** | Containerization |
| **Docker Compose** | Multi-container orchestration |
| **Maven** | Build automation |

## 📖 API Documentation

### Authentication
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "email": "user@example.com",
    "fullName": "Nguyen Van A",
    "role": "USER"
  }
}
```

### Tìm kiếm vé
```http
GET /api/tickets/search?from=HaNoi&to=HoChiMinh&date=2025-12-20
Authorization: Bearer {token}
```

### Đặt vé
```http
POST /api/bookings
Content-Type: application/json
Authorization: Bearer {token}

{
  "ticketId": 1,
  "quantity": 2,
  "passengerDetails": [...]
}
```

Xem chi tiết tại [API Documentation](./API.md) (nếu có)

## ⚙️ Cấu hình

### Application Properties

**File**: `MainServer/src/main/resources/application.yml`

```yaml
spring:
  application:
    name: busticket
  datasource:
    url: jdbc:mysql://localhost:3306/busticket
    username: root
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
  
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:8080/api/auth

server:
  port: 8080
  servlet:
    context-path: /
```

### Biến môi trường
```bash
# Database
export DB_URL=jdbc:mysql://localhost:3306/busticket
export DB_USERNAME=root
export DB_PASSWORD=your_password

# RabbitMQ
export RABBITMQ_HOST=localhost
export RABBITMQ_PORT=5672
export RABBITMQ_USERNAME=guest
export RABBITMQ_PASSWORD=guest

# JWT
export JWT_SECRET=your_jwt_secret_key_here
export JWT_EXPIRATION=86400000
```

## 🧪 Dữ liệu Test

### Tài khoản Test
| Email | Password | Role |
|-------|----------|------|
| admin@test.com | admin123 | ADMIN |
| user@test.com | user123 | USER |
| operator@test.com | operator123 | OPERATOR |

### Tạo dữ liệu giả lập

```bash
cd DataFaker

# Cài đặt dependencies
pip install mysql-connector-python faker

# Tạo dữ liệu nhà cung cấp
python operator_facker.py

# Tạo dữ liệu chuyến xe
python trip_facker.py
```

## 🐳 Docker

### Build Docker Images

**MainServer:**
```bash
cd MainServer
docker build -t busticket-main:1.0 .
```

**SubServer:**
```bash
cd SubServer
docker build -t busticket-sub:1.0 .
```

### Chạy với Docker Compose

Tạo file `docker-compose.yml` tại root:

```yaml
version: '3.8'

services:
  mysql-master:
    image: mysql:8.0
    ports:
      - "3306:3306"
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: busticket
    volumes:
      - ./mysql_replication/mysql-master:/var/lib/mysql

  rabbitmq:
    image: rabbitmq:3.12-management
    ports:
      - "5672:5672"
      - "15672:15672"

  main-server:
    build:
      context: ./MainServer
    ports:
      - "8080:8080"
    depends_on:
      - mysql-master
      - rabbitmq
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql-master:3306/busticket
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: root

  sub-server:
    build:
      context: ./SubServer
    ports:
      - "8081:8080"
    depends_on:
      - mysql-master
      - rabbitmq
```

Chạy:
```bash
docker-compose up -d
```

### Kiểm tra logs
```bash
docker-compose logs -f main-server
docker-compose logs -f sub-server
```
