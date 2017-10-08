package jp.riku1227.mcbetool.util

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream

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
    }
}