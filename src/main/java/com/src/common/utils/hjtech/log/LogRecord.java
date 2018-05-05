package com.src.common.utils.hjtech.log;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */



import java.io.*;
import java.util.*;
//import java.sql.Time;
//import java.text.*;
import java.net.*;


public class LogRecord{
        int LTMAXNUM = LogDefine.LTMAXNUM;
        int ITEMTABLE = LogDefine.ITEMTABLE;
        //Log Directer
        String m_logFolder;
        String m_modulname;
        String m_servername;
        String m_oldDate;

        boolean m_bExit = false;
        //Filelist
        Vector m_filelist = new Vector(LogDefine.MAXFILELIST);
        public static  Object cs = new Object();						//�ļ��б?������

        int	m_filecount;					//�ļ���
        long	m_ThreadCount;					//����ִ�е��߳���
        boolean	m_bHaveClosed;					//�����ļ��رձ�־
        //��ʼ�����ò���
        //Active Table
        boolean	m_bFileActive[] = new boolean[LogDefine.LTMAXNUM];
        boolean	m_bMultiFile;						//ָ���Ƕ��̶߳��ļ��Ͷ��̵߳��ļ�   TRUE:·������(duoduo)��FALSE������(duodan)

        int m_iItemByte[] = new int [ITEMTABLE];
        ActiveTestThread acttestthread;
        public LogRecord()
        {
                m_bExit = true;
                m_ThreadCount = 0;
                m_oldDate = "B";
                m_bHaveClosed = true;

                m_iItemByte[0] = 6;
                m_iItemByte[1] = 11;
                m_iItemByte[2] = 11;
                m_iItemByte[3] = 11;
                m_iItemByte[4] = 2;
                m_iItemByte[5] = 13;
                m_iItemByte[6] = 2;
                m_iItemByte[7] = 2;
                for(int i=8;i< ITEMTABLE;i++)
                        m_iItemByte[i] = -1;
        }




        public int WriteLog(RecordNode lgRecord,int iLogType,String strThreadID)
        {//return----�ɹ�:0 ;
         //ʧ��:-1;(���ݴ���)
         //���δ��ļ�:(�߼�����)
                if(strThreadID=="")
                        return -1;

                String strTemp1 = strThreadID;
                if(m_bExit)
                        return -101;//������б�־����

                if((iLogType<=0)||(iLogType>LTMAXNUM))//��־���ʹ���
                        return -1;

                if(!m_bFileActive[iLogType-1])//��־�ļ�����
                        return -1;


                String strRecord;
                strRecord="";

                String strThread;
                strThread =GetThreadID(strTemp1);

                String strTail;
                strTail = "";

                if( CheckStringValiable(lgRecord.sSubId) == -1 )		//1
                        return -1;
                if( CheckStringValiable(lgRecord.sUserId) == -1 )		//2
                        return -1;
                if( CheckStringValiable(lgRecord.sMMSId) == -1 )		//3
                        return -1;
                if( CheckStringValiable(lgRecord.sNeedReply) == -1 )		//4
                        return -1;
                if( CheckStringValiable(lgRecord.sFeeMobile) == -1 )		//5
                        return -1;
                if( CheckStringValiable(lgRecord.sThreadId) == -1 )		//6
                        return -1;
                if( CheckStringValiable(lgRecord.sPriority) == -1 )		//7
                        return -1;
                if( CheckStringValiable(lgRecord.sMessageId) == -1 )		//8
                        return -1;
                if( CheckStringValiable(lgRecord.sDestId) == -1 )		//9
                        return -1;
                if( CheckStringValiable(lgRecord.sSrcID) == -1 )		//10
                        return -1;
                if( CheckStringValiable(lgRecord.sErrorCode) == -1 )		//11
                        return -1;
                if( CheckStringValiable(lgRecord.sDestMobile) == -1 )		//12
                        return -1;
                if( CheckStringValiable(lgRecord.sSrcMobile) == -1 )		//13
                        return -1;
                if( CheckStringValiable(lgRecord.szContent) == -1 )		//14
                        return -1;


                strTail += GetItemString(iLogType,lgRecord.sSubId,1);
                strTail += "|";
                strTail += GetItemString(iLogType,lgRecord.sUserId,2);
                strTail += "|";
                strTail += GetItemString(iLogType,lgRecord.sMMSId,3);
                strTail += "|";
                strTail += GetItemString(iLogType,lgRecord.sNeedReply,4);
                strTail += "|";
                strTail += GetItemString(iLogType,lgRecord.sFeeMobile,5);
                strTail += "|";
                strTail += GetItemString(iLogType,lgRecord.sThreadId,6);
                strTail += "|";
                strTail += GetItemString(iLogType,lgRecord.sPriority,7);
                strTail += "|";
                strTail += GetItemString(iLogType,lgRecord.sMessageId,8);
                strTail += "|";
                strTail += GetItemString(iLogType,lgRecord.sDestId,9);
                strTail += "|";
                strTail += GetItemString(iLogType,lgRecord.sSrcID,10);
                strTail += "|";
                strTail += GetItemString(iLogType,lgRecord.sErrorCode,11);
                strTail += "|";
                strTail += GetItemString(iLogType,lgRecord.sDestMobile,12);
                strTail += "|";
                strTail += GetItemString(iLogType,lgRecord.sSrcMobile,13);
                strTail += "|";

                String strTemp;
                strTemp  = CheckContentChar(lgRecord.szContent);

                strTail += strTemp;
                strTail += "\r\n";

                int iRet = 0;
                try{
                iRet = Write3Log(iLogType, strThread,strTail);
                }catch (Exception e){
                  iRet = -1;
                }

                return iRet;
        }

