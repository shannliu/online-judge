package cn.edu.nju.software.judge.controller.problem;

import cn.edu.nju.software.judge.beans.Problem;
import cn.edu.nju.software.judge.beans.ProblemExample;
import cn.edu.nju.software.judge.model.ProblemModel;
import cn.edu.nju.software.judge.service.problem.ProblemService;
import cn.edu.nju.software.judge.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
@RequestMapping("/problem")
public class ProblemController {

    @Resource
    private ProblemService problemService;

    /**
     * 根据题目ID获取题目的详细信息
     * @param problemId 题目ID
     * @return
     */
    @GetMapping("/detail")
    public Result detail(final Integer problemId){

        final ProblemModel problemModel = problemService.getByProblemId(problemId);

        if(null == problemModel){
            return Result.failure("问题不存在");
        }
        return Result.success(problemModel);
    }

    /**
     * 获取问题
     * @param id
     * @return
     */
    @RequestMapping("getProblemById")
    public ProblemModel getProblem(int id){
        return problemService.getByProblemId(id);
    }

    /**
     * 获取问题列表
     * @param example
     * @return
     */
    @RequestMapping("getProblemList")
    public Result getProblemList(ProblemExample example){
        List<Problem> problemList = problemService.getProblemList(example);
        if (problemList != null) {
            return Result.success(problemList);
        }
        return Result.success(null);
    }
}
