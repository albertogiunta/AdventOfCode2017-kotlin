import java.io.File
import java.io.InputStream

fun readInputFromFile(name: String): String {
    val inputStream: InputStream = File("src/inputs/$name").inputStream()
    return inputStream.bufferedReader().use { it.readText() }
}