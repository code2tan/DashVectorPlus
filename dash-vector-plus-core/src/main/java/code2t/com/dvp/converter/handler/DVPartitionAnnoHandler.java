package code2t.com.dvp.converter.handler;

import code2t.com.dvp.anno.DVPartition;
import code2t.com.dvp.converter.DashVectorHandlerContext;
import code2t.com.dvp.models.req.CreatePartitionRequest;

import java.util.List;
import java.util.Objects;

public class DVPartitionAnnoHandler implements AnnotationHandler {
    private DVPartition dvPartition;

    /**
     * 判断是否支持处理该类的注解
     *
     * @param entityClass 要处理的类
     * @return 是否支持
     */
    @Override
    public boolean supports(Class<?> entityClass) {
        dvPartition = entityClass.getDeclaredAnnotation(DVPartition.class);
        return Objects.nonNull(dvPartition);
    }


    /**
     * 处理注解业务逻辑
     *
     * @param context     处理上下文（包含构建器等）
     * @param entityClass 要处理的类
     */
    @Override
    public void handle(DashVectorHandlerContext context,
                       Class<?> entityClass) {
        context.setPartitionRequestBuilder(CreatePartitionRequest.builder()
                .partitionName(List.of(dvPartition.name()))
        );
    }
}
