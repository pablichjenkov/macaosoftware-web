<H2>macaosoftware-web</H2>

The code behind `macao-software.com` lives right in this repo. The project is written in **Kotlin** language using the **compose-js** compilation target.

<H3> How to run it</H3>

`./gradlew jsBrowserRun` - run application in a browser

`./gradlew jsBrowserProductionWebpack` - produce the output in `build/distributions`

<H3>Deploy to production</H3>

`./gradlew jsBrowserProductionWebpack`  
`firebase deploy --only hosting`
