# goto-services

* Run `./gradlew idea` to generate IntelliJ idea files and open the project in the idea
* Go to ./exampleService and run `./gradlew run` to start the embedded jetty
* `localhost:8092/json` will hit the example JSON endpoint
* `localhost:8092/html` will hit the example HTML endpoint

## Service description

Simple Dropwizard service which delivers a payload to a calling service.
