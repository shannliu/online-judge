package cn.edu.nju.software.judge.aspect;

import cn.edu.nju.software.judge.constant.RedisConstants;
import cn.edu.nju.software.judge.model.SubmissionModel;
import cn.edu.nju.software.judge.submission.RedisSubmission;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

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
@Aspect
@Component
public class SubmissionAspect {

    private static final Logger LOG = LoggerFactory.getLogger(SubmissionAspect.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Around("execution(* addSubmission(..))")
    public Object addSubmissionToRedis(ProceedingJoinPoint proceedingJoinPoint){
        try {
            Object proceed = proceedingJoinPoint.proceed();

            if(proceed instanceof SubmissionModel){


                SubmissionModel model = (SubmissionModel) proceed;

                RedisSubmission redisSubmission = new RedisSubmission();

                redisSubmission.setLanguage(model.getLanguage());
                redisSubmission.setMemory(model.getMemory());
                redisSubmission.setTime(model.getTime());
                redisSubmission.setProblemId(model.getProblemId());
                redisSubmission.setRunId(model.getSubmissionId());
                redisSubmission.setUserId(model.getUserId());
                redisSubmission.setSource(model.getSource());

                redisTemplate.opsForList().rightPush(RedisConstants.JUDGE_LIST_KEY,redisSubmission);
            }

            return proceed;

        }catch (Throwable e){
            LOG.error("addSubmissionToRedis fail",e);
            throw new RuntimeException(e);
        }
    }
}
