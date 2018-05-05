/*
 * Created on 2005-11-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.src.common.utils.hjtech.util;

import com.src.common.utils.hjtech.log.LogDefine;
import com.src.common.utils.hjtech.log.RecordNode;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LogFile {

  public LogFile(String fileName) {
    this.fileName = fileName;
  }

  public void write(String content) {
    write(content, false);
  }

  public void write(String content, boolean echo) {
    RecordNode rd = new RecordNode();
    rd.sDestMobile = fileName;
    rd.szContent = content;
    LogTool.m_log.WriteLog(rd, LogDefine.LT_GSM);

    if (echo) {
      System.out.println(content);
    }
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  private String fileName;
}