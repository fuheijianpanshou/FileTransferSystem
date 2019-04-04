package command;

public interface Command {
    Byte LOGIN_REQUEST=1;
    Byte LOGIN_RESPONSE=2;
    Byte LS_REQUEST=3;
    Byte LS_RESPONSE=4;
    Byte CD_REQUEST=5;
    Byte CD_RESPONSE=6;
    Byte DOWNFILE_REQUEST=7;
    Byte DOWNFILE_RESPONSE=8;
    Byte UPFILE_REQUEST=9;
    Byte UPFILE_RESPONSE=10;
    Byte LS_CLIENT=11;
}