        public int WriteLog(RecordNode lgRecord,int iLogType)
        {
                if(m_bExit)
                        return -1;

                int i = WriteLog(lgRecord,iLogType,"00");
                return i;
        }

        public String GetAppPath()
        {
          return System.getProperty("user.dir");
        }


        String GetItemString(int iFileType,String str, int iItem)
            {//ȷ��ÿ����Ժ󣬼�����ַ�ȵļ��
          if(m_iItemByte[iItem]>0)
              {//�����
            while(str.length()<m_iItemByte[iItem])
              str = " " + str;

            if(str.length()>m_iItemByte[iItem])
              str = str.substring(0,m_iItemByte[iItem]);
          }
          return str;
        }

        String CheckContentChar(String str)
        {
          if(str == null)
            str = "";
          int len = str.length();
          if( len > LogDefine.MAXCONTENTLENGTH)
            len = LogDefine.MAXCONTENTLENGTH;
   		  str.replaceAll("|","&#7C");
   		  str.replaceAll("\r","&#OD");
   		  str.replaceAll("\n","&#OA");
          return str.substring(0,len);
        }

        String GetFileType(int type)
        {
          String strRet;
          strRet = "ERR";
          switch(type)
          {
            case LogDefine.LT_GAO:strRet = "GAO";break;
            case LogDefine.LT_GFI:strRet = "GFI";break;
            case LogDefine.LT_GAR:strRet = "GAR";break;
            case LogDefine.LT_GSO:strRet = "GSO";break;
            case LogDefine.LT_GFO:strRet = "GFO";break;
            case LogDefine.LT_GDO:strRet = "GDO";break;
            case LogDefine.LT_GRO:strRet = "GRO";break;
            case LogDefine.LT_GFR:strRet = "GFR";break;
            case LogDefine.LT_GSM:strRet = "GSM";break;
              //��������ʱ��Ҫ�޸�
            case LogDefine.LT_SYS:strRet = "SYS";break;
            default:break;
          }
          return strRet;
        }

        int SetLogFolder(String str)
        {
          m_logFolder = str;
          return 0;
        }




        String GetServerName(String str)
        {
          if(str == "")
          {
            try{
              str = InetAddress.getLocalHost().getHostName();
            }catch(Exception excpt){
            }
          }

          int nPos;
          nPos = str.indexOf("_");
          while(nPos!=-1)
          {
            String str1;
            String str2;
            str1 = str.substring(0,nPos);
            str2 = str.substring(nPos + 1);
            str = str1+str2;
            nPos = str.indexOf("_");
          }


          if(str.length()>8)
            str = str.substring(0,8);

          str = str.toUpperCase();

          return str;
        }



        String GetAppName(String str)
        {
          if(str == "")
            str = "IUNKNOW";

          int nPos;
          nPos = str.indexOf("_");
          while(nPos!=-1)
          {
            String str1;
            String str2;
            str1 = str.substring(0,nPos);
            str2 = str.substring(nPos + 1);
            str = str1+str2;
            nPos = str.indexOf("_");
          }

          if(str.length()>8)
            str = str.substring(0,8);

          str = str.toUpperCase();
          return str;
        }





