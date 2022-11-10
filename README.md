<h1 align="center">java-explore-with-me
</h1>

<p align="center">
  
<img src="https://img.shields.io/badge/made%20by-glebinside-blue.svg" >

<img src="https://img.shields.io/badge/java-18-orange.svg">

<img src="https://img.shields.io/github/languages/top/glebinside/java-explore-with-me.svg">

</p>

## Description

**An example of the implementation of the frontend part of the application**
<img width="1440" alt="S19_09_1662138862" src="https://user-images.githubusercontent.com/95642615/201180362-27e65b15-9ea1-4e68-8799-3d80cf22170b.png">

Backend for a service that gives you the opportunity to share information about interesting events and helps you find a company to participate in them. <br/>
Two services: <br/>
The Main service — contains everything necessary for the product to work <br/>
Statistics service — stores the number of views and allows you to make various selections to analyze the operation of the application

## How to use

### The application has only a backend implemented, so you can check its functionality using, for example, postman:
1. Launch java-explore-with-me_main-db_1 and java-explore-with-me_stats-db_1 from the <img src="https://github.com/devicons/devicon/blob/master/icons/docker/docker-original.svg" title="docker" alt="docker" width="40" height="40"/>&nbsp;
2. Launch the Main in the main method
3. Launch the Stats in the main method
4. Launch <img src="https://img.shields.io/badge/postman-orange.svg"> and send requests


## About the project

### Explore together

Main service: <br/>
Divide the API of the main service into three parts. The first one is public, accessible without registration to any network user. The second one is closed, available only to authorized users. The third is administrative, for service administrators. Each part has its own requirements <br/>
Statistics Service: <br/>
The second service is designed to collect information. Firstly, about the number of user requests to the event lists and, secondly, about the number of requests for detailed information about the event. Based on this information, statistics about the operation of the application are generated


