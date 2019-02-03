package cn.edu.nju.software.judge.controller.submission;

import cn.edu.nju.software.judge.beans.SubmissionExample;
import cn.edu.nju.software.judge.constant.RedisConstants;
import cn.edu.nju.software.judge.model.ProblemModel;
import cn.edu.nju.software.judge.model.SubmissionCodeModel;
import cn.edu.nju.software.judge.model.SubmissionModel;
import cn.edu.nju.software.judge.service.problem.ProblemService;
import cn.edu.nju.software.judge.service.submission.SubmissionCodeService;
import cn.edu.nju.software.judge.service.submission.SubmissionService;
import cn.edu.nju.software.judge.submission.RedisSubmission;
import cn.edu.nju.software.judge.submission.ResultCode;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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
 *
 *
 * 当服务器启动时，将数据库内没有判题的信息重新放入redis队列中
 */
@Component
public class SubmissionReload {


    @Resource
    private SubmissionService submissionService;
    @Resource
    private SubmissionCodeService submissionCodeService;
    @Resource
    private ProblemService problemService;

    @Resource
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void load(){
        SubmissionExample submissionExample = new SubmissionExample();
        final SubmissionExample.Criteria criteria = submissionExample.createCriteria();
        criteria.andResultLessThanOrEqualTo(ResultCode.RJ_WT.getCode());
        final List<SubmissionModel> submissions = submissionService.selectByExample(submissionExample);
        for(SubmissionModel submission : submissions){
            final Integer submissionId = submission.getSubmissionId();
            final Integer problemId = submission.getProblemId();
            final SubmissionCodeModel submissionCode = submissionCodeService.findBySubmissionId(submissionId);
            final ProblemModel problem = problemService.getByProblemIdWithoutBlobs(problemId);
            if(null != submissionCode && null != problem){
                RedisSubmission redisSubmission = new RedisSubmission();
                redisSubmission.setLanguage(submission.getLanguage());
                redisSubmission.setMemory(problem.getMemoryLimit());
                redisSubmission.setTime(problem.getTimeLimit());
                redisSubmission.setUserId(submission.getUserId());
                redisSubmission.setProblemId(problemId);
                redisSubmission.setRunId(submissionId);
                redisSubmission.setSource(submissionCode.getSource());
                redisSubmission.setType(null == submission.getJudgeType() ? (byte) 1 : submission.getJudgeType());
                redisTemplate.opsForList().rightPush(RedisConstants.JUDGE_LIST_KEY,redisSubmission);
            }
        }
    }
}
