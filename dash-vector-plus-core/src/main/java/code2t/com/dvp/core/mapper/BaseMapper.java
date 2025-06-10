package code2t.com.dvp.core.mapper;

import code2t.com.dvp.anno.DashVectorCollection;
import code2t.com.dvp.cache.DashVectorCache;
import code2t.com.dvp.core.conditions.Wrapper;
import code2t.com.dvp.service.AbstractClientBuilder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static code2t.com.dvp.toolkits.ValidationToolkits.ensureNotNull;

public abstract class BaseMapper<T> extends AbstractClientBuilder {
    /**
     * 创建通用构建器实例
     *
     * @param wrapper 构建器实例
     * @return 返回构建器实例
     */
    public <W> W lambda(Wrapper<W, T> wrapper) {
        // 获取实例化的类的类型参数T
        Type type = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Class<T> entityType = (Class<T>) type;
        return lambda(wrapper, entityType);
    }

    /**
     * 创建通用构建器实例
     *
     * @param wrapper    Wrapper接口
     * @param entityType 实体类型
     * @param <W>        wrapper
     * @return 返回构建器实例
     */
    public <W> W lambda(Wrapper<W, T> wrapper, Class<T> entityType) {
        // 从实体类上获取@DashVectorCollection注解
        ensureNotNull(entityType.getAnnotation(DashVectorCollection.class),
                "Entity type %s is not annotated with @DashVectorCollection.",
                entityType.getName());

        // 初始化构建器实例
        return wrapper.init(
                getClient(),
                DashVectorCache.get(entityType.getName()),
                entityType
        ).wrapper();
    }
}