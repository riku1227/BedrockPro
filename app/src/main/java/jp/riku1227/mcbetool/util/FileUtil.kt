package jp.riku1227.mcbetool.util

import android.os.Environment
import java.io.*

class FileUtil {
    companion object {
        fun copyFile(inFilePath : String,outFilePath : String) {
            val bufferedInput = BufferedInputStream(FileInputStream(inFilePath))
            val bufferedOutput = BufferedOutputStream(FileOutputStream(outFilePath))
            var i = 0
            val b = ByteArray(8192)
            while ({i = bufferedInput.read(b); i}() != -1) {
                bufferedOutput.write(b,0,i)
            }
            bufferedOutput.flush()
            bufferedInput.close()
            bufferedOutput.close()
        }

        fun createDirectory(path : String) {
            val dirs : List<String> = path.split("/")
            var filePath = getExternalStoragePath()
            for (subFilePath in dirs.iterator()) {
                filePath += "/" + subFilePath
                val file = File(filePath)
                if(!file.exists()) {
                    file.mkdir()
                }
            }
        }

        fun createFile(filePath : String) {
            val file = File(filePath)
            file.createNewFile()
        }

        fun getExternalStoragePath() : String {
            return Environment.getExternalStorageDirectory().absolutePath+"/"
        }
    }
}