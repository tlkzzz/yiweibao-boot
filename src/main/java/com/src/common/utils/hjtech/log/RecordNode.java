package com.src.common.utils.hjtech.log;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class RecordNode
{
        public String sTime = new String("");//ʱ��HHMMSS			//�ڲ���ȡ
        public String sSubId = new String("");
        public String sUserId = new String("");
        public String sMMSId = new String("");
        public String sNeedReply = new String("");
        public String sFeeMobile = new String("");
        public String sThreadId = new String("");
        public String sPriority = new String("");

        public String sMessageId = new String("");
        public String sDestId = new String("");
        public String sSrcID = new String("");
        public String sErrorCode = new String("");
        public String sDestMobile = new String("");
        public String sSrcMobile = new String("");

        public String szContent = new String("");	//��Ϣ

        public RecordNode()
        {

        }

        public void SetContent(String str)
        {
                int len = str.length();
                if(len<=0)
                        return;
                if(len > LogDefine.MAXCONTENTLENGTH)
                        len = LogDefine.MAXCONTENTLENGTH;
                szContent = str.substring(0,len);
        }
}