        String GetThreadID(int iThread)
            {//û�м���Thread��Ч�Լ��
          //�ϵĹ�����ʽ����ݿ������ж��Ƿ���Ҫ��ɶ��ļ�
          //�µķ��鹤����ʽû����һ��
          String strRet;
          strRet = "";
          if(m_bMultiFile)
          {
            String str = new String();
            str = String.valueOf(iThread);

            if(str.length()==1)
              str = "0" + str;//���Ȳ���������

            if(str.length()>2)
              str = "FF";//�����������FF��

            strRet += str;
          }
          else
            strRet += "00";//���õ����߳�

          return strRet;
        }

        String GetThreadID(String strThread)
        {
          int len = strThread.length();
          if(len>2)
            strThread = strThread.substring(0,2);

          while(strThread.length()<2)
            strThread = "0" + strThread;


          strThread = strThread.toUpperCase();
          return strThread;
        }

        void GetDateTime(String strDateTime[])
        {
          Calendar c = Calendar.getInstance();
          String strY;
          String strm;
          String strd;
          String strH;
          String strM;
          String strS;

          strY = String.valueOf(c.get( Calendar.YEAR ));
          strm = String.valueOf(c.get( Calendar.MONTH )+1);
          strd = String.valueOf(c.get(Calendar.DAY_OF_MONTH));

          strH = String.valueOf(c.get( Calendar.HOUR_OF_DAY));
          strM = String.valueOf(c.get( Calendar.MINUTE ));
          strS = String.valueOf(c.get(Calendar.SECOND));



          while(strY.length()<4)
            strY = "0" + strY;
          while(strm.length()<2)
            strm = "0" + strm;
          while(strd.length()<2)
            strd = "0" + strd;
          while(strH.length()<2)
            strH = "0" + strH;
          while(strM.length()<2)
            strM = "0" + strM;
          while(strS.length()<2)
            strS = "0" + strS;

          strDateTime[0] = strY + strm + strd;
          strDateTime[1] = strH + strM + strS;

        }

        String GetFileOprID(int iD) throws OperateIDException
        {
          String str = new String();
          if(iD<=999)
          {
            str = String.valueOf(iD);
            while(str.length()<3)
              str = "0" + str;
          }
          else//iD(>=1000)
          {
            OperateIDException e = new OperateIDException("Error:OperateID Overflow!");
            throw e;
          }
          return str;
        }

        public int CloseLog()
            {//����LOG����,ֱ����һ�ε���INILOG
          //���أ�0 - �ɹ�
          //      -1- ��ʱ
          int iRet = 0;
          m_bExit = true;

          if(m_bHaveClosed)
            return -101;//�Ƿ�ִ��(�Ѿ����ùرպ���)
          else
            m_bHaveClosed = true;

          while(m_ThreadCount>0)
          {
            int a = 0;
            try{
              Thread.sleep(10);
              a++;
              if(a>100)
              {
                iRet = -1;
                break;
              }
            }catch(Exception e){
              iRet = -1;
              break;
            }
          }

          synchronized(cs)
          {
            int i = m_filelist.size();
            for(int b = 0;b<i;b++)
            {
              filenode thisfile = (filenode)m_filelist.get(b);

              thisfile.strLastDate = "";
              thisfile.strThreadID = "";
              if(thisfile.bFileActive)
                try{
                thisfile.file.close();
                }catch(Exception e){
                }
            }
          }

          return iRet;
        }




        public int WriteLog(String strContent,String strErrorCode) throws Exception
            {//return----�ɹ�:0 ;ʧ��:-1;���δ��ļ�:-101;

          CheckStringValiable(strContent);
          
          
		  CheckStringValiable(strErrorCode);
          
          if(m_bExit)
            return -101;//������б�־����




          String strTail;
          strTail =  "";
          strTail += strErrorCode;
          strTail += "|";
          strTail += strContent;
          strTail += "\r\n";
          int iRet = 0;
          //�µķ�������̬
          iRet = Write3Log(LogDefine.LT_SYS,"00",strTail);
        //  iRet =   Write3Log(strErrorCode,"00",strTail);
          return iRet;
        }
        
        public int WriteLog(String strContent,int strErrorCode) throws Exception
        {//return----�ɹ�:0 ;ʧ��:-1;���δ��ļ�:-101;

      CheckStringValiable(strContent);
      
      
	//  CheckStringValiable(strErrorCode);
      
      if(m_bExit)
        return -101;//������б�־����




      String strTail;
      strTail =  "";
      strTail += strErrorCode;
      strTail += "|";
      strTail += strContent;
      strTail += "\r\n";
      int iRet = 0;
      //�µķ�������̬
    //  iRet = Write3Log(LogDefine.LT_SYS,"00",strTail);
      iRet =   Write3Log(strErrorCode,"00",strTail);
      return iRet;
    }


