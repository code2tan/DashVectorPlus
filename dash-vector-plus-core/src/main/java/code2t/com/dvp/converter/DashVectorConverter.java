package code2t.com.dvp.converter;

import code2t.com.dvp.anno.DVCollection;
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
        Set<Annotation> DVCollections = annotationMap.get(DVCollection.class);

        return DVCollections.stream()
                .map(annotation -> {
                    DVCollection DVCollection = (DVCollection) annotation;
                    return CreateCollectionRequest.builder()
                            .name(DVCollection.name())
                            .dimension(DVCollection.dimension())
                            .dataType(DVCollection.dataType())
                            .metric(DVCollection.metric())
                            .timeout(DVCollection.timeout())
                            .build();

                }).toList();
    }
}
