package code2t.com.dvp.domain;


import code2t.com.dvp.anno.DVCollection;
import code2t.com.dvp.anno.DVFieldSchema;

@DVCollection(name = "test_collection", dimension = 1024)
public class TestCollection {

    @DVFieldSchema(name = "test_id")
    private Long testId;
}
