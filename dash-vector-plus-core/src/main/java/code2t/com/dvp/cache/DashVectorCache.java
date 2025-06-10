package code2t.com.dvp.cache;

import com.aliyun.dashvector.models.requests.CreateCollectionRequest;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static code2t.com.dvp.toolkits.ValidationToolkits.ensureNotBlank;
import static code2t.com.dvp.toolkits.ValidationToolkits.ensureNotNull;

/**
 * 本地缓存
 */
@Data
public class DashVectorCache {

    /**
     * Immutable record for cache entries
     */
    public record ConversionCache(
            String collectionName,
            CreateCollectionRequest createCollectionRequest,
            Boolean autoID) {

        public ConversionCache {
            ensureNotNull(collectionName, "collectionName");
            ensureNotNull(createCollectionRequest, "conversionCache");
        }
    }


    /**
     * Using ConcurrentHashMap with atomic operations for thread safety
     */
    private static final Map<String, ConversionCache> CACHE = new ConcurrentHashMap<>();

    private DashVectorCache() {
    }

    /**
     * Retrieves a cache entry atomically
     */
    public static ConversionCache get(String collectionName) {
        return CACHE.get(
                ensureNotBlank(collectionName, "collectionName")
        );
    }

    /**
     * Stores a cache entry atomically
     */
    public static void put(String collectionName, ConversionCache conversionCache) {
        CACHE.put(
                ensureNotNull(collectionName, "collectionName"),
                ensureNotNull(conversionCache, "conversionCache")
        );
    }

    /**
     * Atomically puts the value if absent
     */
    public static ConversionCache putIfAbsent(String collectionName, ConversionCache conversionCache) {
        return CACHE.putIfAbsent(
                ensureNotNull(collectionName, "collectionName"),
                ensureNotNull(conversionCache, "conversionCache")
        );
    }

    /**
     * Atomically removes the cache entry
     */
    public static ConversionCache remove(String collectionName) {
        return CACHE.remove(
                ensureNotBlank(collectionName, "collectionName")
        );
    }

    /**
     * Clears the cache atomically
     */
    public static void clear() {
        CACHE.clear();
    }
}