        int Write3Log(int iFileType, String strThreadID,String strContent) throws Exception
            {//��̬��д
          m_ThreadCount++;
          if(m_bExit)
          {
            m_ThreadCount--;
            return -101;
          }

          filenode thisfile;

          int nUsed=0;
          int icount = m_filelist.size();

          for(int i = 0;i<icount;i++)
          {
            if(m_bExit)
            {
            m_ThreadCount--;
            return -101;
          }
          thisfile = (filenode)m_filelist.get(i);
          if((thisfile.iFileType == iFileType) && (thisfile.strThreadID.compareToIgnoreCase(strThreadID) == 0))
                                  {////��λ�ɹ�
            synchronized(filenode.cs){
              try{
                String strTime;
                String strDate;
                String s[] = new String[2];
                GetDateTime(s);
                strDate = s[0];
                strTime = s[1];

                strContent = strTime + "|" + strContent;

                if(thisfile.bFileActive)
                    {//����ļ�����
                  boolean bNewFile = false;
                  //��ȡʱ�䣬�ж��ļ�ʱ�����Ƿ���������

                  //������ļ��裬ʱ���ǰ���˳����е�
                  if(strDate.compareToIgnoreCase(thisfile.strLastDate)==0)
                  {
                    long llength;
                    llength = thisfile.file.length();
                    if(llength >= LogDefine.MAXFILELENGTH)
                                  {	//�ļ�����
                      bNewFile = true;
                      //����ˮ��
                      thisfile.iFileOprtID++;
                    }
                  }
                  else
                  {
                    //ʱ�䲻�Ǻ�
                    bNewFile = true;
                    //��ʱ��
                    thisfile.strLastDate = strDate;
                    //����ˮ��
                    thisfile.iFileOprtID = 0;
                  }



                  if(bNewFile)
                  {
                    String strNewFileName;

                    String str[] = new String [6];

                    str[0] = GetFileType(iFileType) + "_";
                    str[1] = m_servername + "_";
                    str[2] = m_modulname  + "_";
                    str[3] = strDate      + "_";
                    str[4] = GetThreadID(strThreadID)+"_";
                    str[5] = GetFileOprID(thisfile.iFileOprtID);

                    strNewFileName = "";
                    strNewFileName += str[0];
                    strNewFileName += str[1];
                    strNewFileName += str[2];
                    strNewFileName += str[3];
                    strNewFileName += str[4];
                    strNewFileName += str[5];
                    strNewFileName += ".log";

                    strNewFileName = m_logFolder + strNewFileName;
                    thisfile.file.close();


                    thisfile.bFileActive = false;
                    thisfile.file = new RandomAccessFile(strNewFileName, "rw");
                    thisfile.bFileActive = true;
                    //thisfile.file.writeBytes(strContent);
                    thisfile.file.write(strContent.getBytes());

                  }
                  else
                  {
                    //thisfile.file.writeBytes(strContent);
                    thisfile.file.write(strContent.getBytes());
                  }
                }//end if (fileActive)
                else
                {//ԭ���ļ����ڹر�״̬
                  //Ѱ�ҵ�ǰ�ļ�
                  boolean bNewFile = false;

                  String strNowFileName;
                  strNowFileName = "";
                  String str[] = new String [6];

                  if(strDate.compareToIgnoreCase(thisfile.strLastDate)==0)
                  {
                    //ʱ��(����)�Ǻ�
                    //�������ļ�
                    str[0] = GetFileType(iFileType) + "_";
                    str[1] = m_servername + "_";
                    str[2] = m_modulname  + "_";
                    str[3] = strDate      + "_";
                    str[4] = GetThreadID(strThreadID)+"_";
                    str[5] = GetFileOprID(thisfile.iFileOprtID);

                    strNowFileName = "";
                    strNowFileName += str[0];
                    strNowFileName += str[1];
                    strNowFileName += str[2];
                    strNowFileName += str[3];
                    strNowFileName += str[4];
                    strNowFileName += str[5];
                    strNowFileName += ".log";

                    strNowFileName = m_logFolder + strNowFileName;

                    File fs = new File(strNowFileName);
                    if(fs.exists())
                        {//��ȡ��Ϣ�ɹ�(�����ļ���������)
                      long llength = fs.length();
                      if(llength >= LogDefine.MAXFILELENGTH)
                                    {	//�ļ�����
                        //�½��ļ�
                        bNewFile = true;
                        //����ˮ��
                        thisfile.iFileOprtID++;
                      }
                    }
                    else
                    {//��ȡ��Ϣ���ɹ�( �����ļ������ڻ���)
                      //�½��ļ�
                      bNewFile = true;
                      //����ˮ��
                      thisfile.iFileOprtID++;
                    }

                  }
                  else
                  {
                    //ʱ�䣨���ڣ����Ǻ�
                    bNewFile = true;
                    //��ʱ��
                    thisfile.strLastDate = strDate;
                    //����ˮ��
                    thisfile.iFileOprtID = 0;
                  }

                  if(bNewFile)
                      {//�½��ļ�

                    str[0] = GetFileType(iFileType) + "_";
                    str[1] = m_servername + "_";
                    str[2] = m_modulname  + "_";
                    str[3] = strDate      + "_";
                    str[4] = GetThreadID(strThreadID)+"_";
                    str[5] = GetFileOprID(thisfile.iFileOprtID);

                    strNowFileName =  "";
                    strNowFileName += str[0];
                    strNowFileName += str[1];
                    strNowFileName += str[2];
                    strNowFileName += str[3];
                    strNowFileName += str[4];
                    strNowFileName += str[5];
                    strNowFileName += ".log";

                    strNowFileName = m_logFolder + strNowFileName;

                    thisfile.bFileActive = false;
                    thisfile.file = new RandomAccessFile(strNowFileName, "rw");
                    thisfile.bFileActive = true;
                    //thisfile.file.writeBytes(strContent);
                    thisfile.file.write(strContent.getBytes());

      /*
      CFileException fe;
      if(!lflist->file.Open(strNowFileName,CFile::modeCreate|CFile::modeWrite| \
      CFile::modeNoTruncate|CFile::shareDenyWrite|CFile::typeText,&fe))
      {
       lflist->bFileActive = FALSE;
       AfxThrowFileException(fe.m_cause, fe.m_lOsError, fe.m_strFileName);
      }
      else
      {
       lflist->bFileActive = TRUE;
       lflist->file.WriteString(strContent);
       lflist->file.Flush();
      }
      */
                  }
                  else
                  {
                    thisfile.bFileActive = false;
                    thisfile.file = new RandomAccessFile(strNowFileName, "rw");
                    thisfile.bFileActive = true;
                    thisfile.file.seek(thisfile.file.length());
                    //thisfile.file.writeBytes(strContent);
                    thisfile.file.write(strContent.getBytes());

      /*
      CFileException fe;
      if(!lflist->file.Open(strNowFileName,CFile::modeWrite|CFile::shareDenyWrite|CFile::typeText,&fe))
      {
       lflist->bFileActive = FALSE;
       AfxThrowFileException(fe.m_cause, fe.m_lOsError, fe.m_strFileName);
      }
      else
      {
       lflist->bFileActive = TRUE;
       lflist->file.SeekToEnd();
       lflist->file.WriteString(strContent);
       lflist->file.Flush();
      }
      */
                  }

                }
              }catch (Exception e){
                m_ThreadCount--;
                throw e;//����ݸ��쳣
              }

            }//end of synchronized(thisfile.cs){

            m_ThreadCount--;
            return 0;

          }//end ��λ
          }//end for

          m_ThreadCount--;
          return -1;
        }



