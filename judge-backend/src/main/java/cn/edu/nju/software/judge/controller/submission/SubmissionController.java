package cn.edu.nju.software.judge.controller.submission;

import cn.edu.nju.software.judge.beans.SubmissionExample;
import cn.edu.nju.software.judge.dao.SubmissionMapper;
import cn.edu.nju.software.judge.model.SubmissionModel;
import cn.edu.nju.software.judge.service.submission.SubmissionService;
import cn.edu.nju.software.judge.submission.ResultCode;
import cn.edu.nju.software.judge.vo.Result;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
@RestController
@RequestMapping("/submission")
public class SubmissionController {

    @Resource
    private SubmissionService submissionService;


    @RequestMapping(value = "submit",method = {POST})
    public Result submitCode(@RequestBody SubmissionModel submissionModel){
        try {
            final LocalDateTime now = LocalDateTime.now();
            submissionModel.setResult(ResultCode.WT.getCode());
            submissionModel.setSubmitTime(now);
            submissionModel.setDel(-1);
            submissionModel.setAddDatetime(now);
            submissionModel.setUpdateDatetime(now);
            submissionModel.setOpen((short)0);
            submissionModel.setValid((byte)0);
            submissionModel.setPassRate(new BigDecimal("0"));
            submissionModel.setJudger("local");
            submissionService.addSubmission(submissionModel);

        }catch (Exception e){
            e.printStackTrace();
            return Result.failure("提交失败");
        }

        return Result.success();
    }


    public static void main(String[] args) {

        SubmissionModel submissionModel = new SubmissionModel();

        submissionModel.setUserName("刘孝敬");
        submissionModel.setUserId(1);
        submissionModel.setSource("#include <stdio.h>\n" +
                "\n" +
                "int main()\n" +
                "{\n" +
                "    int a,b;\n" +
                "    while(scanf(\"%d %d\",&a, &b) != EOF){\n" +
                "    \tprintf(\"%d\\n\",a+b);\n" +
                "    }\n" +
                "    return 0;\n" +
                "}");
        submissionModel.setSourceLength(submissionModel.getSource().length());
        submissionModel.setTime(1);
        submissionModel.setMemory(1000);
        submissionModel.setLanguage(0);
        submissionModel.setDispLanguage("C");
        submissionModel.setProblemId(1000);

        System.out.println(JSON.toJSONString(submissionModel));

    }

}
