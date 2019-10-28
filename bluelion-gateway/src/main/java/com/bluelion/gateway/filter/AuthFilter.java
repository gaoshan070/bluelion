package com.bluelion.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.bluelion.gateway.utils.RedisUtil;
import com.bluelion.shared.model.BaseRequest;
import com.bluelion.shared.model.Result;
import com.bluelion.shared.utils.JsonUtil;
import com.bluelion.shared.utils.RequestUtil;
import com.bluelion.shared.utils.ServiceResultUtil;
import io.netty.buffer.ByteBufAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AuthFilter implements GatewayFilter, Ordered {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisUtil redisUtil;

    @Value("${spring.profiles.active}")
    private String profile;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        exchange.getResponse().setStatusCode(HttpStatus.OK);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        String requestMethod = exchange.getRequest().getMethodValue();
        if("POST".equals(requestMethod)) {
            //从请求里获取Post请求体
            BaseRequest baseRequest = resolveBodyFromRequest(serverHttpRequest);
            //TODO 得到Post请求的请求参数后，解析签名鉴权
            logger.info("请求签名:" + baseRequest.getSign());
            logger.info("请求版本:" + baseRequest.getVersion());
            logger.info("请求来源:" + baseRequest.getRequestSource());
            logger.info("请求内容(原始):" + baseRequest.getParams());
            if(baseRequest == null || StringUtils.isEmpty(baseRequest.getSign())
                || StringUtils.isEmpty(baseRequest.getParams())
                || StringUtils.isEmpty(baseRequest.getRequestSource())){
                    logger.error("请求内容参数为空");
                return exchange.getResponse().writeWith(Mono.just(this.getBodyBuffer(exchange.getResponse(), ServiceResultUtil.illegal())));
            }
            String clientIp = RequestUtil.getClientIp(exchange);
            logger.info("请求IP:" + clientIp);
//            baseRequest.getSign();

            //TODO 重新封装Request 向下传递
            //下面的将请求体再次封装写回到request里，传到下一级，否则，由于请求体已被消费，后续的服务将取不到值
            URI uri = serverHttpRequest.getURI();
            ServerHttpRequest request = serverHttpRequest.mutate().uri(uri).build();
            DataBuffer bodyDataBuffer = stringBuffer(JsonUtil.bean2JsonStr(baseRequest));
            Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);
            request = new ServerHttpRequestDecorator(request) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return bodyFlux;
                }
            };
            //封装request，传给下一级
            return chain.filter(exchange.mutate().request(request).build());
        } else {
            return exchange.getResponse().writeWith(Mono.just(this.getBodyBuffer(exchange.getResponse(), ServiceResultUtil.unsupportedMethod())));
        }

        //后端调用跳过验签
//        boolean skipAuth = Boolean.valueOf(exchange.getRequest().getQueryParams().getFirst("skipAuth"));
//        if (!"dev".equals(profile)&&!"pre".equals(profile)) {
//            skipAuth = false;
//        }
//        boolean skipAuth = false;
//        if (!skipAuth) {
//            String sign = exchange.getRequest().getQueryParams().getFirst("sign");
//            if (StringUtils.isEmpty(sign)) {
//                //没有验签参数
//                result.setCode(ResultCodeEnum.SGIN_EMPTY.getCode());
//                result.setMsg(ResultCodeEnum.SGIN_EMPTY.getMsg());
//                return exchange.getResponse().writeWith(Mono.just(this.getBodyBuffer(exchange.getResponse(), result)));
//            }
//            String publicKey = exchange.getRequest().getHeaders().getFirst("publicKey");
//            if (StringUtils.isBlank(publicKey)) {
//                publicKey = exchange.getRequest().getQueryParams().getFirst("p");
//                if (StringUtils.isBlank(publicKey)) {
//                    //没有公钥
//                    result.setCode(ResultCodeEnum.PUBLICKEY_EMPTY.getCode());
//                    result.setMsg(ResultCodeEnum.PUBLICKEY_EMPTY.getMsg());
//                    return exchange.getResponse().writeWith(Mono.just(this.getBodyBuffer(exchange.getResponse(), result)));
//                }
//            }
//            String privateKey = redisUtil.getValue(publicKey);
//            if (!StringUtils.isEmpty(privateKey)) {
//                TreeMap<String, List<String>> parameterMap = new TreeMap<>(exchange.getRequest().getQueryParams());
//                //验签
//                StringBuilder sb = new StringBuilder();
//                parameterMap.entrySet().forEach(stringEntry -> {
//                    if (!StringUtils.equalsIgnoreCase(stringEntry.getKey(), "sign") && !StringUtils.equalsIgnoreCase(stringEntry.getKey(), "p")) {
//                        if (!CollectionUtils.isEmpty(stringEntry.getValue())) {
//                            sb.append(stringEntry.getKey());
//                            sb.append("=");
//                            sb.append(stringEntry.getValue().stream().findFirst().get());
//                        }
//                    }
//                });
//                sb.append("privateKey=");
//                sb.append(privateKey);
//                String urlString = sb.toString();
////                try {
////                    urlString = URLEncoder.encode(sb.toString(), "UTF-8");
////                } catch (UnsupportedEncodingException e) {
////                    logger.error("url encode fail");
////                }
//                logger.info(urlString);
//                String serverSign = DigestUtils.md5Hex(urlString);
//                logger.info(serverSign);
//                if (!serverSign.equals(sign)) {
//                    //验签不通过
//                    result.setCode(ResultCodeEnum.SIGN_INVALID.getCode());
//                    result.setMsg(ResultCodeEnum.SIGN_INVALID.getMsg());
//                    return exchange.getResponse().writeWith(Mono.just(this.getBodyBuffer(exchange.getResponse(), result)));
//                }
//            } else {
//                //私钥过期
//                result.setCode(ResultCodeEnum.PRIVATEKEY_EXPIRE.getCode());
//                result.setMsg(ResultCodeEnum.PRIVATEKEY_EXPIRE.getMsg());
//                return exchange.getResponse().writeWith(Mono.just(this.getBodyBuffer(exchange.getResponse(), result)));
//            }
//        }


//        return chain.filter(exchange);
    }

    /**
     * 从Flux<DataBuffer>中获取字符串的方法
     * @return 请求体
     */
    private BaseRequest resolveBodyFromRequest(ServerHttpRequest serverHttpRequest) {
        //获取请求体
        try {
            Flux<DataBuffer> body = serverHttpRequest.getBody();

            AtomicReference<String> bodyRef = new AtomicReference<>();
            body.subscribe(buffer -> {
                CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
                DataBufferUtils.release(buffer);
                bodyRef.set(charBuffer.toString());
            });
            //获取request body
            String requestBody = formatStr(bodyRef.get());
            return JsonUtil.jsonStr2Bean(requestBody, BaseRequest.class);
        }catch (Exception e) {
            logger.error("解析请求参数失败");
            return null;
        }
    }

    /**
     * 去掉空格,换行和制表符
     * @param str
     * @return
     */
    private String formatStr(String str){
        if (str != null && str.length() > 0) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            return m.replaceAll("");
        }
        return str;
    }


    private DataBuffer stringBuffer(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }
    /**
     * 封装返回值
     *
     * @param response
     * @param result
     * @return
     */
    private DataBuffer getBodyBuffer(ServerHttpResponse response, Result result) {
        return response.bufferFactory().wrap(JSONObject.toJSONBytes(result));
    }

    @Override
    public int getOrder() {
        return -200;
    }
}
