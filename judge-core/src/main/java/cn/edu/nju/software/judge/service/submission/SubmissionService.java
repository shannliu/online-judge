package cn.edu.nju.software.judge.service.submission;

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

    /**
     * 添加提交记录信息
     * @param submissionModel
     * @return
     */
    SubmissionModel addSubmission(SubmissionModel submissionModel);

    /**
     * 根据传递提交记录信息，有选择性的更新非空的信息
     * @param submissionModel
     */
    void updateSubmissionSelective(SubmissionModel submissionModel);

    /**
     * 根据条件分页查询提交记录
     * @param submissionModel
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<SubmissionModel> findByExample(SubmissionModel submissionModel,Integer pageNum,Integer pageSize);

    /**
     * 根据提交记录ID获取提交记录信息
     * @param submissionId
     * @return
     */
    SubmissionModel findBySubmissionId(Integer submissionId);
}
