# TinyScrum Installation #

## Prerequisites ##
  * Java
  * Tomcat or similar web application container
  * MySQL database

## Installation ##
  * Download WAR file
  * Create MySQL database and "tinyscrum" user (ensure access is only possible from "localhost")
  * Deploy the WAR file to Tomcat
  * Database schema will be auto-created on first startup
  * You can customize database properties in META-INF/spring/database.properties

Default properties:
```
database.driverClassName=com.mysql.jdbc.Driver
database.url=jdbc\:mysql\://localhost\:3306/tinyscrum
database.username=tinyscrum
database.password=tinyscrumpass
```

http://code.google.com/p/tinyscrum/source/browse/TinyScrum/src/main/resources/META-INF/spring/database.properties

## Other databases ##
  * should be possible, you need a JDBC driver JAR and you have to configure Hibernate in META-INF/persistence.xml

# Setup #

  * At the moment, you need to use your SQL console or a tool like phpMyAdmin to set up the first user.
  * insert into scrum\_user values (null, true, 'matthias@wuttke.eu', 'Matthias Wuttke', 'password', 'user', 1);
  * Use the Administration area to define the first project. Without a project, most features won't work because every data object is bound to a project.
  * Close your browser and open it again. The project list in the upper right corner refreshes only at application startup. (This is a known issue.)