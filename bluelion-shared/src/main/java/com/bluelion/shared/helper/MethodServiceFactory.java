package com.bluelion.shared.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MethodServiceFactory {

    private Map<String, IMethodService> methodServiceMap;

    @Autowired
    private List<IMethodService> methodServiceList;

    @PostConstruct
    private void init(){
        if (methodServiceMap == null)
            methodServiceMap = new HashMap<String, IMethodService>();
        for (IMethodService method : methodServiceList) {
            String methodName = method.getMethodName();
            if (!methodServiceMap.containsKey(methodName)) {
                methodServiceMap.put(methodName, method);
            }
        }
    }

    public Map<String, IMethodService> getMethodServiceMap() {
        return methodServiceMap;
    }
}
