package cn.edu.nju.software.judge.service.submission.impl;

import cn.edu.nju.software.judge.beans.Submission;
import cn.edu.nju.software.judge.dao.SubmissionMapper;
import cn.edu.nju.software.judge.model.SubmissionModel;
import cn.edu.nju.software.judge.service.submission.SubmissionService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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
@Service
public class SubmissionServiceImpl implements SubmissionService {

    @Resource
    private SubmissionMapper submissionMapper;

    @Resource
    private RedisTemplate redisTemplate;


    @Override
    @Transactional
    public SubmissionModel addSubmission(SubmissionModel submissionModel) {
        Submission submission = submissionModel.submission();
        submissionMapper.insert(submission);

        Integer submissionId = submission.getSubmissionId();

        return null;
    }
}
