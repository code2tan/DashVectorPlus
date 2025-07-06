package code2t.com.dvp.toolkits;

import code2t.com.dvp.anno.DVCollection;

public class DVCollectionToolkits {
    public static String parseCollectionName(Class<?> entityClass) {
        DVCollection declaredAnnotation = entityClass.getDeclaredAnnotation(DVCollection.class);
        return declaredAnnotation.name().isEmpty() ? entityClass.getName() : declaredAnnotation.name();
    }
}
