package cn.edu.nju.software.judge.submission;

import lombok.*;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

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
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RedisSubmission implements Serializable {

    private int runId;

    private int problemId;

    private int language;

    private int time;

    private int memory;

    private String source;

    private int userId;

    private int type = 1;


    public  CompileRequest compileRequest(){


        CompileRequest compileRequest = new CompileRequest();

        BeanUtils.copyProperties(this,compileRequest);


        return compileRequest;
    }

    public  RunRequest runRequest(){

        RunRequest runRequest = new RunRequest();
        BeanUtils.copyProperties(this,runRequest);

        return runRequest;
    }

}
