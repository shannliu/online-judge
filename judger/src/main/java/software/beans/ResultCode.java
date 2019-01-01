package software.beans;

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


    WT(0,"Waiting","等待"),
    RJ_WT(1,"Rejudge_Waiting","重判等待中"),
    CI(2,"Compiling","编译中"),
    RI(3,"Running","运行中"),
    AC(4, "Accepted", "正确"),
    PE(5,"Presentation_Error","格式错误"),
    WA(6,"Wrong_Answer","答案错误"),
    TLE(7,"Time_Limit_Exceed","时间超限"),
    MLE(8,"Memory_Limit_Exceed","内存超限"),
    OLE(9,"Output_Limit_Exceed","输出超限"),
    RE(10,"Runtime_Error","运行错误"),
    CE(11,"Compile_Error","编译错误"),
    CO(12,"Compile_OK","编译成功");
    private int code;

    private String status;

    private String statusCanonical;


    ResultCode(int code, String status, String statusCanonical) {
        this.code = code;
        this.status = status;
        this.statusCanonical = statusCanonical;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
