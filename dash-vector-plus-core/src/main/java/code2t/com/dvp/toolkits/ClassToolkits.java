package code2t.com.dvp.toolkits;

import code2t.com.dvp.anno.DVCollection;
import code2t.com.dvp.exception.Exceptions;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 获取包路径下的Class
 */
public class ClassToolkits {

    private ClassToolkits() {
    }

    private final static String CLASS = "*.class";

    private static final ResourcePatternResolver RESOURCE_PATTERN_RESOLVER =
            new PathMatchingResourcePatternResolver();

    public static List<Class<?>> getClass(List<String> packages) {
        return Optional.ofNullable(packages)
                .orElseThrow(() -> new RuntimeException("model package is null, please configure the [packagesScan] parameter"))
                .stream()
                .map(RESOURCE_PATTERN_RESOLVER_PROCESS)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }


    private final static Function<String, List<Class<?>>> RESOURCE_PATTERN_RESOLVER_PROCESS = package_ -> {
        String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                + org.springframework.util.ClassUtils.convertClassNameToResourcePath(package_ + ".") + CLASS;

        try {
            var readerFactory = new CachingMetadataReaderFactory(RESOURCE_PATTERN_RESOLVER);
            Resource[] resources = RESOURCE_PATTERN_RESOLVER.getResources(pattern);
            return Arrays.stream(resources)
                    .map(resource -> safelyGetClass(resource, readerFactory))
                    .flatMap(Optional::stream)
                    .toList();
        } catch (IOException e) {
            throw Exceptions.runtime("Failed to resolve resources for pattern: " + pattern, e);
        }
    };

    private static Optional<Class<?>> safelyGetClass(Resource resource, MetadataReaderFactory readerFactory) {
        try {
            var reader = readerFactory.getMetadataReader(resource);
            var className = reader.getClassMetadata().getClassName();
            var clazz = Class.forName(className);
            return clazz.isAnnotationPresent(DVCollection.class)
                    ? Optional.of(clazz)
                    : Optional.empty();
        } catch (ClassNotFoundException e) {
            throw Exceptions.runtime("Class not found for resource: " + resource, e);
        } catch (IOException e) {
            throw Exceptions.runtime("Failed to read metadata for resource: " + resource, e);
        } catch (Exception e) {
            throw Exceptions.runtime("Unexpected error processing resource: " + resource, e);
        }
    }
}
