package cn.edu.nju.software.judge.service.contest;

import cn.edu.nju.software.judge.beans.Contest;
import cn.edu.nju.software.judge.beans.ContestWithBLOBs;
import cn.edu.nju.software.judge.beans.Cproblem;

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
public interface ContestService {
    /**
     * 获取所有
     * @return
     */
    List<Contest> getAllContests();

    /**
     * 根据id获取
     * @param contestID
     * @return
     */
    ContestWithBLOBs  getContestByContestID(Integer contestID);

    /**
     * 查找竞赛题目列表
     * @param contestID
     * @return
     */
    List<Cproblem> getCproblemByContestID(Integer contestID);
}
