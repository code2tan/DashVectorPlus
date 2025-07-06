package code2t.com.dvp.cache;

import com.aliyun.dashvector.DashVectorClient;
import com.aliyun.dashvector.DashVectorCollection;
import com.aliyun.dashvector.models.responses.Response;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import static code2t.com.dvp.toolkits.ValidationToolkits.ensureNotBlank;
import static code2t.com.dvp.toolkits.ValidationToolkits.ensureNotNull;

/**
 * 本地缓存
 */
@Data
@Slf4j
public class DashVectorCollectionCache {

    /**
     * Using ConcurrentHashMap with atomic operations for thread safety
     */
    private static final Map<String, DashVectorCollection> CACHE = new ConcurrentHashMap<>();

    private DashVectorCollectionCache() {
    }

    public static void init(DashVectorClient client) {
        Response<List<String>> response = client.list();
        if (!response.isSuccess()) {
            log.warn("failed to init dash vector cache [{}]", response.getMessage());
            return;
        }

        if (response.isSuccess()) {
            List<CompletableFuture<DashVectorCollection>> futures = response.getOutput().stream()
                    .map(collectionName -> CompletableFuture.supplyAsync(() -> client.get(collectionName)))
                    .toList();

            List<DashVectorCollection> collections = futures.stream()
                    .map(CompletableFuture::join)
                    .toList();

            for (DashVectorCollection collection : collections) {
                put(collection.getName(), collection);
            }
        }
    }

    /**
     * Retrieves a cache entry atomically
     */
    public static DashVectorCollection get(String collectionName) {
        return CACHE.get(
                ensureNotBlank(collectionName, "collectionName")
        );
    }

    public static Boolean exist(String collectionName) {
        return CACHE.containsKey(
                ensureNotBlank(collectionName, "collectionName")
        );
    }

    /**
     * Stores a cache entry atomically
     */
    public static void put(String collectionName, DashVectorCollection DashVectorCollection) {
        CACHE.put(
                ensureNotNull(collectionName, "collectionName"),
                ensureNotNull(DashVectorCollection, "DashVectorCollection")
        );
    }

    /**
     * Atomically puts the value if absent
     */
    public static DashVectorCollection putIfAbsent(String collectionName, DashVectorCollection DashVectorCollection) {
        return CACHE.putIfAbsent(
                ensureNotNull(collectionName, "collectionName"),
                ensureNotNull(DashVectorCollection, "DashVectorCollection")
        );
    }

    /**
     * Atomically removes the cache entry
     */
    public static DashVectorCollection remove(String collectionName) {
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
