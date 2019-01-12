package cn.edu.nju.software.judge.controller.problem;

import cn.edu.nju.software.judge.beans.Problem;
import cn.edu.nju.software.judge.beans.ProblemExample;
import cn.edu.nju.software.judge.service.problem.ProblemService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @GetMapping
    @RequestMapping(value = "getProblemList.do")
    public String getProblemList(HttpServletRequest request, HttpServletResponse response,
                                 @Param("name")String name, @Param("id")Integer id){
        System.out.println("name: "+name+" id: "+id);
        return "ok";
    }

    /**
     * 根据条件获取练习列表
     * @param example
     * @return
     */
    @RequestMapping("/getProblemList")
    @ResponseBody
    public List<Problem> getListProblem(ProblemExample example){
        return problemService.getListProblem(example);
    }

    /**
     * 获取相应id的练习
     * @param id
     * @return
     */
    @RequestMapping("/getProblemById")
    @ResponseBody
    public Problem getProblemById(int id){
        return problemService.getProblemById(id);
    }
}
