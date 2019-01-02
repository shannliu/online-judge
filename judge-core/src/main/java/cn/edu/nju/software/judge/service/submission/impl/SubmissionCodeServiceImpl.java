package cn.edu.nju.software.judge.service.submission.impl;

import cn.edu.nju.software.judge.beans.SubmissionCode;
import cn.edu.nju.software.judge.dao.SubmissionCodeMapper;
import cn.edu.nju.software.judge.model.SubmissionCodeModel;
import cn.edu.nju.software.judge.service.submission.SubmissionCodeService;
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
public class SubmissionCodeServiceImpl implements SubmissionCodeService {


    @Resource
    private SubmissionCodeMapper submissionCodeMapper;


    @Override
    @Transactional
    public void insert(SubmissionCodeModel submissionCodeModel) {
        Assert.notNull(submissionCodeModel.getSubmissionId(),"submissionId not null");
        submissionCodeMapper.insert(submissionCodeModel.submissionCode());
    }
}
