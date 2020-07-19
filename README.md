# Computer Consultancy Firm
A java application which manages details of employees, customers and contracts of a firm using JavaFX for the front-end and MySQL for the database. 

## Table of Contents
- [Getting Started](#getting-started)
- [Features](#features)
- [Screenshots](#screenshots)
- [Built With](#built-with)
- [Authors](#authors)
- [License](#license)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

* [JDK 8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
* [XAMPP](https://www.apachefriends.org/download.html)
* [MySQL](https://www.mysql.com/downloads/)
* [Apache](https://httpd.apache.org/download.cgi)
* [Scene Builder](https://gluonhq.com/products/scene-builder/)

### Installation

#### Clone Repository:
  
    git clone https://github.com/shiromi-basil/computer-consultancy-firm.git
    
#### Setup database:

Type `localhost/phpmyadmin/` in the browser's address bar. Click on the 'Import' tab in the navigation menu. Upload the file `computer-consultancy-firm/computer-consultancy-firm.sql` to setup datebase and add demo data into the database.

#### To run the application from the console:

    cd src/code
    javac Main.java
    java Main.java

## Features

#### Employeee
* Add an employee to the system.
* Delete an employee using employee ID.
* Update details of an employee.
* View details of an employee using employee ID.
* Assign job role to an employee.
* Assign work to an employee.
* Calculate the pay of an employee.

#### Customer
* Add a customer to the system.
* Delete a customer using customer ID.
* Update details of a customer.
* View details of a customer using customer ID.

#### Contract
* Add a contract to the system.
* Delete a contract using contract ID.
* Update details of a contract.
* View details of a contract using contract ID.
* Assign work to a contract.

## Screenshots
![Demo GIF](demo/computer-consultancy-firm.gif)

## Built With
* [Java](https://docs.oracle.com/en/java/)
* [JavaFX](https://docs.oracle.com/javafx/2/)
* [MySQL](https://dev.mysql.com/doc/)

## Authors
* Shiromi Basil - [shiromi-basil](https://github.com/shiromi-basil)

See also the list of [contributors](https://github.com/shiromi-basil/computer-consultancy-firm/graphs/contributors) who participated in this project.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
