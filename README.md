# Studis

> Simplified student information system.

Database: MariaDB
username: studis
password: studisIS

Database can be created with `create-db.sql` script found in `entities/src/main/resources/sql`.

## Requirements
1. Maven
```bash
mvn -v
```

2. Java JRE
```bash
java -version
```

3. Running MySQL/MariaDB on localhost and listening on port 3306

## How to run
1. Build project
```bash
mvn clean package
```

2. Run
```bash
cd api/target
java -jar api-0.0.1.jar
```

### Test data

- `testniPodatki/kandidati.txt` (test candidates import as [#4](https://github.com/primozH/studis-server/issues/4))
