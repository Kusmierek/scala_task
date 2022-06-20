import com.typesafe.config.ConfigFactory
import org.scalatest.funsuite.AnyFunSuite

import java.io.File
import javax.imageio.ImageIO
import scala.runtime.stdLibPatches.Predef.assert

class pointsTest extends AnyFunSuite {
  val conf = ConfigFactory.load()
  val input = conf.getString("configs.input")
  val files = getListOfFiles(input)
  println(files)

  test("photoRecognizer.getColors") {
    assert(100-getColors(ImageIO.read(new File(s"${files(0)}"))) == 68)
    assert(100-getColors(ImageIO.read(new File(s"${files(1)}"))) == 60)
    assert(100-getColors(ImageIO.read(new File(s"${files(5)}"))) == 68)
    assert(100-getColors(ImageIO.read(new File(s"${files(10)}"))) == 71)
  }
}
