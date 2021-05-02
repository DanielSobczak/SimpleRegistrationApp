# Yet Another Registration Sample App

## Prerequisites

1. Android Studio 4.1.1 or higher
1. Android SDK 23 & 30

## Running tests

This sample project contains Unit Tests that can be invoked from command
line with:
>./gradlew test

as well as UI test written with the help of [kakao](https://github.com/agoda-com/Kakao)
>./gradlew connectedAndroidTest

## Static code analysis
For static code analysis run [detekt] (https://github.com/detekt/detekt)
>./gradlew detekt

and default lint configuration
>./gradlew lint

## Overview
App is written in MVI architecture based on a [mavericks library](https://github.com/airbnb/mavericks).

## TODO list
* improve loading/error feedback on UI
* handle back navigation from Confirmation screen
* extract versions & clean up gradle dependencies
* add proper formatting rules
* go through lint warning

