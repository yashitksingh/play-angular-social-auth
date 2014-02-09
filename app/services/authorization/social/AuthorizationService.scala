/**
 * Created by esfandiaramirrahimi on 2/7/2014.
 * Trait inherited by specialized authorizers.
 */
package services.authorization.social

import exceptions.UnauthorizedException

/**
 * Trait inherited by specialized authorizers.
 */
trait AuthorizationService {
  @throws[UnauthorizedException]("Authorization failed!")
  def getAuthToken(code: String): String

  def getAuthUrl: String
}