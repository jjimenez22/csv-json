The raw data can be found, for free, as a CSV file here: https://www.gov.uk/government/statistical-data-sets/price-paid-data-downloads.
Also, an explanation of the data structure can be found here: https://www.gov.uk/guidance/about-the-price-paid-data#explanations-of-column-headers-in-the-ppd

## Requirements
You will need docker and docker-compose to run the app.
Maven will be downloaded if not installed previously.

## Installation
Open a terminal in the app's root folder and run:
> ./mvnw package && docker-compose build

## Run the app
In a terminal in the app's root folder, run:
> docker-compose run

## Endpoints
- `GET /pricespaid?from=yyyy-MM-dd&to=yyyy-MM-dd&page=pageNum`
- `GET /pricespaid/:transactionId`

from and to are optional, page is defaulted to 0

### Examples
- Get all: http://localhost:8080/pricespaid
- Get all by date range: http://localhost:8080/pricespaid?from=2007-01-01&to=2021-08-21
- Get another page: http://localhost:8080/pricespaid?page=1
- Get by id: http://localhost:8080/pricespaid/C6209F5F-241F-295E-E053-6C04A8C0DDCC
