/**
 * Created by esfandiaramirrahimi on 2/7/2014.
 */
package services.authorization.social

import exceptions.UnauthorizedException

/**
 * Strategy Context to choose the right method based on the provider received from the client.
 */
class AuthorizationContainer {
  self: AuthorizationService =>

  def getUrl: String = {
    getAuthUrl
  }

  def authorize(code: String): Boolean = {
    try {
      getAuthToken(code)
      true
    }
    catch {
      case exception: UnauthorizedException =>
        false
    }
  }
}
