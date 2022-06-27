# EWU-CSCD212-Final-Group-Project
Group Members (Back Row 1):
  - Kelton Bassingthwaite
  - Josh Pedersen
  - Malcolm Shepherd

# Getting started

## Running via Gradle

```console
$ gradle run -q --console=plain
```

For just the CLI include the following argument.

```console
$ gradle run -q --console=plain --args=cli
```

> Note: Make sure to use the correct gradle wrapper for your platform.
>   - Unix - `./gradlew`
>   - Windows - `./gradlew.bat`

Furthermore, JUnit and Checkstyle can be run via `gradle test` and `gradle check` respectively.
All CI tasks can be run via `gradle ci`. 

## Running via provided JARs

The latest release can be downloaded [here](https://github.com/KGB33/EWU-CSCD212-Final-Group-Project/releases/latest/). 
The `app-fat-<version>.jar` includes the dependencies and is the recommended version to download.
Again, to run just the cli include the cli argument.

```console
java -jar $JAR_FILE
java -jar $JAR_FILE cli
```

## Command Line Options

**Usage**

```console
$ java -jar $JAR_FILE [ARG...]
```

**Arguments**
  - `cli` -- If present, launches the CLI instead of the GUI.
  - `two-player` -- If present, enables two player mode, i.e. no AI.


# Project Summary

We are going to create a chess game. 

Patterns:
  - Decorator Pattern
  - Adaper Pattern
  - Factory Pattern
  - Observer Pattern

# Milesontes 
  - [x] April 25th - Complete inital project setup (This document)
  - [x] May 7th    - King Tracer - Bare minimum to move a piece.
    - [x] May 1st - Simple API
      - [x] CLI <strike> /(g)RPC/web </strike>
    - [x] May 1st - King navigation
  - [ ] May 15th - Custom Chess Engine - [PR #20](https://github.com/KGB33/EWU-CSCD212-Final-Group-Project/pull/20)
  - [ ] May 15st - GUI - [PR #12](https://github.com/KGB33/EWU-CSCD212-Final-Group-Project/pull/12)
  - [x] May 15th - Remaining Pecies
    - [x] Capture
    - [x] Check/Checkmate
    - [x] En Passant

Github Repo is at: https://github.com/KGB33/EWU-CSCD212-Final-Group-Project
