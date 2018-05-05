package com.src.common.utils.hjtech.log;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class FileTable{
        public int    iLogType;
        public String sThread = new String();
        public FileTable(){
                iLogType = 0;
                sThread = "00";
        }
}