# Movie Rental Application

* The microservice generates a movie rental information slip based on the provided order.
* The application calculates the rent and reward based on the movie type.
* The response for the Movie Rental Info Slip includes customer information and details about the movie rental.
* Additional movie data can be updated in the movie.json file.
* The application offers a Swagger UI for convenient API testing.
* A Dockerfile has been included to facilitate the building of the image.
* Unit test cases have been added to cover various test scenarios.
* The code quality is examined using SonarLint in IntelliJ.
* Logging functionality has been implemented to capture and record pertinent information, events, or errors that arise during program execution.

### Assumptions
* The setup of movie data has been done according to the assignment requirements.
* If the number of rent days is less than one, set the default value to one.
* If the movie is not available, the function should return an empty movie rental information slip.

### Setup
* Import shared code in your IDE.
* Set up Maven and JDK 17.
* Navigate to the directory where the MovieRentalApplication and Start the Server. 
* Run Server and open swagger url to access API.
  http://localhost:9082/movierental/swagger-ui/index.html
* Swagger UI sample request -
  Request Header : "customerName": "C. U. Stomer"
  Request Body :
  {
    "movieRentData": [
     {
       "movieId": "F001",
       "noOfRentDay": 3
      },
      {
       "movieId": "F002",
       "noOfRentDay": 1
      }
   ]
  }
* Swagger UI sample Response -
  {
    "customerName": "C. U. Stomer",
    "movieRentDetails": [
      {
        "movieTitle": "You've Got Mail",
        "rentAmount": 3.5,
        "rewardPoints": 1
      },
      {
        "movieTitle": "Matrix",
        "rentAmount": 2,
        "rewardPoints": 1
      }
    ],
    "totalRentalAmount": 5.5,
    "totalRewardPoints": 2
  }

### TODO
* JWT based security configurations in Microservice
* Performance test
* SonarQube setup
