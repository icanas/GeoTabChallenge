# BackUpVehicleData - Geotab Data Backup Application

This Java application is designed to back up vehicle data from a Geotab system. It allows users to execute scheduled backups, change the output path for CSV data, view YAML file values, change the execute interval, and exit the application.

## Author
Ivan Canas Ramos

## Prerequisites

To run the BackUpVehicleData application, you need the following:

1. Java Development Kit (JDK) 8 or above installed on your system.
2. Geotab API credentials (database name, username, and password) stored in a YAML file named `credentials.yml`.

## Execution Steps

Follow the steps below to execute the BackUpVehicleData application:

1. **Set Up API Credentials**: Before running the application, ensure you have the Geotab API credentials in the `credentials.yml` file. The file should be structured as follows:

```yaml
database: your_database_name
userName: your_geotab_username
password: your_geotab_password
```
1. **Compile the Application**: Open a terminal/command prompt and navigate to the root directory of the application. Then, compile the application using the following command:

```ps1
javac -cp .:<path_to_geotab_api_jar>:<path_to_yaml_jar> org/geotab/example/main/BackUpVehicleData.java
```
Replace <path_to_geotab_api_jar> and <path_to_yaml_jar> with the paths to the respective Geotab API and SnakeYAML library JAR files on your system.

2. **Options Menu**: Open a terminal/command prompt and navigate to the root directory of the application. Then, compile the application using the following command:

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

1. Option 1 - Start Scheduled Execution: This option starts the scheduled execution of the data backup. The data will be backed up at regular intervals specified in minutes.

2. Option 2 - Change CSV Data Output Path: This option allows you to change the output path for the CSV data. Enter the new path when prompted, and the changes will take effect immediately.

3. Option 3 - Print YAML File Values: This option displays the Geotab API credentials stored in the credentials.yml file.

4. Option 4 - Change Execute Interval (Minutes): This option allows you to change the interval at which data backups are executed. Enter the new interval in minutes, and the changes will take effect immediately.

5. Option 5 - Exit: This option allows you to exit the application.


