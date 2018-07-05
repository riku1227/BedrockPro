package com.riku1227.bedrockpro.util

import java.io.*
import java.util.zip.ZipInputStream

class FileUtil {
    companion object {

        fun createFile(file : File) {
            val dirs : List<String> = file.absolutePath.split("/")
            var filePath = ""
            var i = 0
            for (subFilePath in dirs.iterator()) {
                i++
                filePath += "/$subFilePath"
                val newFile = File(filePath)
                if(i == dirs.size) {
                    if(!newFile.exists()) {
                        newFile.createNewFile()
                    }
                }
                else {
                    if(!newFile.exists()) {
                        newFile.mkdir()
                    }
                }
            }
        }


        fun unzip(input: File, output: File, filter: String? = null) {
            output.mkdirs()
            val zipInput = ZipInputStream(FileInputStream(input))
            var entry = zipInput.nextEntry

            fun copyEntryFile() {
                createFile(File(output, entry.name))

                val bufferedOutputStream = BufferedOutputStream(FileOutputStream(File(output, entry.name)))
                var temp = 0
                val byte = ByteArray(8192)
                while ({temp = zipInput.read(byte); temp}() != -1) {
                    bufferedOutputStream.write(byte,0,temp)
                }
                bufferedOutputStream.flush()
                bufferedOutputStream.close()
            }

            while (entry != null) {
                if(entry.isDirectory) {
                    entry = zipInput.nextEntry
                    continue
                }

                if(filter != null) {
                    if(entry.name.contains(filter)) {
                        copyEntryFile()
                    }
                } else {
                    copyEntryFile()
                }

                entry = zipInput.nextEntry
            }
        }
    }
}