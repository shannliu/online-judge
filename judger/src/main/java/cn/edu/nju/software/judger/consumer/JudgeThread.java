package cn.edu.nju.software.judger.consumer;

import cn.edu.nju.software.judge.constant.RedisConstants;
import cn.edu.nju.software.judge.model.CompileinfoModel;
import cn.edu.nju.software.judge.model.RuntimeinfoModel;
import cn.edu.nju.software.judge.model.SubmissionModel;
import cn.edu.nju.software.judge.service.submission.CompileinfoService;
import cn.edu.nju.software.judge.service.submission.RuntimeinfoService;
import cn.edu.nju.software.judge.service.submission.SubmissionService;
import cn.edu.nju.software.judge.submission.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import cn.edu.nju.software.judge.submission.CompileResponse;
import cn.edu.nju.software.judge.submission.RedisSubmission;
import cn.edu.nju.software.judge.submission.RunResponse;
import cn.edu.nju.software.judger.core.JudgeClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

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

    public static final Logger LOGGER = LoggerFactory.getLogger(JudgeThread.class);

    private RedisTemplate<String, Object> redisTemplate;

    private JudgeClient judgeClient;

    Thread thread;

    private SubmissionService submissionService;

    private CompileinfoService compileinfoService;

    private RuntimeinfoService runtimeinfoService;

    public JudgeThread(RedisTemplate<String, Object> redisTemplate, JudgeClient judgeClient, SubmissionService submissionService, CompileinfoService compileinfoService, RuntimeinfoService runtimeinfoService) {
        this.redisTemplate = redisTemplate;
        this.judgeClient = judgeClient;
        this.submissionService = submissionService;
        this.compileinfoService = compileinfoService;
        this.runtimeinfoService = runtimeinfoService;
    }

    private boolean stop = false;


    public void start() {
        thread = new Thread(() -> {
            while (!stop) {
                RedisSubmission redisSubmission = null;
                try {

                    final Object o = redisTemplate.opsForList().leftPop(RedisConstants.JUDGE_LIST_KEY, 10, TimeUnit.SECONDS);

                    if (!(o instanceof RedisSubmission)) {
                        continue;
                    }

                    redisSubmission = (RedisSubmission) o;

                    CompileResponse compile = judgeClient.compile(redisSubmission.compileRequest());

                    System.out.println(compile);

                    SubmissionModel model = new SubmissionModel();

                    Integer submissionId = redisSubmission.getRunId();
                    model.setSubmissionId(submissionId);

                    if (!compile.isSuccess()) {
                        //编译错误
                        model.setResult(ResultCode.CE.getCode());
                        model.setJudgeTime(LocalDateTime.now());
                        model.setJudger("local");


                        //记录编译错误信息
                        CompileinfoModel compileinfoModel = new CompileinfoModel();
                        compileinfoModel.setSubmissionId(submissionId);
                        compileinfoModel.setError(compile.getError());
                        compileinfoService.insert(compileinfoModel);

                    } else {
                        //run
                        RunResponse run = judgeClient.run(redisSubmission.runRequest());

                        final short result = (short) run.getResult();

                        model.setResult(result);

                        model.setJudgeTime(LocalDateTime.now());
                        model.setJudger("local");
                        model.setMemory(run.getMemory());
                        model.setTime(run.getTime());
                        model.setPassRate(new BigDecimal(run.getPassRate()));

                        String error = run.getError();

                        if (StringUtils.isNotEmpty(error)) {
                            //记录运行时错误
                            RuntimeinfoModel runtimeinfoModel = new RuntimeinfoModel();
                            runtimeinfoModel.setError(run.getError());
                            runtimeinfoModel.setSubmissionId(submissionId);
                            runtimeinfoService.insert(runtimeinfoModel);
                        }

                    }
                    //设置状态
                    model.setStatusCanonical(ResultCode.getStatusByCode(model.getResult()));
                    submissionService.updateSubmissionSelective(model);

                } catch (Exception e) {
                    LOGGER.error("run error", e);
                    if (null != redisSubmission) {
                        redisTemplate.opsForList().rightPush(RedisConstants.JUDGE_LIST_KEY, redisSubmission);
                    }
                }
            }
            //如果被标记停止执行，则将阻塞队列中的所有值清空
        });

        thread.setDaemon(true);
        thread.start();

    }
}
