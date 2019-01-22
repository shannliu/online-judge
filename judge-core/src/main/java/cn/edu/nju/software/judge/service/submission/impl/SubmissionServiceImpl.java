package cn.edu.nju.software.judge.service.submission.impl;

import cn.edu.nju.software.judge.beans.Submission;
import cn.edu.nju.software.judge.beans.SubmissionCode;
import cn.edu.nju.software.judge.beans.SubmissionExample;
import cn.edu.nju.software.judge.dao.SubmissionCodeMapper;
import cn.edu.nju.software.judge.dao.SubmissionMapper;
import cn.edu.nju.software.judge.model.SubmissionModel;
import cn.edu.nju.software.judge.service.submission.SubmissionService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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
//    @CacheEvict
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
//    @Cacheable("submission_findByExample")
    public PageInfo<SubmissionModel> findByExample(SubmissionModel submissionModel,Integer pageNum,Integer pageSize) {
        LOG.info("get from db....");
        PageHelper.startPage(pageNum, pageSize);

        final List<Submission> submissions = submissionMapper.selectBySelective(submissionModel.submission());

        PageInfo page = new PageInfo(submissions);

        List<SubmissionModel> submissionModels = new ArrayList<>(submissions.size());

        for(Submission submission : (List<Submission>)page.getList()){
            submissionModels.add(PO2Model(submission));
        }
        page.setList(submissionModels);
        return page;
    }

    @Override
    public SubmissionModel findBySubmissionId(Integer submissionId) {
        return PO2Model(submissionMapper.selectByPrimaryKey(submissionId));
    }

    @Override
    public SubmissionModel getLastCode(SubmissionModel submissionModel) {
        PageHelper.startPage(1,1,false);
        final List<Submission> submissions = submissionMapper.selectBySelective(submissionModel.submission());
        if(null == submissions || submissions.isEmpty()){
            return null;
        }
        return PO2Model(submissions.get(0));
    }


    @Override
    public List<SubmissionModel> selectByExample(SubmissionExample submissionExample) {
        final List<Submission> submissions = submissionMapper.selectByExample(submissionExample);
        List<SubmissionModel> submissionModels = new ArrayList<>(submissions.size());
        for(Submission s : submissions){
            submissionModels.add(PO2Model(s));
        }
        return submissionModels;
    }

    @Override
    public List<Submission> getListSubmission(SubmissionExample example) {
        List<Submission> submissions = submissionMapper.selectByExample(example);

        return submissions != null ? submissions : null;
    }

    private SubmissionModel PO2Model(SubmissionModel submissionModel, Submission submission){
        BeanUtils.copyProperties(submission,submissionModel);
        return submissionModel;
    }
    private SubmissionModel PO2Model(Submission submission){
        if(null == submission){
            return null;
        }
        SubmissionModel submissionModel = new SubmissionModel();
        BeanUtils.copyProperties(submission,submissionModel);

        return submissionModel;
    }
}
