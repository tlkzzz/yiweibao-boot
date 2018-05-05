package com.src.common.utils.hjtech.log;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class InitialFile{
        final int LTMAXNUM = LogDefine.LTMAXNUM;
        public String		sFolder;
        public String		sServer;
        public String		sModule;
        public FileTable   ftlist[] = new FileTable[LTMAXNUM];

        public InitialFile(){
                sFolder = "";
                sServer = "";
                sModule = "";
                for(int i=0;i<LTMAXNUM;i++)
                ftlist[i] = new FileTable();
        }
}
