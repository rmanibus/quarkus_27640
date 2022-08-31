package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class GreetingResourceTest {
    QueryEntity entity;

    @BeforeEach
    @Transactional
    public void setup() {
        entity = new QueryEntity();
        entity.setDependency(new QueryEntity());
        entity.persist();

    }

    @Test
    @Transactional
    public void testHelloEndpoint() {
        assertEquals(2, QueryEntity.count());
        assertNotNull(entity.getId());
        assertTrue(QueryEntity.deleteById(entity.getId()));
        QueryEntity.getEntityManager().flush();
        assertEquals(1, QueryEntity.count()); // This is failing, expected: 1, actual: 2
    }

}