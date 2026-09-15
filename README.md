# PlaneSpotter
A web application for identifying a plane in the sky and saving personal plane sightings.
The app allows users to provide their location and the direction in which they see a plane. It uses live aviation data to identify the most likely plane and retrieves additional flight information like the airline, departure and arrival airports.
Logged in users can save identified planes as sightings in their own personal collection.

## Features
- Register/login
- Identify plane using location (latitude, longitude) and bearing
- Displays ICAO24, Callsign, Airline, Departure and Arrival Airports
- Save sighting
- View "My Collection"
- Delete sighting 
- Update user details
- Local airline and airport data stored in MySQL

## Technologies 
- Java
- Maven
- Spring Boot
- Spring Security
- Spring Hibernate
- MySQL
- HTML
- JavaScript
- OpenSky API
- FlightLabs API

## App flow
1. User enters coordinates and bearing
2. OpenSky identifies the plane and provides ICAO24 and Callsign
3. FlightLabs provides flight information 
4. App displays the result
5. User can save the sighting 

## Project Structure
### Controllers
Handle HTTP requests and responses
### Services
Contain application and business logic (plane identification, additional flight details, authentication, sighting management)
### Repositories
Provide database access using Spring Data JPA
### Entities 
Represent data stored in MySQL (User, Plane, Sighting, Airline, Airport)
### DTOs
Request and response DTOs are used to transfer data between the frontend and the backend without exposing database entities.

## How to run
1. Clone the repository
2. Configure MySQL
3. Add API keys
4. Run Spring Boot
5. Open the application's local address in a browser

## Future Improvements
- Improved frontend design
- Improved aircraft matching accuracy
- Sighting ordering, filtering
- Collection statistics 
