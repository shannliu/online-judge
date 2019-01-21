package cn.edu.nju.software.judge.service.contest.impl;

import cn.edu.nju.software.judge.beans.*;
import cn.edu.nju.software.judge.dao.ContestMapper;
import cn.edu.nju.software.judge.dao.CproblemMapper;
import cn.edu.nju.software.judge.service.contest.ContestService;
import cn.edu.nju.software.judge.service.user.impl.UserServiceImpl;
import cn.edu.nju.software.judge.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class ContestServiceImpl implements ContestService {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private ContestMapper contestMapper;
    @Resource
    private CproblemMapper cproblemMapper;

    public List<Contest> getAllContests(){
        return contestMapper.selectAll();
    }

    /**
     * 根据ID获取contest详情
     * @param contestID
     * @return
     */
    public ContestWithBLOBs getContestByContestID(Integer contestID){
        ContestExample contestExample = new ContestExample();
        ContestExample.Criteria criteria = contestExample.createCriteria();
        criteria.andContestIdEqualTo(contestID);
        contestExample.or(criteria);
        List<ContestWithBLOBs> result = contestMapper.selectByExampleWithBLOBs(contestExample);
        if (result == null|| result.size()==0){
            LOGGER.error("contest not found");
            return null;
        }
        return result.get(0);
    }

    public List<Cproblem> getCproblemByContestID(Integer contestID){
        CproblemExample cproblemExample = new CproblemExample();
        CproblemExample.Criteria criteria = cproblemExample.createCriteria();
        criteria.andContestIdEqualTo(contestID);
        cproblemExample.or(criteria);
        List<Cproblem> result = cproblemMapper.selectByExample(cproblemExample);
        if (result == null||result.size()==0){
            LOGGER.error("contest not found");
            return null;
        }
        return result;
    }

}
