/**
 * Created by esfandiaramirrahimi on 2/7/2014.
 */
package services.authorization.social

import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson.JacksonFactory
import com.google.api.client.googleapis.auth.oauth2.{GoogleBrowserClientRequestUrl, GoogleTokenResponse, GoogleAuthorizationCodeFlow}
import scala.collection.JavaConverters
import com.google.api.client.auth.oauth2.{Credential, TokenResponseException}
import com.google.api.client.http.{HttpRequest, GenericUrl, HttpRequestFactory}
import play.api.libs.json.{JsValue, Json}
import org.streum.configrity.Configuration
import exceptions.UnauthorizedException

trait GoogleAuthorizationService extends AuthorizationService {

  private val config = Configuration.loadResource("/social.conf")
  private val CLIENT_ID = config[String]("google.client.id")
  private val CLIENT_SECRET = config[String]("google.client.secret")
  private val REDIRECT_URL = config[String]("google.redirect.url")
  private val USER_INFO_URL = config[String]("google.user.info.url")
  private val SCOPES = JavaConverters.asJavaCollectionConverter(config[List[String]]("google.authorization.scopes")).asJavaCollection

  private val TRANSPORT = new NetHttpTransport()
  private val JSON_FACTORY = new JacksonFactory()

  private val googleAuthorizationCodeFlow: GoogleAuthorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(TRANSPORT,
    JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, SCOPES).build()

  private val authorizationUrl = new GoogleBrowserClientRequestUrl(CLIENT_ID, REDIRECT_URL, SCOPES).set("access_type", "offline").set("response_type", "code").build()


  override def getAuthUrl: String = {
    authorizationUrl
  }

  @throws[UnauthorizedException]("Authorization with Google failed!")
  def getAuthToken(code: String): String = {
    try {
      val googleTokenResponse: GoogleTokenResponse = googleAuthorizationCodeFlow.newTokenRequest(code).setRedirectUri(REDIRECT_URL).execute()
      googleTokenResponse.getAccessToken
    }
    catch {
      case exception: TokenResponseException =>
        throw UnauthorizedException.build("Could not authorize with facebook", exception)
    }
  }

  def getUserInfo(googleTokenResponse: GoogleTokenResponse): JsValue = {
    val url: GenericUrl = new GenericUrl(USER_INFO_URL)
    val httpRequest: HttpRequest = getGoogleRequestFactory(googleTokenResponse).buildGetRequest(url)
    httpRequest.getHeaders.setContentType("application/json")
    val response = httpRequest.execute().parseAsString()
    val jsonGoogleIdentity = Json.parse(response)
    jsonGoogleIdentity
  }

  private def getGoogleRequestFactory(googleTokenResponse: GoogleTokenResponse) = {
    val credential: Credential = googleAuthorizationCodeFlow.createAndStoreCredential(googleTokenResponse, null)
    val requestFactory: HttpRequestFactory = TRANSPORT.createRequestFactory(credential)
    requestFactory
  }
}

