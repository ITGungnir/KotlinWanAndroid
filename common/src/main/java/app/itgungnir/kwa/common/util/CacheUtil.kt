package app.itgungnir.kwa.common.util

import android.app.Application
import java.io.File
import java.math.BigDecimal

class CacheUtil : Util {

    lateinit var cacheFile: File

    companion object {
        val instance by lazy { CacheUtil() }
    }

    override fun init(application: Application) {
        val filePath = "${application.cacheDir.absolutePath}${File.separator}data${File.separator}CacheFile"
        this.cacheFile = File(filePath)
        if (!cacheFile.exists()) {
            cacheFile.mkdirs()
        }
    }

    /**
     * 获取缓存大小
     */
    fun getCacheSize(): String {
        if (!cacheFile.exists()) {
            cacheFile.mkdirs()
        }
        return getFormatSize(getFolderSize(cacheFile).toDouble())
    }

    /**
     * 清除缓存
     */
    fun clearCache(file: File = cacheFile): Boolean {
        if (file.exists() && file.isDirectory) {
            val children = file.list()
            for (aChildren in children) {
                val success = clearCache(File(file, aChildren))
                if (!success) {
                    return false
                }
            }
        }
        return file.delete()
    }

    private fun getFolderSize(file: File): Long {
        var size: Long = 0
        try {
            val fileList = file.listFiles()
            for (aFileList in fileList) {
                // 如果下面还有文件
                size += if (aFileList.isDirectory) {
                    getFolderSize(aFileList)
                } else {
                    aFileList.length()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return size
    }

    private fun getFormatSize(size: Double): String {
        val kiloByte = size / 1024
        if (kiloByte < 1) {
            return "0KB"
        }

        val megaByte = kiloByte / 1024
        if (megaByte < 1) {
            val result1 = BigDecimal(java.lang.Double.toString(kiloByte))
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "KB"
        }

        val gigaByte = megaByte / 1024
        if (gigaByte < 1) {
            val result2 = BigDecimal(java.lang.Double.toString(megaByte))
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "MB"
        }

        val teraBytes = gigaByte / 1024
        if (teraBytes < 1) {
            val result3 = BigDecimal(java.lang.Double.toString(gigaByte))
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "GB"
        }
        val result4 = BigDecimal(teraBytes)
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB"
    }
}