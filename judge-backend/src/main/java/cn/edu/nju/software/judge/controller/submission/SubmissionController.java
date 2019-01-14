package cn.edu.nju.software.judge.controller.submission;

import cn.edu.nju.software.judge.model.*;
import cn.edu.nju.software.judge.service.problem.ProblemService;
import cn.edu.nju.software.judge.service.submission.CompileinfoService;
import cn.edu.nju.software.judge.service.submission.RuntimeinfoService;
import cn.edu.nju.software.judge.service.submission.SubmissionCodeService;
import cn.edu.nju.software.judge.service.submission.SubmissionService;
import cn.edu.nju.software.judge.submission.LangEnum;
import cn.edu.nju.software.judge.submission.ResultCode;
import cn.edu.nju.software.judge.vo.Result;
import cn.edu.nju.software.judge.vo.SubmissionVO;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

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

    @Resource
    private SubmissionCodeService submissionCodeService;

    @Resource
    private ProblemService problemService;

    @Resource
    private CompileinfoService compileinfoService;

    @Resource
    private RuntimeinfoService runtimeinfoService;

    /**
     * 用户提交代码
     * @param submissionModel
     * @return
     */
    @RequestMapping(value = "submit", method = {POST})
    public Result submitCode(@RequestBody SubmissionModel submissionModel) {
        try {
            Assert.notNull(submissionModel.getProblemId(), "problemId is not null");
            final ProblemModel problemModel = problemService.getByProblemId(submissionModel.getProblemId());

            if (null == problemModel) {
                throw new Exception("问题不存在");
            }


            submissionModel.setTime(problemModel.getTimeLimit());
            submissionModel.setMemory(problemModel.getMemoryLimit());
            final LocalDateTime now = LocalDateTime.now();
            submissionModel.setResult(ResultCode.WT.getCode());
            submissionModel.setSubmitTime(now);
            submissionModel.setDel(-1);
            submissionModel.setAddDatetime(now);
            submissionModel.setDispLanguage(LangEnum.getDispByLang(submissionModel.getLanguage()));
            submissionModel.setUpdateDatetime(now);
            submissionModel.setOpen((short) 0);
            submissionModel.setValid((byte) 0);
            submissionModel.setJudgeType(null == submissionModel.getJudgeType() ? (byte) 1 : submissionModel.getJudgeType());
            submissionModel.setPassRate(new BigDecimal("0"));
            submissionModel.setJudger("local");
            submissionService.addSubmission(submissionModel);

        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(e.getMessage());
        }

        return Result.success();

    }

    /**
     * 显示自己的提交记录
     * @param submissionModel
     * @return
     */
    @PostMapping("/my")
    public Result list(@RequestBody SubmissionModel submissionModel) {
        //先写死，后期从session里面取
        submissionModel.setUserId(1);
        final PageInfo<SubmissionModel> example = submissionService.findByExample(submissionModel, submissionModel.getPageNum(), submissionModel.getPageSize());

        return Result.success(example);
    }

    /**
     * 显示提交记录详情
     * @param submissionId
     * @return
     */
    @GetMapping("/detail")
    public Result detail(Integer submissionId) {

        final SubmissionModel submissionModel = submissionService.findBySubmissionId(submissionId);


        if (null == submissionModel) {
            return Result.failure("提交记录不存在");
        }

        /**
         * 判断提交记录是不是本人的，本人的才可以查看详情
         */
        final Integer userId = submissionModel.getUserId();


        if (!Objects.equals(userId, 1)) {
            return Result.failure("无权限查看");
        }

        SubmissionVO submissionVO = new SubmissionVO();

        final SubmissionCodeModel submissionCodeModel = submissionCodeService.findBySubmissionId(submissionId);

        submissionModel.setSource(null == submissionCodeModel ? null : submissionCodeModel.getSource());

        submissionVO.setSubmissionModel(submissionModel);

        if (submissionModel.getResult() == ResultCode.CE.getCode()) {
            //编译错误，获取编译错误信息
            final CompileinfoModel compileinfoModel = compileinfoService.findBySubmissionId(submissionId);
            submissionVO.setCompileinfoModel(compileinfoModel);
        } else if (submissionModel.getResult() == ResultCode.RE.getCode()) {
            //运行错误，获取运行错误信息
            final RuntimeinfoModel runtimeinfoModel = runtimeinfoService.findBySubmissionId(submissionId);
            submissionVO.setRuntimeinfoModel(runtimeinfoModel);
        }


        return Result.success(submissionVO);

    }

    /**
     * 获取最后一次提交的代码信息
     * @param problemId
     * @return
     */
    @GetMapping("/getLastCode")
    public Result getLastCode(Integer problemId) {


        SubmissionModel submissionModel = new SubmissionModel();
        submissionModel.setProblemId(problemId);
        submissionModel.setUserId(1);
        final SubmissionModel submissionModel1 = submissionService.getLastCode(submissionModel);

        if (null == submissionModel1) {

            return Result.success(null);
        }

        final SubmissionCodeModel submissionCodeModel = submissionCodeService.findBySubmissionId(submissionModel1.getSubmissionId());

        submissionModel1.setSource(submissionCodeModel.getSource());

        return Result.success(submissionModel1);

    }

    public static void main(String[] args) {

        SubmissionModel submissionModel = new SubmissionModel();

        submissionModel.setUserName("刘孝敬");
        submissionModel.setUserId(1);
        submissionModel.setSource("#include<stdio.h>\n" +
                "#include<string.h>\n" +
                "int main()\n" +
                "{\n" +
                "       int b[150],i,j,t,n,q,num,sum,z=1;\n" +
                "       char a[150];\n" +
                "       while(scanf(\"%d%*c\",&n)!=EOF || 1 == 1)\n" +
                "       {\n" +
                "           num=0;\n" +
                "           sum=0;\n" +
                "           while(n--)\n" +
                "           {\n" +
                "               q=0;\n" +
                "               sum=0;\n" +
                "               for(i=0;i<150;i++)\n" +
                "                b[i]=0;\n" +
                "               scanf(\"%s\",a);\n" +
                "               for(i=0;i<strlen(a);i++)\n" +
                "               b[a[i]]++;\n" +
                "               for(i=0;i<123;i++)\n" +
                "                if(b[i]>0)\n" +
                "                sum++;\n" +
                "                if(sum==1)\n" +
                "                    q=1;\n" +
                "               for(i=65;i<123;i++)\n" +
                "               for(j=65;j<123;j++)\n" +
                "                   if(b[i]==b[j]&&i!=j&&b[i]!=0&&b[j]!=0)\n" +
                "                    {q=1;break;}\n" +
                "              if(q==0)\n" +
                "                num++;\n" +
                "           }\n" +
                "           printf(\"Case %d: %d\\n\",z++,num);\n" +
                "       }\n" +
                "}\n");
        submissionModel.setSourceLength(submissionModel.getSource().length());
        submissionModel.setTime(1);
        submissionModel.setMemory(1000);
        submissionModel.setLanguage(0);
        submissionModel.setDispLanguage("C");
        submissionModel.setProblemId(5868);
        submissionModel.setJudgeType((byte) 1);

        System.out.println(JSON.toJSONString(submissionModel));


//        List<JSONObject> lists = new ArrayList<>();
//
//
//        JSONObject jsonObject1 = new JSONObject();
//
//        jsonObject1.put("name","天津法院审判流程标准");
//        jsonObject1.put("dir","第一批");
//        jsonObject1.put("filename","1.天津法院审判流程标准.doc");
//        lists.add(jsonObject1);
//
//        JSONObject jsonObject2 = new JSONObject();
//
//        jsonObject2.put("name","天津法院庭审质量标准");
//        jsonObject2.put("dir","第一批");
//        jsonObject2.put("filename","2.天津法院庭审质量标准.doc");
//        lists.add(jsonObject2);
//
//
//
//        JSONObject jsonObject3 = new JSONObject();
//
//        jsonObject3.put("name","天津法院裁判文书质量标准");
//        jsonObject3.put("dir","第一批");
//        jsonObject3.put("filename","3.天津法院裁判文书质量标准.doc");
//        lists.add(jsonObject3);
//
//
//
//        JSONObject jsonObject4 = new JSONObject();
//
//        jsonObject4.put("name","天津法院司法公开实施标准");
//        jsonObject4.put("dir","第一批");
//        jsonObject4.put("filename","4.天津法院司法公开实施标准.doc");
//        lists.add(jsonObject4);
//
//
//        JSONObject jsonObject5 = new JSONObject();
//
//        jsonObject5.put("name","天津法院审判组织工作标准");
//        jsonObject5.put("dir","第一批");
//        jsonObject5.put("filename","5.天津法院审判组织工作标准.doc");
//        lists.add(jsonObject5);
//
//
//
//        JSONObject jsonObject6 = new JSONObject();
//
//        jsonObject6.put("name","天津法院案件质量问题认定标准");
//        jsonObject6.put("dir","第一批");
//        jsonObject6.put("filename","6.天津法院案件质量认定标准.doc");
//        lists.add(jsonObject6);
//
//
//
//        JSONObject jsonObject7 = new JSONObject();
//
//        jsonObject7.put("name","天津法院立案工作标准");
//        jsonObject7.put("dir","第二批");
//        jsonObject7.put("filename","1.天津法院立案工作标准.doc");
//        lists.add(jsonObject7);
//
//
//
//
//        JSONObject jsonObject8 = new JSONObject();
//
//        jsonObject8.put("name","天津法院小额诉讼程序标准");
//        jsonObject8.put("dir","第二批");
//        jsonObject8.put("filename","2.天津法院小额诉讼程序标准.doc");
//        lists.add(jsonObject8);
//
//
//
//
//        JSONObject jsonObject9 = new JSONObject();
//
//        jsonObject9.put("name","天津法院人民法庭司法工作标准");
//        jsonObject9.put("dir","第二批");
//        jsonObject9.put("filename","3.天津法院人民法庭司法工作标准.doc");
//        lists.add(jsonObject9);
//
//
//
//
//        JSONObject jsonObject10 = new JSONObject();
//
//        jsonObject10.put("name","天津法院执行结案标准");
//        jsonObject10.put("dir","第二批");
//        jsonObject10.put("filename","4.天津法院执行结案标准.doc");
//        lists.add(jsonObject10);
//
//
//
//
//
//        JSONObject jsonObject11 = new JSONObject();
//
//        jsonObject11.put("name","天津法院涉案财物管理标准");
//        jsonObject11.put("dir","第二批");
//        jsonObject11.put("filename","5.天津法院涉案财物管理标准.doc");
//        lists.add(jsonObject11);
//
//
//
//        JSONObject jsonObject12 = new JSONObject();
//
//        jsonObject12.put("name","天津法院审限管理标准");
//        jsonObject12.put("dir","第二批");
//        jsonObject12.put("filename","6.天津法院审限管理标准.doc");
//        lists.add(jsonObject12);
//
//
//
//
//        JSONObject jsonObject13 = new JSONObject();
//
//        jsonObject13.put("name","天津法院诉讼服务平台建设标准");
//        jsonObject13.put("dir","第二批");
//        jsonObject13.put("filename","7.天津法院诉讼服务平台建设标准.doc");
//        lists.add(jsonObject13);
//
//
//
//        JSONObject jsonObject14 = new JSONObject();
//
//        jsonObject14.put("name","天津法院涉诉信访流程标准");
//        jsonObject14.put("dir","第二批");
//        jsonObject14.put("filename","8.天津法院涉诉信访流程标准.doc");
//        lists.add(jsonObject14);
//
//
//
//        JSONObject jsonObject15 = new JSONObject();
//
//        jsonObject15.put("name","天津法院物业服务纠纷案件审理标准");
//        jsonObject15.put("dir","第三批");
//        jsonObject15.put("filename","1.天津法院物业服务纠纷案件审理标准（试行）.doc");
//        lists.add(jsonObject15);
//
//
//
//        JSONObject jsonObject16 = new JSONObject();
//
//        jsonObject16.put("name","天津法院国家司法救助工作标准");
//        jsonObject16.put("dir","第三批");
//        jsonObject16.put("filename","1.天津法院国家司法救助工作标准（试行）.doc");
//        lists.add(jsonObject16);
//
//
//
//        JSONObject jsonObject17 = new JSONObject();
//
//        jsonObject17.put("name","天津法院审判职权行使与审判责任认定标准（试行）");
//        jsonObject17.put("dir","第三批");
//        jsonObject17.put("filename","1.天津法院审判职权行使与审判责任认定标准（试行）.doc");
//        lists.add(jsonObject17);
//
//
//
//        JSONObject jsonObject18 = new JSONObject();
//
//        jsonObject18.put("name","天津法院民事送达工作标准");
//        jsonObject18.put("dir","第三批");
//        jsonObject18.put("filename","2.天津法院民事送达工作标准（试行）.doc");
//        lists.add(jsonObject18);
//
//
//
//        JSONObject jsonObject19 = new JSONObject();
//
//        jsonObject19.put("name","天津法院融资租赁合同纠纷案件审理标准");
//        jsonObject19.put("dir","第三批");
//        jsonObject19.put("filename","3.天津法院融资租赁合同纠纷案件审理标准（试行）.doc");
//        lists.add(jsonObject19);
//
//
//        JSONObject jsonObject20 = new JSONObject();
//
//        jsonObject20.put("name","天津法院船舶碰撞案件审理标准");
//        jsonObject20.put("dir","第三批");
//        jsonObject20.put("filename","4.天津法院船舶碰撞案件审理标准(试行).doc");
//        lists.add(jsonObject20);
//
//
//        JSONObject jsonObject21 = new JSONObject();
//
//        jsonObject21.put("name","天津法院机动车交通事故责任纠纷 案件审理标准");
//        jsonObject21.put("dir","第三批");
//        jsonObject21.put("filename","5.天津法院机动车交通事故责任纠纷案件审理标准（试行）.doc");
//        lists.add(jsonObject21);
//
//
//
//        JSONObject jsonObject22 = new JSONObject();
//
//        jsonObject22.put("name","天津法院委托鉴定、评估、司法拍卖工作标准（试行）");
//        jsonObject22.put("dir","第三批");
//        jsonObject22.put("filename","天津法院委托鉴定、评估、司法拍卖工作标准（试行）.doc");
//        lists.add(jsonObject22);
//
//        JSONObject jsonObject23 = new JSONObject();
//
//        jsonObject23.put("name","天津法院暂予监外执行工作标准");
//        jsonObject23.put("dir","第三批");
//        jsonObject23.put("filename","天津法院暂予监外执行工作标准.doc");
//        lists.add(jsonObject23);
//
//        JSONObject jsonObject24 = new JSONObject();
//
//        jsonObject24.put("name","天津法院执行异议之诉案件审理标准（试行）");
//        jsonObject24.put("dir","第三批");
//        jsonObject24.put("filename","天津法院执行异议之诉案件审理标准（试行）.doc");
//        lists.add(jsonObject24);
//        JSONObject jsonObject25 = new JSONObject();
//
//        jsonObject25.put("name","天津法院政府信息公开案件审理标准（试行）");
//        jsonObject25.put("dir","第三批");
//        jsonObject25.put("filename","天津法院政府信息公开案件审理标准（试行）.doc");
//        lists.add(jsonObject25);
//        JSONObject jsonObject26 = new JSONObject();
//
//        jsonObject26.put("name","天津法院关于国家赔偿自赔案件办理标准（试行）");
//        jsonObject26.put("dir","第三批");
//        jsonObject26.put("filename","天津法院关于国家赔偿自赔案件办理标准（试行）.doc");
//        lists.add(jsonObject26);
//        JSONObject jsonObject27 = new JSONObject();
//
//        jsonObject27.put("name","天津法院劳动争议案件审理标准（试行）");
//        jsonObject27.put("dir","第三批");
//        jsonObject27.put("filename","天津法院劳动争议案件审理标准（试行）.doc");
//        lists.add(jsonObject27);
//        JSONObject jsonObject28 = new JSONObject();
//
//        jsonObject28.put("name","天津法院再审审查案件审查标准（试行）");
//        jsonObject28.put("dir","第三批");
//        jsonObject28.put("filename","天津法院再审审查案件审查标准（试行）.doc");
//        lists.add(jsonObject28);
//        JSONObject jsonObject29 = new JSONObject();
//
//        jsonObject29.put("name","天津法院关于侵害信息网络传播权纠纷案件审理标准");
//        jsonObject29.put("dir","第三批");
//        jsonObject29.put("filename","天津法院关于侵害信息网络传播权纠纷案件审理标准.doc");
//        lists.add(jsonObject29);
//
//
//        System.out.println(JSON.toJSONString(lists));

    }


}