        int Add2FList(int iFileType,String strThread) throws Exception
        {
          //����ļ����ͳ���
          if(iFileType!=LogDefine.LT_SYS)
            if((iFileType<=0)||(iFileType>LTMAXNUM))
              return -1;//���ʹ���}

            strThread = strThread + ",00,";
            int nPos;
            nPos = strThread.indexOf(",");

            while(nPos != -1)
            {
              String strTemp;
              strTemp = strThread.substring(0,nPos);
              strThread = strThread.substring(nPos + 1);
              nPos  = strThread.indexOf(",");


              if(strTemp.length()==0)
                continue;

              boolean bHave = false;
              int count = m_filelist.size();
              for(int i = 0 ; i<count ; i++)
              {
                filenode thisfile;
                thisfile = (filenode)m_filelist.get(i);

                if( (thisfile.iFileType == iFileType) && (thisfile.strThreadID == GetThreadID(strTemp)))
                {
                  bHave = true;
                  break;
                }
              }

              if(bHave)
                continue;



              if(m_filecount>LogDefine.MAXFILELIST)
              {
                return -1;
              }

              m_filecount++;

              filenode thisfile = new filenode();
              String strDate;
              String strTime;
              String strT[] = new String[2];
              GetDateTime(strT);
              strDate = strT[0];
              strTime = strT[1];
              if(iFileType == LogDefine.LT_SYS)
                strTemp = "00";
              //��ȡ��ǰ����ˮ��

              String str[] = new String[6];
              String strBegin;
              String strEnd;

              str[0] = GetFileType(iFileType) + "_";	//����
              str[1] = m_servername + "_";			//����
              str[2] = m_modulname  + "_";			//ģ��
              str[3] = strDate      + "_";			//ʱ��
              str[4] = GetThreadID(strTemp) + "_";	//�߳�
              str[5] = "*";							//��ˮ

              strBegin =  "";
              strBegin += str[0];
              strBegin += str[1];
              strBegin += str[2];
              strBegin += str[3];
              strBegin += str[4];

              strEnd = ".log";


              //strNowFileName = m_logFolder + strNowFileName;
              boolean bFind = false;
              File finder = new File(m_logFolder);
              String strlist[] = finder.list(new logfilter(strBegin,strEnd));


              String strIniID;
              strIniID = "000";//��ʼ��ˮ��(ASCII)

              String strHead;
              strHead =  "";
              strHead += str[0];
              strHead += str[1];
              strHead += str[2];
              strHead += str[3];
              strHead += str[4];

              int filecount = strlist.length;
              String strOpID = new String();

              for(int fi = 0;fi<filecount;fi++)
              {
                bFind = true;
                strOpID = strlist[fi];
                int nfp = -1;
                nfp = strOpID.indexOf(".log");
                //(strOpID.length() == (strHead.length() + 3 +4) )
                if( nfp != -1){
                  bFind = false;
                  bFind = true;
                  String strK;
                  strK = ":)";
                  strOpID = strOpID.substring(nfp - 3,nfp);

                  Integer intA = new Integer(strOpID);
                  Integer intB = new Integer(strIniID);

                  if(intA.intValue()>intB.intValue())
                    strIniID = strOpID;
                }
              }

              thisfile.iFileOprtID = (new Integer(strIniID).intValue());


              String strNowFileName;

              str[0] = GetFileType(iFileType) + "_";
              str[1] = m_servername + "_";
              str[2] = m_modulname  + "_";
              str[3] = strDate      + "_";
              str[4] = GetThreadID(strTemp)+"_";
              str[5] = GetFileOprID(thisfile.iFileOprtID);

              strNowFileName =  "";
              strNowFileName += str[0];
              strNowFileName += str[1];
              strNowFileName += str[2];
              strNowFileName += str[3];
              strNowFileName += str[4];
              strNowFileName += str[5];
              strNowFileName += ".log";


              strNowFileName = m_logFolder + strNowFileName;

              boolean bNewFile = false;
              if(bFind)
                  {//����ҵ�������ļ�
                File fs = new File(strNowFileName);
                if(fs.exists())
                    {//��ȡ�ļ���Ϣ
                  if(fs.length() >= LogDefine.MAXFILELENGTH)
                                    {//�������һ���ļ�Ҳ����
                    //�½�
                    bNewFile = true;
                    //��ȡ�µ���ˮ��
                    thisfile.iFileOprtID++;
                  }
                  else
                  {
                    //�ļ����,���´��ļ�
                  }
                }
                else
                {//�ļ��쳣���½��ļ�
                  //�½�
                  bNewFile = true;
                  //��ȡ�µ���ˮ��
                  thisfile.iFileOprtID++;
                }
              }
              else
              {//�ļ�û���ҵ�
                //�½�
                bNewFile = true;
              }




              thisfile.bFileActive = true;
              thisfile.iFileType = iFileType;
              thisfile.strLastDate = strDate;
              thisfile.strThreadID = GetThreadID(strTemp);

              if(bNewFile)
                  {//�½��ļ�
                str[0] = GetFileType(iFileType) + "_";
                str[1] = m_servername + "_";
                str[2] = m_modulname  + "_";
                str[3] = strDate      + "_";
                str[4] = GetThreadID(strTemp)+"_";
                str[5] = GetFileOprID(thisfile.iFileOprtID);

                strNowFileName =  "";
                strNowFileName += str[0];
                strNowFileName += str[1];
                strNowFileName += str[2];
                strNowFileName += str[3];
                strNowFileName += str[4];
                strNowFileName += str[5];
                strNowFileName += ".log";

                strNowFileName = m_logFolder + strNowFileName;
                thisfile.bFileActive = false;
                try{
                  thisfile.file = new RandomAccessFile(strNowFileName, "rw");
                  thisfile.bFileActive = true;
                }catch (Exception e){
                  throw e;
                }

              }
              else
              {//��Դ���ļ�
                thisfile.bFileActive = false;
                try{
                  thisfile.file = new RandomAccessFile(strNowFileName, "rw");
                  thisfile.bFileActive = true;
                  thisfile.file.seek(thisfile.file.length());
                }catch (Exception e){
                  throw e;
                }
              }

              m_filelist.add(thisfile);
            }//end while nPos
          return 0;
        }


