Just a jar app to export data from json source to csv.
The mapping is customized for a specific structure.
The url is configured in the pom.xml
I used profiles to ease environment configuration.
As first release only local profile exists.

To run the application you need a java 6+ version installed on your system. Then type
java -jar target/GoEuroTest.jar CITY_NAME
