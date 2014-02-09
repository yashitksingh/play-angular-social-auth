# Example application for Play + AngularJS + Authorization with Social login.

## Intro

This is just a very basic sample application so I could play around and deal with a single-page's app security.
An oauth provider(facebook and google) are used to authorize a user. Angular frontend uses the generated token for signing the requests.

The template used here was the template generously provided by Marius Soutier [play-angular-require-seed](https://github.com/mariussoutier/play-angular-require-seed).
It also used his great blog post [Securing AngularJS Applications with Play](http://www.mariussoutier.com/blog/2013/07/14/272/)

Its quite easy to use other providers by extending the authorizer trait and building the AuthorizationContainer with that trait. 

provider match
{
   case "google" => authorizer = Option(new                			AuthorizationContainer with GoogleAuthorizationService)
}

I'm a beginner with Play, Angular and all the aforementioned technologies and so far so fun!

This works on Chrome and Firefox but for some reason login doest not work in Safari!
****I get [Error] Failed to load resource: the server 
responded with a status of 400 (Bad Request)****
I would greatly appreciate it if someone could help me understand why.

## Running the app locally

* `play update`
* Edit to social.conf and add your own client id, client secret and redirect url.
* Run via `play ~run`
* Go to localhost:9000

