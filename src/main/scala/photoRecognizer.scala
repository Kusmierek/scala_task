import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import com.typesafe.config._


def getListOfFiles(dir: String):List[File] = {
  val d = new File(dir)
  if (d.exists && d.isDirectory) {
    d.listFiles.filter(_.isFile).toList
  } else {
    List[File]()
  }
}


def getColors (photo: BufferedImage): Int = {
  var average = 0.0
  val originalImage: BufferedImage = photo

  // Resize
  val resized = originalImage.getScaledInstance(10, 10, java.awt.Image.SCALE_DEFAULT)

  // Saving Image
  val bufferedImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB)
  bufferedImage.getGraphics.drawImage(resized, 0, 0, null)

//  Getting value of Brightness
  val valuesOf = 0.to(9).foreach((x)=>0.to(9).foreach((y)=> {
    val color = bufferedImage.getRGB(x, y)
    val red = (color >>> 16) & 0xFF
    val green = (color >>> 8) & 0xFF
    val blue = (color >>> 0) & 0xFF
    val luminance = (red * 0.21 + green * 0.71 + blue * 0.08)/255
    average = average + luminance

  }
  ))
  val points = java.lang.Math.round(average).toInt
  return points
}


def checkBrightnessAndSave(dir: String, files: List[File], cut_off: Int ): List[File] = {
  files.map((x)=> {
    val photo = ImageIO.read(new File(s"$x"))
    val name  = x.getName().split("\\.").toList
    val points = getColors(photo)
    if(points<cut_off){
       ImageIO.write(photo, "jpg", new File(s"${dir}/${name(0)}_dark_${100-points}.${name(1)}"))
    }
    else{
      ImageIO.write(photo, "jpg", new File(s"${dir}/${name(0)}_bright_${100-points}.${name(1)}"))
    }
    })
    return getListOfFiles(dir)
  }

@main
def main():Unit= {
  val conf = ConfigFactory.load()
  val cut_off = conf.getInt("configs.cut_off")
  val input = conf.getString("configs.input")
  val output = conf.getString("configs.output")
  val files = getListOfFiles(input)
  println(checkBrightnessAndSave(output,files,cut_off))

  }
