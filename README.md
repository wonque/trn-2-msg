# trn-2-msg app

App reads transaction file and writes the result to file

## MOST IMPORTANT NOTE
It works on my machine =)
## Other non-crucial info how to build/run/etc
## Notes
This is a simple single threaded solution. Possibly to process large input file a multi-threaded solution should be implemented (with splitting input file on chunks).
## Build

```bash
cd <project_dir>

./gradlew jar
```
generated jar located in
```bash
build/libs
```

## Usage

```java
java -jar trn-2-msg-1.0-SNAPSHOT.jar <transaction_file_path> <output_file_path>
```

## Logging location
By default program should create log file in {user.home}/logs/<logFile>
