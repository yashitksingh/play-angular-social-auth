/**
 * Created by esfandiaramirrahimi on 2/7/2014.
 */
package services.authorization.social


import org.streum.configrity.Configuration
import com.restfb.DefaultFacebookClient
import com.restfb.FacebookClient.AccessToken
import com.restfb.exception.FacebookException
import exceptions.UnauthorizedException

trait FacebookAuthorizationService extends AuthorizationService {

  private val config = Configuration.loadResource("/social.conf")
  private val CLIENT_ID = config[String]("facebook.client.id")
  private val CLIENT_SECRET = config[String]("facebook.client.secret")
  private val REDIRECT_URL = config[String]("facebook.redirect.url")
  private val authorizationUrl = "https://www.facebook.com/dialog/oauth?client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URL + "&scope=email"

  override def getAuthUrl = {
    authorizationUrl
  }

  @throws[UnauthorizedException]("Authorization with facebook failed!")
  override def getAuthToken(code: String): String = {
    println("code code:" + code)
    try {
      val accessToken: AccessToken = new DefaultFacebookClient().obtainAppAccessToken(CLIENT_ID, CLIENT_SECRET)
      accessToken.getAccessToken
    }
    catch {
      case exception: FacebookException =>
        throw UnauthorizedException.build("Could not authorize with facebook", exception)
    }
  }
}

