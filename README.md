# Example application for Play + AngularJS + Authorization with Social login.

## Intro

I've just begun to learn Scala, Play and AngularJs. This is just a very basic sample application so I could play around and deal with a single-page's app security and experiment with these technologies. Oauth provider(facebook and google) are used to authorize the client. Angular frontend then uses the generated token for signing the request. I think in reality I will end up using a comprehensive library for security such as [play-silhouette](https://github.com/mohiva/play-silhouette) which seems to be actively developed, playing around for now! 

The template used here was the template generously provided by Marius Soutier [play-angular-require-seed](https://github.com/mariussoutier/play-angular-require-seed).
It also the blog post [Securing AngularJS Applications with Play](http://www.mariussoutier.com/blog/2013/07/14/272/)

For authorizing with google I used the realease candidate of google's java api client => sbt: "com.google.api-client" % "google-api-client" % "1.17.0-rc". And For authorizing with facebook I used google restfb since the facebook's official java api client is not actively maintained anymore => sbt:  "com.restfb" % "restfb" % "1.6.12".


Its quite easy to use other providers by extending the authorizer trait and building the AuthorizationContainer with that trait. 

provider match
{
   case "google" => authorizer = Option(new                			AuthorizationContainer with GoogleAuthorizationService)
}

The app is running on http://play-angular-social-auth.herokuapp.com. This works fine on Chrome and Firefox but for some reason login doest not work in Safari!
I get [Error] Failed to load resource: the server responded with a status of 400 (Bad Request)
I would greatly appreciate it if someone could help me understand why.

## Running the app locally

* `play update`
* Edit to social.conf and add your own client id, client secret and redirect url.
* Run via `play ~run`
* Go to localhost:9000

