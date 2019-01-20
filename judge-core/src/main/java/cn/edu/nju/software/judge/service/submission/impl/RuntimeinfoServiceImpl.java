package cn.edu.nju.software.judge.service.submission.impl;

import cn.edu.nju.software.judge.beans.Runtimeinfo;
import cn.edu.nju.software.judge.dao.RuntimeinfoMapper;
import cn.edu.nju.software.judge.model.RuntimeinfoModel;
import cn.edu.nju.software.judge.service.submission.RuntimeinfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
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
 *
 * @author liuxiaojing
 */
@Service
public class RuntimeinfoServiceImpl implements RuntimeinfoService {

    public static final Logger LOGGER = LoggerFactory.getLogger(RuntimeinfoServiceImpl.class);

    @Resource
    private RuntimeinfoMapper runtimeinfoMapper;

    @Override
    @Transactional
    public void insert(RuntimeinfoModel runtimeinfoModel) {
        Assert.notNull(runtimeinfoModel.getSubmissionId(),"submissionId not null");
        runtimeinfoMapper.insert(runtimeinfoModel.runtimeinfo());

    }


    @Override
    @Cacheable(value = "runtimeinfo",key = "'submissionId_'+#submissionId")
    public RuntimeinfoModel findBySubmissionId(Integer submissionId) {
        return PO2Model(runtimeinfoMapper.selectByPrimaryKey(submissionId));
    }

    private RuntimeinfoModel PO2Model(Runtimeinfo runtimeinfo){
        if(null == runtimeinfo){
            return null;
        }
        try{
            RuntimeinfoModel runtimeinfoModel = new RuntimeinfoModel();
            BeanUtils.copyProperties(runtimeinfo,runtimeinfoModel);
            return runtimeinfoModel;
        }catch (Exception e){
            LOGGER.error("PO2Model fail",e);
        }
        return null;
    }
}
