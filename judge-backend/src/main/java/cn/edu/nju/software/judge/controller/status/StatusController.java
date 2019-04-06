package cn.edu.nju.software.judge.controller.status;

import cn.edu.nju.software.judge.beans.SubmissionExample;
import cn.edu.nju.software.judge.model.SubmissionModel;
import cn.edu.nju.software.judge.service.submission.SubmissionService;
import cn.edu.nju.software.judge.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/status")
public class StatusController {

    @Resource
    private SubmissionService submissionService;

    @GetMapping("/list")
    public Result list(SubmissionExample submissionExample){
        submissionExample.setOrderByClause("add_datetime desc");
        final List<SubmissionModel> submissionModels = submissionService.selectByExample(submissionExample);
        return Result.success(submissionModels);
    }
}
