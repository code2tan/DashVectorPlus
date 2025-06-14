package code2t.com.dvp.converter.handler;

import code2t.com.dvp.converter.DashVectorHandlerContext;

import java.lang.annotation.Annotation;

public interface AnnotationHandler {
    /**
     * 判断是否支持处理该类的注解
     *
     * @param entityClass 要处理的类
     * @return 是否支持
     */
    boolean supports(Class<?> entityClass);

    /**
     * 处理注解业务逻辑
     *
     * @param context     处理上下文（包含构建器等）
     * @param entityClass 要处理的类
     */
    void handle(
            DashVectorHandlerContext context,
            Class<?> entityClass);
}