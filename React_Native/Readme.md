## Go Corona App

Developed using React Native which means one single codebase for both iOS and Android


## Getting started

Ensure you have the following installed,
- Node.js
- XCode for Mac (for mac os)
- Android Studio (for windows/linux)

Install Expo CLI

`
npm install -g expo-cli
`

## Development

- Get into `go-corona-app` on your CLI and use command `yarn start` to start the development server.
- You will then have more options to open iOS or Android simulator

## Mock Server

- install supervisor globally using `npm install -g supervisor`
- run mock-server using `npm start` form the mock-server folder

## Setup Google OAuth Credentials

We need to create an app on Google Developer Console to be able to login with google.

Follow this to do so - https://docs.expo.io/versions/latest/sdk/google/#using-it-inside-of-the-expo-app

## Development Test

- Install Expo app from iOS or Android app store
- You will see the app listed in your projects once you login with your account
- You can test the app by launching from Expo app (this works only if both your development machine and phone are connected to the same WiFi network)

## Beta Test

- iOS - we need to publish the app to Apple Test Flight (platform to test apps privately without publishing to App Store; we will need a developer license for this)
- Android - we can share the APK, but it needs to be built on a windows/linux machine.

## Publish to App Store

- We need to go through the approval process
