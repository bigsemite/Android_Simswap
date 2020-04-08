# SIM SAWP 

## Intro:

Android Application to Monitor the SIM card in the android device. If it notice the change, the app sends SMS to another number

## Application

The App contains 3 intents while utilizing Android JobService to do active listening and monitor boot-up

### MainActivity Intent

The User interface that makes the user to configure App with the resue phone number. This intent also starts the myService3.java service which calls the Monitor.java intent when SIM card serial number is changed

