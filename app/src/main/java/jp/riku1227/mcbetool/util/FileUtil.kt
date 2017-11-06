package jp.riku1227.mcbetool.util

import android.os.Environment
import java.io.*
import java.util.zip.ZipInputStream



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
            var filePath = ""
            for (subFilePath in dirs.iterator()) {
                filePath += "/" + subFilePath
                val file = File(filePath)
                if(!file.exists()) {
                    file.mkdir()
                }
            }
        }

        fun createFile(path : String) {
            val dirs : List<String> = path.split("/")
            var filePath = ""
            var i = 0
            for (subFilePath in dirs.iterator()) {
                i++
                filePath += "/" + subFilePath
                val file = File(filePath)
                if(i == dirs.size) {
                    if(!file.exists()) {
                        file.createNewFile()
                    }
                }
                else {
                    if(!file.exists()) {
                        file.mkdir()
                    }
                }
            }
        }

        fun deleteFile(path : String) {
            val file = File(path)
            if(file.isDirectory) {
                for (subFile in file.listFiles()) {
                    deleteFile(subFile.absolutePath)
                }
            }
            file.delete()
        }

        fun deleteFile(path : String, filter : String) {
            val file = File(path)
            if(!file.absolutePath.contains(filter)) {
                if(file.isDirectory) {
                    for (subFile in file.listFiles()) {
                        deleteFile(subFile.absolutePath,filter)
                    }
                }
                file.delete()
            }
        }

        fun unzip(inputPath : String, outputPath : String) {
            val zipInputStream = ZipInputStream(FileInputStream(inputPath))

            var entry = zipInputStream.nextEntry

            while (entry != null) {
                if(entry.isDirectory) {
                    continue
                }

                createFile(outputPath+entry.name)

                val bufferedOutputStream = BufferedOutputStream(FileOutputStream(outputPath + entry.name))
                var temp = 0
                val byte = ByteArray(8192)
                while ({temp = zipInputStream.read(byte); temp}() != -1) {
                    bufferedOutputStream.write(byte,0,temp)
                }
                bufferedOutputStream.flush()
                bufferedOutputStream.close()

                entry = zipInputStream.nextEntry
            }
            zipInputStream.close()
        }

        fun unzip(inputPath : String, outputPath : String, filter : String) {
            val zipInputStream = ZipInputStream(FileInputStream(inputPath))

            var entry = zipInputStream.nextEntry

            while (entry != null) {
                if(entry.isDirectory) {
                    continue
                }

                if(entry.name.contains(filter)) {
                    createFile(outputPath+entry.name)

                    val bufferedOutputStream = BufferedOutputStream(FileOutputStream(outputPath + entry.name))
                    var temp = 0
                    val byte = ByteArray(8192)
                    while ({temp = zipInputStream.read(byte); temp}() != -1) {
                        bufferedOutputStream.write(byte,0,temp)
                    }
                    bufferedOutputStream.flush()
                    bufferedOutputStream.close()
                }
                entry = zipInputStream.nextEntry
            }
            zipInputStream.close()
        }

        fun createTxtFile(path : String,content : String) {
            val txtFile = File(path)
            if(!txtFile.exists()) {
                txtFile.createNewFile()
            }
            txtFile.writeText(content)
        }

        fun getFolderSize(path : String) : Int {
            var fileSize = 0
            val file = File(path)
            if(!file.exists()) {
                return fileSize
            } else {
                if(file.isDirectory) {
                    val files = file.listFiles()
                    if(files != null) {
                        for (i in 0 until files.size) {
                            fileSize += getFolderSize(files[i].absolutePath)
                        }
                    }
                } else {
                    fileSize = file.length().toInt()
                }
            }
            return fileSize
        }

        fun getExternalStoragePath() : String {
            return Environment.getExternalStorageDirectory().absolutePath+"/"
        }
    }
}