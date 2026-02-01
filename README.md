# Salesforce Metadata Fetcher

A Spring Boot backend application that integrates with Salesforce using REST APIs to fetch metadata such as Validation Rules.  
This project demonstrates secure OAuth 2.0 authentication, API integration, and clean service-based architecture.

---

## Features
- Salesforce OAuth 2.0 authentication
- Fetch Salesforce metadata via REST APIs
- Secure handling of sensitive credentials
- Clean and modular Spring Boot service layer

---

## Technology Stack
- Java
- Spring Boot
- Maven
- REST APIs
- Salesforce Platform

---

## Configuration & Security
All sensitive information is managed using environment variables.  
No secrets are hardcoded or committed to the repository.

Required environment variables:

SF_CLIENT_ID
SF_CLIENT_SECRET
SF_USERNAME
SF_PASSWORD

---

## How to Run the Project ------------

*** 1. Clone the repository
git clone https://github.com/sainiharish7305/salesforceProject_CloudVandana.git


*** 2. Navigate to the project directory
cd salesforceProject_CloudVandana


*** 3. Set environment variables (Git Bash example)
export SF_CLIENT_ID=your_client_id
export SF_CLIENT_SECRET=your_client_secret
export SF_USERNAME=your_username
export SF_PASSWORD=your_password


*** 4. Run the application
mvn spring-boot:run


---

## Project Structure
src/main/java/Project_WebApp/demo
│
├── SalesforceService.java
├── SalesforceApiService.java
└── SalesforceTokenService.java


---

## Best Practices Followed
- Secrets excluded using `.gitignore`
- Environment-based configuration
- Service-oriented design
- Clean and readable code structure

---

## Author
Harish Saini  
GitHub: https://github.com/sainiharish7305
