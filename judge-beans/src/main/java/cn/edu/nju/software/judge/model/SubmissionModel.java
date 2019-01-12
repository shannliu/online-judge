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

    private String statusCanonical;

    private Integer sourceLength;

    private Byte valid;

    private Byte judgeType;

    private LocalDateTime judgeTime;

    private BigDecimal passRate;

    private String judger;

    private LocalDateTime addDatetime;

    private LocalDateTime updateDatetime;

    private Integer del;

    private String source;


    public Submission submission(){

        Submission submission = new Submission();
        BeanUtils.copyProperties(this,submission);
        return submission;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Integer submissionId) {
        this.submissionId = submissionId;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public String getContestNum() {
        return contestNum;
    }

    public void setContestNum(String contestNum) {
        this.contestNum = contestNum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Short getResult() {
        return result;
    }

    public void setResult(Short result) {
        this.result = result;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public LocalDateTime getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(LocalDateTime submitTime) {
        this.submitTime = submitTime;
    }

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    public Short getOpen() {
        return open;
    }

    public void setOpen(Short open) {
        this.open = open;
    }

    public String getDispLanguage() {
        return dispLanguage;
    }

    public void setDispLanguage(String dispLanguage) {
        this.dispLanguage = dispLanguage;
    }

    public String getStatusCanonical() {
        return statusCanonical;
    }

    public void setStatusCanonical(String statusCanonical) {
        this.statusCanonical = statusCanonical;
    }

    public Integer getSourceLength() {
        return sourceLength;
    }

    public void setSourceLength(Integer sourceLength) {
        this.sourceLength = sourceLength;
    }

    public Byte getValid() {
        return valid;
    }

    public void setValid(Byte valid) {
        this.valid = valid;
    }

    public LocalDateTime getJudgeTime() {
        return judgeTime;
    }

    public void setJudgeTime(LocalDateTime judgeTime) {
        this.judgeTime = judgeTime;
    }

    public BigDecimal getPassRate() {
        return passRate;
    }

    public void setPassRate(BigDecimal passRate) {
        this.passRate = passRate;
    }

    public String getJudger() {
        return judger;
    }

    public void setJudger(String judger) {
        this.judger = judger;
    }

    public LocalDateTime getAddDatetime() {
        return addDatetime;
    }

    public void setAddDatetime(LocalDateTime addDatetime) {
        this.addDatetime = addDatetime;
    }

    public LocalDateTime getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(LocalDateTime updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Byte getJudgeType() {
        return judgeType;
    }

    public void setJudgeType(Byte judgeType) {
        this.judgeType = judgeType;
    }
}
