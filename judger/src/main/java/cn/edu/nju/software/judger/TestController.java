package cn.edu.nju.software.judger;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.edu.nju.software.judger.beans.RedisSubmission;
import cn.edu.nju.software.judger.core.JudgeClient;

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
@Controller
public class TestController {

    @Resource
    JudgeClient judgeClient;

    @Resource
    RedisTemplate<String,Object> redisTemplate;


    @RequestMapping("/test")
    @ResponseBody
    public RedisSubmission test(){

        RedisSubmission runRequest = new RedisSubmission();

        runRequest.setLanguage(0);
        runRequest.setMemory(128);
        runRequest.setTime(1);
        runRequest.setProblemId(1000);
        runRequest.setRunId(1000);
        runRequest.setSource("#include <stdio.h>\n" +
                "\n" +
                "int main()\n" +
                "{\n" +
                "    int a,b;\n" +
                "    while(scanf(\"%d %d\",&a, &b) != EOF){\n" +
                "    \tprintf(\"%d\\n\",a+b);\n" +
                "    \tprintf(\"\\n\");\n" +
                "    }\n" +
                "    return 0;\n" +
                "}");


        redisTemplate.opsForList().rightPush("judges",runRequest);


        return runRequest;
    }
}
