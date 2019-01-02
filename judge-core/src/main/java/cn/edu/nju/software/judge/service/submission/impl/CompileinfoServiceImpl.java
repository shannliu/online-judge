package cn.edu.nju.software.judge.service.submission.impl;

import cn.edu.nju.software.judge.beans.Compileinfo;
import cn.edu.nju.software.judge.dao.CompileinfoMapper;
import cn.edu.nju.software.judge.model.CompileinfoModel;
import cn.edu.nju.software.judge.service.submission.CompileinfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
@Service
public class CompileinfoServiceImpl implements CompileinfoService {

    @Resource
    private CompileinfoMapper compileinfoMapper;


    @Override
    @Transactional
    public void insert(CompileinfoModel compileinfoModel) {
        Assert.notNull(compileinfoModel.getSubmissionId(),"submissionId not null");
        Compileinfo compileinfo = compileinfoModel.compileinfo();
        compileinfoMapper.insert(compileinfo);
    }
}