        public int InitialLog(InitialFile iniParam) throws Exception
        {
          m_filecount = 0;
          if(!m_bExit)
            return -101;//�Ƿ�������������������
          try
          {
            //��ȡĿ¼��
            m_logFolder = iniParam.sFolder;

            if(m_logFolder.indexOf(":")==-1)//ʹ��ȱʡ��Ŀ¼
            {
              int nPos = m_logFolder.indexOf("\\");
              m_logFolder = m_logFolder.substring(nPos+1);
              String strTemp;
              strTemp = GetAppPath();
              strTemp += "\\";

              m_logFolder = strTemp+m_logFolder;
            }
            if((m_logFolder.charAt(m_logFolder.length()-1)!= '\\')||(m_logFolder.charAt(m_logFolder.length()-1)!='/'))
            {
              m_logFolder += "\\";
            }

            //��ȡServer&App��
            m_modulname = GetAppName(iniParam.sModule);
            m_servername =  GetServerName(iniParam.sServer);

            //��ʼ������?�ļ�д����
            for(int i=0;i<LogDefine.LTMAXNUM;i++)
            {
              m_bFileActive[i] = true;
            }

            //��¼�Ѿ���ʼ���˵��ļ����ͣ�Ϊ������Ч������ж�������
            boolean bFileTypeIni[][] = new boolean[LogDefine.LTMAXNUM][2];
            for(int i =0 ;i<LogDefine.LTMAXNUM ; i++)
            {
              bFileTypeIni[i][0] = false;//ishave
              bFileTypeIni[i][1] = false;//isbad
            }

            //��ʼ���ļ��б�
            Add2FList(LogDefine.LT_SYS,"00");//ϵͳ��־

            for(int i =0 ;i<LogDefine.LTMAXNUM ; i++)
            {
              iniParam.ftlist[i].sThread="";
              int iRet = Add2FList(iniParam.ftlist[i].iLogType,iniParam.ftlist[i].sThread);
              if((iniParam.ftlist[i].iLogType>0)&&(iniParam.ftlist[i].iLogType<=LogDefine.LTMAXNUM))
              {
                bFileTypeIni[iniParam.ftlist[i].iLogType - 1][0] = true;
                if(iRet!=0)
                  bFileTypeIni[iniParam.ftlist[i].iLogType - 1][1] = true;
              }
            }
            m_bExit = false;
            //g_Exit = m_bExit;
            m_ThreadCount  =0;
            m_bHaveClosed  = false;
            String strTime;
            String strTemp[] = new String [2];

            GetDateTime(strTemp);
            m_oldDate = strTemp[0];
            strTime = strTemp[1];

            //��ʱ��
            //AfxBeginThread(ActiveTestThread, this,THREAD_PRIORITY_BELOW_NORMAL);
            acttestthread = new ActiveTestThread(this);
            acttestthread.start();

            boolean bOk = true;
            for(int i=0;i<LogDefine.LTMAXNUM;i++)
                  {//���16���ļ���������
              if((m_bFileActive[i] == true) && (bFileTypeIni[i][0] == false))
              {
              bOk = false;
            }

            if((m_bFileActive[i] == true) && (bFileTypeIni[i][0] == true) && (bFileTypeIni[i][1] == true))
            {
              bOk = false;
            }
            }

            if(bOk==false)
              return -1;

            return 0;
          }catch(Exception e){
            CloseLog(); //��ʼ���쳣���رգ����³�ʼ��
            throw e;
          }
        }

