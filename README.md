# Employ management API
> Simple example of full back-end application with endpoints

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Features](#features)
* [Status](#status)
* [Inspiration](#inspiration)
* [Contact](#contact)

## General info
The purpose of this exemplary application is to implement exemplary data flow.
No need of installing additional DB software. The Api works on in memory H2 DB.

## Technologies
* SpringBoot
* Maven 
* Apache Tomcat 
* JUnit
* Swagger

## Setup
1. Build project with `mvn install` command.
2. Start project with port number 8080
3. Use Swagger or Postman as api front simulation
      Swagger url -> http://localhost:8080/swagger-ui.html#/
      Postman file under AdditionalFiles project directory

## Features
List of features ready:
* Adding employee 
* find employee by id
* modify employee
* delete employee
* search for employees by search criteria

## Status
Project is: _inprogress_
Searching by swagger require look in to. 
Support for type of objects is missing.

## Inspiration
The project was inspired by testing purposes.

## Contact
Created by [@AdamSobieraj](https://github.com/AdamSobieraj) - feel free to contact me!