package com.bluelion.content.api;

import com.bluelion.content.request.QaListRequest;
import com.bluelion.content.response.QaListResponse;
import com.bluelion.content.service.ContentService;
import com.bluelion.shared.helper.BaseService;
import com.bluelion.shared.helper.IMethodService;
import com.bluelion.shared.model.*;
import com.bluelion.shared.utils.JsonUtil;
import com.bluelion.shared.utils.ServiceResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QaContentService extends ContentBaseService implements IMethodService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ContentService contentService;

    @Override
    public ApiRequestBody getRequestBody(BaseRequest baseRequest) throws Exception {
        QaListRequest qaListRequest = new QaListRequest();
        qaListRequest.parseRequest(baseRequest);
        return qaListRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(ApiRequestBody apiRequestBody) {
        QaListRequest qaListRequest = (QaListRequest) apiRequestBody;
        User user = userService.userDetail(qaListRequest.getUserId());
        return user;
    }

    @Override
    public String getMethodName() {
        return "qa_content";
    }

    @Override
    public Result execute4Server() {
        return null;
    }

    @Override
    public Result execute4Client(ApiRequestBody apiRequestBody, User user) throws Exception {
        QaListRequest qaListRequest = (QaListRequest) apiRequestBody;
        return dealQaList(qaListRequest, user);
    }

    private Result dealQaList(QaListRequest qaListRequest, User user){
        logger.info("处理QA内容列表请求");
        if(qaListRequest.getUserId() == null || user == null) {
            logger.error("QA内容获取参数不正确");
            return ServiceResultUtil.illegal("Invalid Params");
        }
        List<Content> qaList = contentService.getQaList();
        QaListResponse result = new QaListResponse();
        result.setQaList(qaList);
        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(result));
    }
}
