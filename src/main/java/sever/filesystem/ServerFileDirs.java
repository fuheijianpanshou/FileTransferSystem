package sever.filesystem;

import java.io.File;

public class ServerFileDirs {
    public static ServerFileDirs IN=new ServerFileDirs();
    public ServerFileDirs(){

    }

    public File[] getFileList(String path){
        File file=new File(path);
        File[] arry=file.listFiles();
        return arry;
    }

}
