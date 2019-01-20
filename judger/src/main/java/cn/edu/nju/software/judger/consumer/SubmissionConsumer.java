package cn.edu.nju.software.judger.consumer;

import cn.edu.nju.software.judge.service.submission.CompileinfoService;
import cn.edu.nju.software.judge.service.submission.RuntimeinfoService;
import cn.edu.nju.software.judge.service.submission.SubmissionService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import cn.edu.nju.software.judger.core.JudgeClient;

import javax.annotation.PostConstruct;
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

@Component
public class SubmissionConsumer {

    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @Resource
    JudgeClient judgeClient;

    @Resource
    SubmissionService submissionService;

    @Resource
    CompileinfoService compileinfoService;

    @Resource
    RuntimeinfoService runtimeinfoService;

    @PostConstruct
    public void init(){

        int cpus = Runtime.getRuntime().availableProcessors();

        System.out.println(cpus);

        for(int i = 0 ; i < 1 ; i ++){
            System.out.println("-----------"+i+"-----------");

            JudgeThread judgeThread = new JudgeThread(redisTemplate,judgeClient,submissionService,compileinfoService,runtimeinfoService);

            judgeThread.start();

        }
    }

}
