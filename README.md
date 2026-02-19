# Starcord

![Version](https://img.shields.io/badge/version-1.0.0–beta-orange)
![Language](https://img.shields.io/badge/Backend%20Language-TypeScript-blue)
![Language](https://img.shields.io/badge/Backend%20Language-Java-yellow)
![License](https://img.shields.io/badge/license-MIT-blue)

## Table of Contents

* [Overview](#overview)
* [Features](#features)
* [Tech Stack](#tech-stack)
* [Installation](#installation)
* [Usage](#usage)
* [Configuration](#configuration)
* [License](#license)

## Overview

**Project Name:** `Starcord`

Starcord is a project I started for an alternative to large complicated messaging apps, and extra micro-transactions and limitations. Starcord has one main feature, chatting. You can create large groupchats, send DMs to one another, upload files, and more. Furthermore, all of it is end to end encrypted (E2EE) so your chats can never get leaked.

## Features

* Real-time messaging with WebSockets
* Multiple group chats
* User authentication and profiles
* Message history persistence

## Tech Stack

* **Frontend:** `Angular`
* **Backend:** `Spring Boot`
* **Database:** `PostgreSQL`
* **Real-time Communication:** `WebSockets`

## Installation

```bash
git clone <repo-url>
cd <project-directory>
./mvnw install   # for Maven
npm install      # for Angular frontend
./mvnw spring-boot:run  # start backend
ng serve         # start frontend
```

## Usage

To be done

## Configuration

* `application.properties` example:

```
server.port=8080
spring.datasource.url=<your-database-uri>
spring.datasource.username=<db-username>
spring.datasource.password=<db-password>
jwt.secretKey==<your-jwt-secret>
jwt.accessExpiration=<your-access-expiration>
jwt.refreshExpiration=<your-refresh-expiration>
```

## License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).
