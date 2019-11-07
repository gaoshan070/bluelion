package com.bluelion.content.api;

import com.bluelion.content.request.InitRequest;
import com.bluelion.content.request.QaListRequest;
import com.bluelion.content.response.InitResponse;
import com.bluelion.content.service.AdvertiseService;
import com.bluelion.content.service.PrintTemplateService;
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
public class InitService extends BaseService implements IMethodService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AdvertiseService advertiseService;

    @Autowired
    PrintTemplateService printTemplateService;

    @Override
    public ApiRequestBody getRequestBody(BaseRequest baseRequest) throws Exception {
        InitRequest initRequest = new InitRequest();
        initRequest.parseRequest(baseRequest);
        return initRequest;
    }

    @Override
    public boolean checkUser() {
        return false;
    }

    @Override
    public User getUser(ApiRequestBody apiRequestBody) {
        return null;
    }

    @Override
    public boolean validateRequestToken(int userId, ApiRequestBody apiRequestBody) throws Exception {
        return false;
    }

    @Override
    public String getMethodName() {
        return "init";
    }

    @Override
    public Result execute4Server() {
        return null;
    }

    @Override
    public Result execute4Client(ApiRequestBody apiRequestBody, User user) throws Exception {
        InitRequest initRequest = (InitRequest) apiRequestBody;
        return dealInit(initRequest);
    }

    private Result dealInit(InitRequest initRequest) {
        logger.info("处理初始化请求");
        InitResponse initResponse = new InitResponse();
        List<Advertise> initAds = advertiseService.getStartAds();
        initResponse.setAdList(initAds);
        List<PrintTemplate> printTemplates = printTemplateService.getPrintTemplates();
        initResponse.parsePrintTemplate(printTemplates);
        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(initResponse));
    }
}
