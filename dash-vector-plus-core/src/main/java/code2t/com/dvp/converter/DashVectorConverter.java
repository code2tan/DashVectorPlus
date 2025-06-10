package code2t.com.dvp.converter;

import code2t.com.dvp.anno.DashVectorCollection;
import com.aliyun.dashvector.models.requests.CreateCollectionRequest;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DashVectorConverter {

    public static List<CreateCollectionRequest> convert(Class<?> entityClass) {
        Annotation[] annotations = entityClass.getAnnotations();

        Map<? extends Class<? extends Annotation>, Set<Annotation>> annotationMap = Arrays.stream(annotations)
                .collect(Collectors.groupingBy(Annotation::annotationType, Collectors.toSet()));
        Set<Annotation> dashVectorCollections = annotationMap.get(DashVectorCollection.class);

        return dashVectorCollections.stream()
                .map(annotation -> {
                    DashVectorCollection dashVectorCollection = (DashVectorCollection) annotation;
                    return CreateCollectionRequest.builder()
                            .name(dashVectorCollection.name())
                            .dimension(dashVectorCollection.dimension())
                            .dataType(dashVectorCollection.dataType())
                            .metric(dashVectorCollection.metric())
                            .timeout(dashVectorCollection.timeout())
                            .build();

                }).toList();
    }
}
