package cn.edu.nju.software.judge.service.submission;

import cn.edu.nju.software.judge.beans.Submission;
import cn.edu.nju.software.judge.beans.SubmissionExample;
import cn.edu.nju.software.judge.model.SubmissionModel;

import java.util.List;

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
public interface SubmissionService {

    SubmissionModel addSubmission(SubmissionModel submissionModel);


    void updateSubmissionSelective(SubmissionModel submissionModel);

    /**
     * 获取指定提交记录
     * @param id
     * @return
     */
    Submission getSubmissionById(int id);

    /**
     * 获取提交记录列表
     * @param example
     * @return
     */
    List<Submission> getListSubmission(SubmissionExample example);
}
