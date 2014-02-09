/**
 * Created by esfandiaramirrahimi on 2/7/2014.
 */
package controllers


import play.api.cache.Cache
import play.api.mvc._
import play.api.mvc.Cookie
import services.authorization.social.{AuthorizationContainer, FacebookAuthorizationService, GoogleAuthorizationService}
import exceptions.UnauthorizedException

/**
 * Authorization controller.
 */
object Authorization extends Controller with Security {

  lazy val CacheExpiration =
    app.configuration.getInt("cache.expiration").getOrElse(300)

  var provider: Option[String] = None
  var authorizer: Option[AuthorizationContainer] = None

  implicit class ResultWithToken(result: Result) {
    def withToken(token: String): Result = {
      Cache.set(token, CacheExpiration)
      result.withCookies(Cookie(AuthTokenCookieKey, token, None, httpOnly = false))
    }
  }

  def login(provider: String) = Action {
    implicit request =>
      println("Provider:" + provider)
      if (!this.provider.equals(provider))
        authorizer = None
      this.provider = Option(provider)
      Ok(getAuthorizer(provider).getUrl)
  }

  /**
   * Google Oauth2 accessing code and exchanging it for Access & Refresh Token
   */
  def getAuthCallback = Action {
    implicit request =>

      val code = request.queryString("code").toList(0)

      try {
        val socialToken = getAuthorizer(provider.get).authorize(code)
        val token = java.util.UUID.randomUUID().toString
        Redirect("/").withToken(token)
      }
      catch {
        case exception: UnauthorizedException =>
          Unauthorized
      }
  }

  /** Invalidate the token in the Cache and discard the cookie */
  def logout = Action {
    implicit request =>
      val token = request.headers.get(AuthTokenHeader).orElse(request.getQueryString(AuthTokenUrlKey)).orNull
      Cache.remove(token)
      provider = None
      authorizer = None
      Ok(views.html.index()).discardingCookies(DiscardingCookie(name = AuthTokenCookieKey))
  }


  private def getAuthorizer(provider: String) = {
    if (!authorizer.isDefined) {
      provider match {
        case "google" => authorizer = Option(new AuthorizationContainer with GoogleAuthorizationService)
        case "facebook" => authorizer = Option(new AuthorizationContainer with FacebookAuthorizationService)
      }
    }
    authorizer.get
  }

}

