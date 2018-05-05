package com.src.common.utils.hjtech.util;
import com.src.common.utils.hjtech.log.LogRecord;

import java.util.Map;

/**
 * @author phan
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LogTool {	
	
	public static LogRecord m_log = new LogRecord();
	
	public static void WriteLog(String content,String errcode)
	{
		try{
		 int iRet = m_log.WriteLog(content,errcode);
         }catch(Exception e){}
      
	}
	public static void WriteLog(String content)
	{
		WriteLog(content,"G100");	
		System.out.println(content);
	}	
	
	public static void WriteLog(String content,int errcode)
	{
		try{
		 int iRet = m_log.WriteLog(content,errcode);
         }catch(Exception e){}
      
	}
	
}
