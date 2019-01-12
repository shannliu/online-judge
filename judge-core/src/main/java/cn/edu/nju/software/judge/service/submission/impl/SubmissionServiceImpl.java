package cn.edu.nju.software.judge.service.submission.impl;

import cn.edu.nju.software.judge.beans.Submission;
import cn.edu.nju.software.judge.beans.SubmissionCode;
import cn.edu.nju.software.judge.beans.SubmissionExample;
import cn.edu.nju.software.judge.dao.SubmissionCodeMapper;
import cn.edu.nju.software.judge.dao.SubmissionMapper;
import cn.edu.nju.software.judge.model.SubmissionModel;
import cn.edu.nju.software.judge.service.submission.SubmissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
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
public class SubmissionServiceImpl implements SubmissionService {

    private static final Logger LOG = LoggerFactory.getLogger(SubmissionServiceImpl.class);

    @Resource
    private SubmissionMapper submissionMapper;

    @Resource
    private SubmissionCodeMapper submissionCodeMapper;


    @Override
    @Transactional
    public SubmissionModel addSubmission(SubmissionModel submissionModel) {
        try {
            Submission submission = submissionModel.submission();
            submissionMapper.insert(submission);

            SubmissionCode submissionCode = new SubmissionCode();
            submissionCode.setSubmissionId(submission.getSubmissionId());
            submissionCode.setSource(submissionModel.getSource());
            submissionCodeMapper.insert(submissionCode);

            return PO2Model(submissionModel,submission);
        }catch (Exception e){
            LOG.error("addSubmission fail",e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void updateSubmissionSelective(SubmissionModel submissionModel) {
        try {
            Submission submission = submissionModel.submission();
            submissionMapper.updateByPrimaryKeySelective(submission);
        }catch (Exception e){
            LOG.error("updateSubmissionSelective fail",e);
            throw e;
        }
    }

    @Override
    public Submission getSubmissionById(int id) {
        return submissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Submission> getListSubmission(SubmissionExample example) {
        return submissionMapper.selectByExample(example);
    }

    private SubmissionModel PO2Model( SubmissionModel submissionModel, Submission submission){
        BeanUtils.copyProperties(submission,submissionModel);
        return submissionModel;
    }
    private SubmissionModel PO2Model(Submission submission){

        SubmissionModel submissionModel = new SubmissionModel();
        BeanUtils.copyProperties(submission,submissionModel);

        return submissionModel;
    }
}
