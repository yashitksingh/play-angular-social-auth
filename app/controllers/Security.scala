package controllers

import play.api.cache.Cache
import play.api.mvc._
import play.api.libs.json.Json

trait Security {
  self: Controller =>

  implicit val app: play.api.Application = play.api.Play.current

  val AuthTokenHeader = "X-XSRF-TOKEN"
  val AuthTokenCookieKey = "XSRF-TOKEN"
  val AuthTokenUrlKey = "auth"

  /** Checks whether the token exists */
  def hasToken(token: String): Boolean = {
    Cache.get(token).isDefined
  }

  def HasToken[A](p: BodyParser[A] = parse.anyContent)(f: String => Request[A] => Result): Action[A] =
    Action(p) {
      implicit request =>
        val token = request.headers.get(AuthTokenHeader).orElse(request.getQueryString(AuthTokenUrlKey)).getOrElse("NaN")
        if (Cache.get(token).isDefined)
          f(token)(request)
        else
          Unauthorized(Json.obj("err" -> "No Token"))

    }


}