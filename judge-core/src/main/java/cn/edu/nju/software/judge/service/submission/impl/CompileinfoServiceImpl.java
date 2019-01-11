package cn.edu.nju.software.judge.service.submission.impl;

import cn.edu.nju.software.judge.beans.Compileinfo;
import cn.edu.nju.software.judge.dao.CompileinfoMapper;
import cn.edu.nju.software.judge.model.CompileinfoModel;
import cn.edu.nju.software.judge.service.submission.CompileinfoService;
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
 */
@Service
public class CompileinfoServiceImpl implements CompileinfoService {

    public static final Logger LOGGER = LoggerFactory.getLogger(CompileinfoServiceImpl.class);

    @Resource
    private CompileinfoMapper compileinfoMapper;


    @Override
    @Transactional
    public void insert(CompileinfoModel compileinfoModel) {
        Assert.notNull(compileinfoModel.getSubmissionId(),"submissionId not null");
        Compileinfo compileinfo = compileinfoModel.compileinfo();
        compileinfoMapper.insert(compileinfo);
    }


    @Override
    @Cacheable(value="compileinfo",key = "'submissionId_'+#submissionId")
    public CompileinfoModel findBySubmissionId(Integer submissionId) {
        Assert.notNull(submissionId,"submissionId not null");
        final Compileinfo compileinfo = compileinfoMapper.selectByPrimaryKey(submissionId);
        return PO2Model(compileinfo);
    }

    private CompileinfoModel PO2Model(Compileinfo compileinfo){
        if(null == compileinfo){
            return null;
        }
        try{
            CompileinfoModel compileinfoModel = new CompileinfoModel();
            BeanUtils.copyProperties(compileinfo,compileinfoModel);
            return compileinfoModel;
        }catch (Exception e){
            LOGGER.error("PO2Model fail",e);
        }
        return null;

    }
}
