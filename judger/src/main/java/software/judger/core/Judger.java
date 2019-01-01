package software.judger.core;

import org.apache.commons.io.IOUtils;
import software.beans.JudgeBean;
import software.beans.Submission;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;
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
public class Judger {

    public native String judge(JudgeBean judgeBean) throws Exception;


    public String judge2(JudgeBean judgeBean) throws Exception{
        ProcessBuilder processBuilder = new ProcessBuilder("gcc", "Main.c", "-o", "Main", "-Wall",
                "-lm", "-DONLINE_JUDGE");
//
//
//        processBuilder.start();


        processBuilder = new ProcessBuilder("./Main", "./Main");

        Process start = processBuilder.start();

        boolean result = start.waitFor(1L, TimeUnit.SECONDS);

        InputStream stderr = start.getErrorStream();
        InputStream stdout = start.getInputStream();
        OutputStream stdin = start.getOutputStream();

        processBuilder.redirectErrorStream(true);

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
        BufferedReader reader = new BufferedReader (new InputStreamReader(stdout));

        writer.write("1 2");

        writer.flush();
        writer.close();

        String line = "";
        while((line = reader.readLine()) != null){
            System.out.println(line);
        }

        System.out.println(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());

        ProcessHandle.Info info = start.toHandle().info();

        System.out.println(start.pid());

        System.out.printf("Run time duration: %sms%n",
                info.totalCpuDuration().orElse(Duration.ZERO).toMillis());


        System.out.printf("Run time duration: %sms%n",
                ProcessHandle.current().info().totalCpuDuration().orElse(Duration.ZERO).toMillis());
        System.out.println(result);
        return null;

    }


    public String test() throws Exception{

        throw new Exception("hhhh\n11111111");
    }

    static{
        System.loadLibrary("judge");
    }

    public static void main(String[] args) {


        Judger judger = new Judger();

        JudgeBean judgeBean = new JudgeBean();

        judgeBean.setLanguage(0);
        judgeBean.setMemory(1000);
        judgeBean.setTime(1000);
        judgeBean.setProblemId(1);
        judgeBean.setSource("11111111测试一下");
        judgeBean.setRunId(1);

        try {
            String run = judger.judge2(judgeBean);
            System.out.println(run);
        }catch (Exception e){
//            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }
}
