/**
 * Created by esfandiaramirrahimi on 2/7/2014.
 */
package services.util

import java.io.FileNotFoundException
import org.slf4j.LoggerFactory
import org.streum.configrity.Configuration
import org.streum.configrity.converter.ValueConverter


class ConfigurationReader {

  import ConfigurationReader._

  private val log = LoggerFactory.getLogger(getClass)

  def canLoadConfiguration = {
    configOption.isDefined
  }

  @throws[NoSuchElementException]("Property doesn't exist!")
  def getProperty[T](key: String)(implicit converter: ValueConverter[T]): Option[T] = {
    var propertyValue: Option[T] = None
    if (configOption.get.contains(key))
      propertyValue = Option(configOption.get[T](key))

    propertyValue
  }

}

object ConfigurationReader {

  var configOption: Option[Configuration] = None

  def apply(path: String) = {
    val configurationReader = new ConfigurationReader

    try {
      configOption = Option(Configuration.loadResource("/social.conf"))
    }
    catch {
      case ioException: FileNotFoundException =>
        configurationReader.log.error("Configuration File Not Found!")
    }

    configurationReader
  }
}


