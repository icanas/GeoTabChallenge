# BackUpVehicleData - Geotab Data Backup Application

This Java application is designed to back up vehicle data from a Geotab system. It allows users to execute scheduled backups, change the output path for CSV data, view YAML file values, change the execute interval, and exit the application.

## Author
Ivan Canas Ramos

## Prerequisites

To run the BackUpVehicleData application, you need the following:

1. Java Development Kit (JDK) 17 or above installed on your system.
2. Geotab API credentials (database name, username, and password) stored in a YAML file named `credentials.yml`. This file must be inside **./src/main/resources**.

## Execution Steps

Follow the steps below to execute the BackUpVehicleData application:

1. **Set Up API Credentials**: Before running the application, ensure you have the Geotab API credentials in the `credentials.yml`
file. This file must be inside **./src/main/resources** . The file should be structured as follows:

```yaml
database: your_database_name
userName: your_geotab_username
password: your_geotab_password
```
By default, this application is using **"mypreview.geotab.com"** as server, this can be changes inside **ConnectionConstants.java** before building
1. **Compile the Application**: Open a terminal/command prompt and navigate to the root directory of the application (where **pom.xml** lives). Then, compile the application using the following command:

```ps1
 mvn clean package
```
Then execute the generated jarfile
```ps1
java -jar target/GeoTabChallenge-1.0-SNAPSHOT.jar
```

2. **Options Menu**: 

```
=== Options Menu ===
1. Start Scheduled Execution
2. Change CSV Data Output Path
3. Print YAML File Values
4. Change Execute Interval (Minutes)
5. Exit
   ====================
```

### Execute Options: 

1. Option 1 - Start Scheduled Execution: This option starts the scheduled execution of the data backup. The data will be backed up at regular 1 minute interval by default.

2. Option 2 - Change CSV Data Output Path: This option allows you to change the output path for the CSV data. Enter the new path when prompted, and the changes will take effect immediately.

3. Option 3 - Print YAML File Values: This option displays the Geotab API credentials stored in the credentials.yml file.

4. Option 4 - Change Execute Interval (Minutes): This option allows you to change the interval at which data backups are executed. Enter the new interval in minutes, and the changes will take effect immediately.

5. Option 5 - Exit: This option allows you to exit the application.

### Tests: 
Follow the steps below to execute Test:

1. **Navigate to the Project Directory:**: Open a terminal/command prompt and navigate to the root directory of the project containing the pom.xml file.

```
mvn test
```


