package cn.edu.nju.software.judger.consumer;

import cn.edu.nju.software.judge.dao.SubmissionMapper;
import cn.edu.nju.software.judge.model.SubmissionModel;
import cn.edu.nju.software.judge.service.submission.SubmissionService;
import cn.edu.nju.software.judge.submission.ResultCode;
import org.springframework.data.redis.core.RedisTemplate;
import cn.edu.nju.software.judge.submission.CompileResponse;
import cn.edu.nju.software.judge.submission.RedisSubmission;
import cn.edu.nju.software.judge.submission.RunResponse;
import cn.edu.nju.software.judger.core.JudgeClient;

import java.time.LocalDate;
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

public class JudgeThread {

    private RedisTemplate<String,Object> redisTemplate;

    private JudgeClient judgeClient;

    Thread thread;

    private SubmissionService submissionService;

    public JudgeThread(RedisTemplate<String,Object> redisTemplate, JudgeClient judgeClient, SubmissionService submissionService) {
        this.redisTemplate = redisTemplate;
        this.judgeClient = judgeClient;
        this.submissionService = submissionService;
    }

    private boolean stop = false;


    public void start() {
        thread = new Thread(() -> {
                while(!stop){
                    try {

                        final Object judges = redisTemplate.opsForList().leftPop("judges");

                        if(judges instanceof RedisSubmission){

                            RedisSubmission redisSubmission = (RedisSubmission) judges;

                            CompileResponse compile = judgeClient.compile(redisSubmission.compileRequest());

                            System.out.println(compile);

                            SubmissionModel model = new SubmissionModel();
                            model.setSubmissionId(redisSubmission.getRunId());

                            if(!compile.isSuccess()) {
                                //编译错误
                                model.setResult(ResultCode.CE.getCode());
                                model.setJudgeTime(LocalDateTime.now());

                            }else{
                                //run
                                RunResponse run = judgeClient.run(redisSubmission.runRequest());

                                System.out.println(run);

                            }
                            submissionService.updateSubmissionSelective(model);
                        }

                    } catch (Exception e) {
                        if(stop){

                        }

                    }
                }
                //如果被标记停止执行，则将阻塞队列中的所有值清空
        });

        thread.setDaemon(true);
        thread.start();

    }
}
