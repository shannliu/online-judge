package cn.edu.nju.software.judge.model;

import cn.edu.nju.software.judge.beans.Submission;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class SubmissionModel implements Serializable {

    private static final long serialVersionUID = 4372507055455356050L;

    private Integer submissionId;

    private Integer problemId;

    private Integer contestId;

    private String contestNum;

    private Integer userId;

    private String userName;

    private Short result;

    private Integer time;

    private Integer memory;

    private LocalDateTime submitTime;

    private Integer language;

    private Short open;

    private String dispLanguage;

    private String username;

    private String statusCanonical;

    private Integer sourceLength;

    private Byte valid;

    private LocalDateTime judgeTime;

    private BigDecimal passRate;

    private String judger;

    private LocalDateTime addDatetime;

    private LocalDateTime updateDatetime;

    private Integer del;


    public Submission submission(){

        Submission submission = new Submission();
        BeanUtils.copyProperties(this,submission);
        return submission;
    }


}