        public void CheckActive()
        {
          String strTemp[] = new String [2];
          String strTime;
          String strDate;
          GetDateTime(strTemp);
          strDate = strTemp[0];
          strTime = strTemp[1];

          int count = m_filelist.size();
          for(int i=0;i<count;i++)
          {
            if(m_bExit)
            {
            return;
          }

          filenode lflist = (filenode)m_filelist.get(i);
          synchronized(filenode.cs)
          {
            if(lflist.bFileActive)
                {//�ļ��򿪵�ʱ�������Щ���
              boolean bClose = false;
              //������ڱ��//�ر��ļ�
              if(strDate.compareToIgnoreCase(m_oldDate)!=0)
                bClose = true;
              else
              {
                String str[] = new String [5];
                str[0] = GetFileType(lflist.iFileType) + "_";
                str[1] = m_servername + "_";
                str[2] = m_modulname  + "_";
                str[3] = lflist.strLastDate      + "_";
                str[4] = GetThreadID(lflist.strThreadID)+"_";
                try{
                str[5] = GetFileOprID(lflist.iFileOprtID);
                }catch (Exception e){
                  return;
                }


                String strNowFileName;
                strNowFileName =  "";
                strNowFileName += str[0];
                strNowFileName += str[1];
                strNowFileName += str[2];
                strNowFileName += str[3];
                strNowFileName += str[4];
                strNowFileName += str[5];
                strNowFileName += ".log";

                strNowFileName = m_logFolder + strNowFileName;

                File fs = new File(strNowFileName);

                Calendar c2 = Calendar.getInstance();
                c2.setTime( new Date(fs.lastModified()));

                int iLastHour = c2.get(Calendar.HOUR);
                int iLastMinu = c2.get(Calendar.MINUTE);

                int iNowHour = (new Integer(strTime.substring(0,2))).intValue();
                int iNowMinu = (new Integer(strTime.substring(2,4))).intValue();


                if( ((iNowHour - iLastHour)*60 + iNowMinu - iLastMinu) > LogDefine.UNMODIFYTIME )
                  bClose = true;

              }

              if(bClose)
              {
                try{
                lflist.file.close();
                }catch(Exception e){
                  lflist.bFileActive=false;
                }
                lflist.bFileActive = false;
              }

            }

          }

          }

          m_oldDate = strDate;
          return ;
        }

