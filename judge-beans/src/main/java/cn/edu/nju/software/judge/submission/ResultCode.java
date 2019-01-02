package cn.edu.nju.software.judge.submission;

/**
 * ////////////////////////////////////////////////////////////////////
 * //                          _ooOoo_                               //
 * //                         o8888888o                              //
 * //                         88" . "88                              //
 * //                         (| ^_^ |)                              //
 * //                         O\  =  /O                              //
 * //                      ____/`---'\____                           //
 * //                    .'  \\|     |//  `.                         //
 * //                   /  \\|||  :  |||//  \                        //
 * //                  /  _||||| -:- |||||-  \                       //
 * //                  |   | \\\  -  /// |   |                       //
 * //                  | \_|  ''\---/''  |   |                       //
 * //                  \  .-\__  `-`  ___/-. /                       //
 * //                ___`. .'  /--.--\  `. . ___                     //
 * //              ."" '<  `.___\_<|>_/___.'  >'"".                  //
 * //            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
 * //            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
 * //      ========`-.____`-.___\_____/___.-`____.-'========         //
 * //                           `=---='                              //
 * //      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
 * //            佛祖保佑       永不宕机     永无BUG                    //
 * ////////////////////////////////////////////////////////////////////
 */
public enum  ResultCode {


    WT((short)0,"Waiting","等待"),
    RJ_WT((short)1,"Rejudge_Waiting","重判等待中"),
    CI((short)2,"Compiling","编译中"),
    RI((short)3,"Running","运行中"),
    AC((short)4, "Accepted", "正确"),
    PE((short)5,"Presentation_Error","格式错误"),
    WA((short)6,"Wrong_Answer","答案错误"),
    TLE((short)7,"Time_Limit_Exceed","时间超限"),
    MLE((short)8,"Memory_Limit_Exceed","内存超限"),
    OLE((short)9,"Output_Limit_Exceed","输出超限"),
    RE((short)10,"Runtime_Error","运行错误"),
    CE((short)11,"Compile_Error","编译错误"),
    CO((short)12,"Compile_OK","编译成功");

    private short code;

    private String status;

    private String statusCanonical;


    ResultCode(short code, String status, String statusCanonical) {
        this.code = code;
        this.status = status;
        this.statusCanonical = statusCanonical;
    }

    public short getCode() {
        return code;
    }

    public void setCode(short code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCanonical() {
        return statusCanonical;
    }

    public void setStatusCanonical(String statusCanonical) {
        this.statusCanonical = statusCanonical;
    }}
