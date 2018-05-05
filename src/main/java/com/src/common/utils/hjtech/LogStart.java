package com.src.common.utils.hjtech;

import com.src.common.utils.hjtech.log.InitialFile;
import com.src.common.utils.hjtech.log.LogDefine;
import com.src.common.utils.hjtech.util.LogTool;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


public class LogStart extends HttpServlet {

	
	private static InitialFile inifile;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	
		try{
			LogTool.m_log.InitialLog(inifile);
		}catch(Exception e){
			System.out.println("HjtechMain-Process:初始化日志失败!" + e.getMessage() );
		}
	}
	
	
	
//	定义静态的全局使用的变量
	static{
		    inifile = new InitialFile();
		     inifile.ftlist[0].iLogType = LogDefine.LT_GAO;
		    inifile.ftlist[1].iLogType = LogDefine.LT_GSO;
		    inifile.ftlist[2].iLogType = LogDefine.LT_GDO;
		    //inifile.ftlist[3].iLogType = LogDefine.LT_GRO;
		    inifile.ftlist[4].iLogType = LogDefine.LT_SYS;
		    inifile.ftlist[5].iLogType = LogDefine.LT_GFO;
		    //inifile.ftlist[6].iLogType = LogDefine.LT_GSM;
		    inifile.sFolder = "d://log//rabbit";
		    inifile.sModule = "HJ";
		  }
	
	
	public static void main(String[] args) {
		double x = 234.33;
		System.out.println((int) x);
	}
}
