package exceptions

/**
 * Created by esfandiaramirrahimi on 2/7/2014.
 */
class UnauthorizedException(msg: String) extends RuntimeException(msg)

object UnauthorizedException {
  def build(msg: String): UnauthorizedException = new UnauthorizedException(msg)

  def build(msg: String, cause: Throwable) = new UnauthorizedException(msg).initCause(cause)
}
