package code2t.com.dvp.cache;

import com.aliyun.dashvector.DashVectorCollection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashVectorCollectionCache {
    private static final Map<String, DashVectorCollection> EXIST_CACHE = new HashMap<>();

    public static void init(List<DashVectorCollection> collections) {
        collections.forEach(c -> EXIST_CACHE.put(c.getName(), c));
    }

    public static Boolean exists(String name) {
        return EXIST_CACHE.containsKey(name);
    }
}
