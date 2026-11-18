# RadioInfo

RadioInfo is a Java desktop application for browsing Swedish Radio channels and
their scheduled programs. It retrieves channel and program data from the
[Sveriges Radio API](https://api.sr.se/).

## Requirements

- Java 21 or later
- Maven 3.9 or later
- Internet access when the application is running

## Build and run

From the project directory:

```bash
mvn clean package
java -jar target/radioinfo-fat.jar
```

The application opens with a list of available channels. Select a channel to
view its programs, and use the refresh action to retrieve the latest data.

## Tests

Run the test suite with:

```bash
mvn test
```
