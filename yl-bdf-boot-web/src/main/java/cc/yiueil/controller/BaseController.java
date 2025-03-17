package cc.yiueil.controller;

import cc.yiueil.vo.ResultVo;
import cc.yiueil.util.JsonUtils;

/**
 * BaseController 基础的控制器
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 23:07
 * @version 1.0
 */
public interface BaseController {
    /**
     * 返回成功结构体 无data 无tips
     * @return ResultVo
     */
    default String success() {
        return JsonUtils.toJsonString(ResultVo.success());
    }

    /**
     * 返回成功结构体 含data 无tips
     * @param obj data
     * @return ResultVo
     */
    default String success(Object obj) {
        return JsonUtils.toJsonString(ResultVo.success(obj));
    }

    /**
     * 返回成功结构体 含data 含tips
     * @param obj data
     * @param tips tips
     * @return ResultVo
     */
    default String success(Object obj, String tips) {
        return JsonUtils.toJsonString(ResultVo.success(obj, tips));
    }

    /**
     * 返回成功结构体 含data 含tips
     * @param obj data
     * @param tips tips
     * @param newToken 重新签发的token
     * @return ResultVo
     */
    default String success(Object obj, String tips, String newToken) {
        return JsonUtils.toJsonString(ResultVo.success(obj, tips, newToken));
    }

    /**
     * 返回失败结构体 无data 含tips
     * @param tips tips
     * @return ResultVo
     */
    default String fail(String tips) {
        return JsonUtils.toJsonString(ResultVo.fail(null, tips));
    }

    /**
     * 返回失败结构体 无data 含tips
     * @param body data
     * @param tips tips
     * @return ResultVo
     */
    default String fail(Object body, String tips) {
        return JsonUtils.toJsonString(ResultVo.fail(body, tips));
    }

    /**
     * 返回错误结构体
     * @param e 引发的异常
     * @param tips tips
     * @return ResultVo
     */
    default String error(String tips, Exception e) {
        return JsonUtils.toJsonString(ResultVo.error(tips, e));
    }

    /**
     * todo 完善加密部分的结果返回内容
     * @param data data
     * @param aesKey 密钥
     * @return ResultVo
     */
    default String successAes(Object data, String aesKey) {
        return null;
    }
}
