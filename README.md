# Elo Ranking System

This is web application implemented using Spring Boot. This application determines player rankings based on their
scores.

## Functionality

This application exposes below REST endpoints to receive input.

**1. **GET : /ranking****

    This endpoint returns list of players with their rankings

**2. **GET: /ranking/{PLAYER_NAME}****

    This endpoint returns ranking of specified player

    Response Structure of above 2 requests is same and it looks like:

        [{"player": {"id": 0,"name": "Wesley"},"score": 0,"rank": 6}]

**3. **GET: /players****

    This endpoint returns list of player sorted by player id

**4. GET: /players?sortBY=score&order=_asc/desc_**

    This endpoint returns list of players sorted by score in ascending or descending order(based on `order` paramenet value).

****5.** GET: /players?sortBY=ranking&order=_asc/desc_**

    This endpoint returns list of players sorted by rank in ascending or descending order(based on \`order\` paramenet value).

****6.** GET: /players?sortBY=wins&order=_asc/desc_**

    This endpoint returns list of players sorted by wins in ascending or descending order(based on \`order\` paramenet value).

****7.** GET: /players?sortBY=losses&order=_asc/desc_**

    This endpoint returns list of players sorted by losses in ascending or descending order(based on \`order\` paramenet value).

    Response Structure of endpoints from #3 to #7 is same and would look like:
    [
    {
        "player": {
            "id": 4,
            "name": "Bunny"
        },
        "score": 2,
        "rank": 4,
        "wins": 2,
        "losses": 6
    }]

******8.** GET: /report/{PLAYER_NAME}**

    This endpoind will return report for specified player, showing with whom they played and how they fared
    
    Response structure would look like:

    {
    "player": {
        "id": 3,
        "name": "Johanne"
    },
    "matchResults": [
        {
            "opposition": {
                "id": 29,
                "name": "Johnna"
            },
            "result": "Lost"
        }]
    }

## System Design

Application is built using Spring Boot and Java 8. Maven is used for building the application.

If we look at the input carefully, it looks like we have to chose algorithm at runtime and hence I have used **Strategy
Pattern** which is well suited for these kind of requirements.

If in future we want to perform some other operation like sorting based on player name or change response structure, we
can do it easily by adding/implementing new strategy without affecting existing functionality. If we look at
_**RankingStrategy**_ interface, I have used generic return type which means strategy can return different reponse
structures for different algorithms.

Application reads players and matches data from static files present at _src/main/resources/players.txt_ and
_src/main/resources/matches.txt_ files. Code for reading data from these files is put in DAO layer as these files work
as a database here.

I have used **Strategy**, **Factory** and **Builder** design patterns. I have also tried to follow **Single
Responsibility Principle** by breaking classes into small ones .

While implementing this functionality, I followed TDD approach and that is why code coverage is above 80%.

## How to Run Application

1. Clone this repository by executing below command

        git clone https://github.com/spote87/elo-ranking-system.git

2. Go to directory where you have checked out code and run below commands

        mvn clean install

        mvn spring-boot:run
   Second command will launch application. Once application is started, you can access it using below URL:

        http://localhost:8080/elorankingsystem/

**Note**: If port 8080 is already being used on your machine, please change value of _**server.port**_ in _
src/main/resources/application.properties_ to something else and try again.

You can access above REST services like below

        http://localhost:8080/elorankingsystem/report/Johanne
                        