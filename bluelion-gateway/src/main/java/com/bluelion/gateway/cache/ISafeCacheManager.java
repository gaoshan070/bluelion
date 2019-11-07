package com.bluelion.gateway.cache;

import com.bluelion.shared.model.SafeInfo;

public interface ISafeCacheManager {

    SafeInfo getSafeInfo(String requestSourceIndex);

    boolean setSafeInfo(String requestSourceIndex, SafeInfo safeInfo);
}
