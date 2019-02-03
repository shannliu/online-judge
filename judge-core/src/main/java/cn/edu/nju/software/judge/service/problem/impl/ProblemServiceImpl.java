package cn.edu.nju.software.judge.service.problem.impl;

import cn.edu.nju.software.judge.beans.Problem;
import cn.edu.nju.software.judge.beans.ProblemExample;
import cn.edu.nju.software.judge.beans.ProblemWithBLOBs;
import cn.edu.nju.software.judge.dao.ProblemMapper;
import cn.edu.nju.software.judge.model.ProblemModel;
import cn.edu.nju.software.judge.service.problem.ProblemService;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

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
@Service
@EnableCaching
public class ProblemServiceImpl implements ProblemService {

    @Resource
    ProblemMapper problemMapper;

    @Override
    @Cacheable(value = "problem",key = "'ProblemWithBLOBs_'+#problemId")
    public ProblemModel getByProblemId(Integer problemId) {
        final ProblemWithBLOBs problemWithBLOBs = problemMapper.selectByPrimaryKey(problemId);
        return PO2Model(problemWithBLOBs);
    }

    @Override
    @Cacheable(value = "problem",key = "'Problem_'+#problemId")
    public ProblemModel getByProblemIdWithoutBlobs(Integer problemId) {
        final Problem problem = problemMapper.findByProblemId(problemId);
        return PO2Model(problem);
    }

    @Override
    public List<Problem> getProblemList(ProblemExample example) {
        return problemMapper.selectByExample(example);
    }

    private ProblemModel PO2Model(Problem problem){

        if(null == problem){
            return null;
        }

        try{
            ProblemModel problemModel = new ProblemModel();

            BeanUtils.copyProperties(problem,problemModel);

            return problemModel;
        }catch (Exception e){

        }
        return null;
    }
}
