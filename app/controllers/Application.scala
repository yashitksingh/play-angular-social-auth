package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._

object Application extends Controller with Security {

  /** Serves the index page, see views/index.scala.html */
  def index = Action {
    Ok(views.html.index())
  }

  /**
   * Returns the JavaScript router that the client can use for "type-safe" routes.
   * @param varName The name of the global variable, defaults to `jsRoutes`
   */
  def jsRoutes(varName: String = "jsRoutes") = Action {
    implicit request =>
      Ok(
        Routes.javascriptRouter(varName)(
          routes.javascript.Authorization.login,
          routes.javascript.Authorization.logout,
          routes.javascript.Application.ping
        )
      ).as(JAVASCRIPT)
  }


  /**
   * Returns ok if the token exists.
   */
  def authenticatedAction = Action {
    implicit request =>
      val token = request.headers.get(AuthTokenHeader).orElse(request.getQueryString(AuthTokenUrlKey)).getOrElse("Nan")
      if (hasToken(token))
        Ok(Json.obj("message" -> "You are authorized!"))
      else
        Unauthorized(Json.obj("err" -> "No Token"))
  }

  /**
   * Returns the current authorization's ID if a valid token is transmitted.
   * Also sets the cookie (useful in some edge cases).
   * This action can be used by the route service.
   */
  def ping() = HasToken() {
    token => implicit request =>
      Ok(Json.obj("message" -> "You are authorized!"))
  }

}
