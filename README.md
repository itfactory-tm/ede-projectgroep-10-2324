# Enterprise Development Project - group 10

## Subject: Voetbal

## Team members:

- Sohaib Ibenhajene
- Jarne Dirken

## Contents

- [Introduction](#introduction)
- [Architecture](#acrtitecture)
- [Github Repositories](#github-repositories)
- [CI-CD pipline](#ci-cd-pipeline)
- [GitHub Actions](#github-actions)
- [Docker Compose](#docker-compose)
- [Docker Desktop](#docker-desktop)
- [0Auth2 Security](#0auth-security)
- [Postman](#postman)
- [Unit tests](#unit-tests)
- [Conclusion](#conclusion)

## Introduction

In our project we developed a Java backend achitecture of the basis of microservices. The acrhitecture includes:

- 4 microservices
  - Two MySQL backend microservices
  - Two MongoDB backend microservices
- Github Actions pipline
- Api related stuff
- Local hosting (Okteto didn't work anymore)

Our story is that we want to let people see football matches, clubs, players and transfers.

## Architecture

Our application has the following achitecture:
![alt text](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/images/ArchitectureDiagram.drawio.png?raw=true)

## Github Repositories

- [Club service](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/club-service)
- [Match service](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/match-service)
- [Player service](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/player-service)
- [Transfer compose](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/transfer-service)
- [Api Gateway](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/api-gateway)
- [Docker compose](https://github.com/itfactory-tm/ede-projectgroep-10-2324/blob/main/docker-compose.yml)

## CI-CD pipline

### GitHub Actions

All code and version control is handled by GitHub actions. A GitHub workflow is made made. This includes building and uploading a docker container of each service.

![alt text](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/images/GitHubWorkflow.png?raw=true)

### Docker Compose

This Docker Compose file serves as a blueprint for orchestrating a network of connected services within a containerized environment. It essentially defines a set of containers, each encapsulating a specific component of a larger application. The containers include databases like MongoDB and MySQL, as well as custom services for handling match, player, club, and transfer-related data.

![alt text](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/images/DockerCompose.png?raw=true)

### Docker Desktop

Because the okteto hosting didn't work at the time of making this project. We did everything local and used docker desktop.

![alt text](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/images/DockerDesktop.png?raw=true)

## 0Auth2 Security

We secrured our API gateway with 0Auth2. This way you can only do the basic function without being autorized (like view all the upcomming matches, view all the clubs and players). But it's not possible for anyone to delete a club, edit, etc... .

![alt text](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/images/0AuthCode.png?raw=true)
![alt text](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/images/0AuthAccepted.png?raw=true)

### without key

Here I try calling one club without being authorised:
![alt text](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/images/0AuthNotAutherised.png?raw=true)
As you can see this didn't work. Now let's try the same call but with our 0auth2 token.

### with key

Here you can see that the request did indeed work as expected.
![alt text](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/images/0AuthAutherised.png?raw=true)

## Postman

Here you can view all of our requests.

### Club requests

Get All Clubs
![alt text](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/images/GetAllClubs.png?raw=true)

Get One Club by name
![alt text](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/images/GetClubByName.png?raw=true)

Edit club
![alt text](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/images/EditClub.png?raw=true)

### Match requests

Get All Matches
![alt text](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/images/GetAllMatches.png?raw=true)

Get One Match
![alt text](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/images/GetOneMatch.png?raw=true)

Delete A Match
![alt text](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/images/DeleteMatch.png?raw=true)

### Player requests

Get All Players
![alt text](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/images/GetAllPlayers.png?raw=true)

Get One Player
![alt text](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/images/GetOnePlayer.png?raw=true)

Delete A Player
![alt text](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/images/DeletePlayer.png?raw=true)

### Transfer requests

Get All Transfers
![alt text](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/images/GetAllTransfers.png?raw=true)
(In this example were there no transfers so we got an empty array back)

## Unit tests

Of course we used Unit tests. Here you can see that we did unit tests in all of our services. These tests have a 100% coverage on our service classes.

### Club service

![alt text](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/images/ClubUnitTest.png?raw=true)

### Match service

![alt text](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/images/MatchUnitTest.png?raw=true)

### Player service

![alt text](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/images/PlayerUnitTest.png?raw=true)

### Transfer service

![alt text](https://github.com/itfactory-tm/ede-projectgroep-10-2324/tree/main/images/TransferUnitTest.png?raw=true)

## Conclusion

We found this project useful and fun.

#itfactory #thomasmore
