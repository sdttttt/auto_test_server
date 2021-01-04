package com.auto.test.utils;

import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.List;

@Slf4j
public class ZipUtils {
  /**
   * 判断zip文件是否为jmeter模板文件
   *
   * @param zipPathName
   * @return
   */
  public static boolean isExistsInZip(String zipPathName, String zipName) {
    try {
      ZipFile zipFile = new ZipFile(zipPathName);
      return isExistsInZip(zipFile, zipName);
    } catch (ZipException e) {
      return false;
    }
  }
  
  /**
   * 判断zip文件路径是否为存在在zip文件中
   *
   * @param zipFile
   * @return
   */
  public static boolean isExistsInZip(ZipFile zipFile, String zipName) {
    List<FileHeader> fileHeaders;
    try {
      zipFile.setFileNameCharset("GBk");
      fileHeaders = zipFile.getFileHeaders();
    } catch (ZipException e) {
      return false;
    }
    for (FileHeader fileHeader : fileHeaders) {
      if (fileHeader.getFileName().equals(zipName)) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * 将ZipFile zipFile对象的 List<FileHeader> datas 的对象 压缩到ZipFile desZipFile 对象
   *
   * @param zipFile    原zip
   * @param desZipFile 目标zip
   * @param datas      原zip中需要移动的文件
   * @param oldRootDir 原zip的跟路径
   * @param newRootDir 新zip的根路径
   * @return
   * @throws Exception
   */
  public static String getJmeterDataPath(ZipFile zipFile, ZipFile desZipFile, List<FileHeader> datas, String oldRootDir, String newRootDir) throws Exception {
    String dataPath = "";
    ZipParameters zipParameters = new ZipParameters();
    zipParameters.setSourceExternalStream(true);
    zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
    for (FileHeader datasFileHeader : datas) {
      String fileNameInZip = datasFileHeader.getFileName().replace(oldRootDir, newRootDir);
      zipParameters.setFileNameInZip(fileNameInZip);
      ZipInputStream inputStream1 = zipFile.getInputStream(datasFileHeader);
      if (ZipUtils.isExistsInZip(desZipFile, fileNameInZip)) {
        desZipFile.removeFile(fileNameInZip);
      }
      desZipFile.addStream(inputStream1, zipParameters);
      inputStream1.close();
      dataPath = dataPath + datasFileHeader.getFileName().replace(oldRootDir + "data/", "") + ",";
    }
    return dataPath;
  }
  
  public static boolean addStingToZip(String zipPathName, String content, String desZipName) throws Exception {
    InputStream inputStream = IOUtils.toInputStream(content, "utf-8");
    addInputStreamToZip(zipPathName, inputStream, desZipName);
    inputStream.close();
    return true;
  }
  
  public static boolean addInputStreamToZip(String zipPathName, InputStream inputStream, String desZipName) throws Exception {
    ZipFile zipFile = new ZipFile(zipPathName);
    addInputStreamToZip(zipFile, inputStream, desZipName);
    return true;
  }
  
  public static boolean addInputStreamToZip(ZipFile zipFile, InputStream inputStream, String desZipName) throws Exception {
    zipFile.setFileNameCharset("GBK");
    
    ZipParameters zipParameters = new ZipParameters();
    zipParameters.setSourceExternalStream(true);
    zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
    zipParameters.setFileNameInZip(desZipName);
    if (ZipUtils.isExistsInZip(zipFile, desZipName)) {
      zipFile.removeFile(desZipName);
    }
    zipFile.addStream(inputStream, zipParameters);
    return true;
  }
  
  public static ZipInputStream getInputStream(String zipPathName, String zipName) throws Exception {
    ZipFile zipFile = new ZipFile(zipPathName);
    zipFile.setFileNameCharset("GBK");
    FileHeader fileHeader = zipFile.getFileHeader(zipName);
    
    if (fileHeader != null) {
      return zipFile.getInputStream(fileHeader);
    } else {
      return null;
    }
  }
  
  public static void removeFile(String zipPathName, String zipName) throws Exception {
    ZipFile zipFile = new ZipFile(zipPathName);
    removeFile(zipFile, zipName);
  }
  
  public static void removeFile(ZipFile zipFile, String zipName) throws Exception {
    zipFile.setFileNameCharset("GBK");
    FileHeader fileHeader = zipFile.getFileHeader(zipName);
    if (fileHeader != null) {
      zipFile.removeFile(fileHeader);
    }
  }
  
  public static void renameFile(String zipPathName, String oldName, String newName) throws Exception {
    ZipParameters zipParameters = new ZipParameters();
    zipParameters.setFileNameInZip(newName);
    zipParameters.setSourceExternalStream(true);
    
    ZipFile zipFile = new ZipFile(zipPathName);
    zipFile.setFileNameCharset("GBK");
    FileHeader fileHeader = zipFile.getFileHeader(oldName);
    if (fileHeader != null) {
      ZipInputStream inputStream = zipFile.getInputStream(fileHeader);
      InputStream bufferedInputStream = IOUtils.toBufferedInputStream(inputStream);
      zipFile.addStream(bufferedInputStream, zipParameters);
      inputStream.close();
      bufferedInputStream.close();
      removeFile(zipFile, oldName);
    }
  }
  
  private String getGbkEncodedFileName(String fileName) throws Exception {
    return new String(fileName.getBytes("Cp437"), "GBK");
  }
  
}
