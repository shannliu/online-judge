package software.judger.consumer;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import software.judger.core.JudgeClient;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @PostConstruct
    public void init(){

        int cpus = Runtime.getRuntime().availableProcessors();

        System.out.println(cpus);

        for(int i = 0 ; i < 4 ; i ++){
            System.out.println("-----------"+i+"-----------");


            JudgeThread judgeThread = new JudgeThread(redisTemplate,judgeClient);

            judgeThread.start();

        }
    }

}
