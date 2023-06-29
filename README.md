The code behind `macaosoftware.com` lives right in this repo. The project is written in kotlin
and targets **compose-js** as platform.

<H3>How to run it</H3>
- `./gradlew jsBrowserRun` - run application in a browser
- `./gradlew jsBrowserProductionWebpack` - produce the output in `build/distributions`

<H3>Deploy in production</H3>
- `./gradlew jsBrowserProductionWebpack`
- `firebase deploy --only hosting`