        int CheckStringValiable(String str)
        {
          if(str == null)
            str = "";
          //�����Ч��    

 		  str.replaceAll("|","&#7C");
 		  str.replaceAll("\r","&#OD");
   		  str.replaceAll("\n","&#OA");

          return 0;
        }

        String GetItemString(int iLogType, int iNum, int iItem)
        {
          if(iNum<=0)
            return "0";

          String str = new String();
          str = String.valueOf(iNum);
          String strRet;
          strRet = GetItemString(iLogType,str,iItem);
          return strRet;
        }

}

class OperateIDException extends Exception
{
        public OperateIDException(String msg)
        {
                super(msg);
        }
}



class logfilter extends Object implements FilenameFilter
{
      String   matchBegin;
      String   matchEnd;
      public logfilter(String matchB,String matchE)
      {
        matchBegin = matchB;
        matchEnd = matchE;
      }
      public boolean accept(File dir,String name)
      {
        if(name.endsWith(matchEnd) && name.startsWith(matchBegin))
          return true;
        return false;
      }
}




//////////////////////////////////////////////////////////////////////

class filenode{
        int	iFileType;//�ļ�����
        String  strThreadID;//�̱߳�ʶ
        String  strLastDate;//���д������
        int	iFileOprtID;//��ˮ��
        boolean	bFileActive;//�ļ�״̬���򿪣��رգ�
        public RandomAccessFile    file;	    //file
        public static  Object  cs = new Object();	    //�ļ��ڵ㱣������
}



class ActiveTestThread extends Thread
{
         public LogRecord log;
         int CHECK_TIME_STEP = LogDefine.CHECK_TIME_STEP;
          ActiveTestThread(LogRecord p) {
                   log = p;
          }

          public void run(){
                long ltime = CHECK_TIME_STEP*60*100;
                long count=0;

                while(true)
                {
                  if(log.m_bExit)
                    break;
                  count++;
                  try{
                  	Thread.sleep(10);
                  }catch(Exception e){
                  	continue;
                  }
                  if(count>=ltime)
                  {
 	                 log.CheckActive();
                     count = 0;
                  }
  		      }
          }
}
