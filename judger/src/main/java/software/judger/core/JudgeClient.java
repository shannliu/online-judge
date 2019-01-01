package software.judger.core;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import judge.JudgeGrpc;
import judge.JudgeOuterClass;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import software.beans.CompileRequest;
import software.beans.CompileResponse;
import software.beans.RunRequest;
import software.beans.RunResponse;

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
public class JudgeClient  {

    private ManagedChannel channel;
    private JudgeGrpc.JudgeBlockingStub blockingStub;

    public JudgeClient(String host, int port) {
        System.out.println(host);
        System.out.println(port);
       this.channel =  ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build();
        this.blockingStub = JudgeGrpc.newBlockingStub(channel);
    }
    public CompileResponse compile(CompileRequest compileRequest){
        JudgeOuterClass.CompileRequest request = JudgeOuterClass.CompileRequest.newBuilder().setRunId(compileRequest.getRunId())
                .setLanguage(compileRequest.getLanguage())
                .setSource(compileRequest.getSource())
                .build();
        JudgeOuterClass.CompileResponse response;
        try {
            response = blockingStub.compile(request);


            CompileResponse compileResponse = new CompileResponse();

            BeanUtils.copyProperties(response,compileResponse);

            return compileResponse;
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }

        return null;
    }
    public RunResponse run(RunRequest runRequest){

        JudgeOuterClass.RunRequest request = JudgeOuterClass.RunRequest.newBuilder()
                .setRunId(runRequest.getRunId())
                .setLanguage(runRequest.getLanguage())
                .setMemory(runRequest.getMemory())
                .setTime(runRequest.getTime())
                .setProblemId(runRequest.getProblemId())
                .build();

        JudgeOuterClass.RunResponse response;
        try{

            response = blockingStub.run(request);


            RunResponse runResponse = new RunResponse();

            BeanUtils.copyProperties(response,runResponse);

            return runResponse;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }



}
