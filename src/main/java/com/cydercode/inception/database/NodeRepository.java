package com.cydercode.inception.database;

import com.cydercode.inception.model.Node;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class NodeRepository {

    @Autowired
    private EntityStore entityStore;

    @Autowired
    private Environment environment;

    public NodeEntity add(Node node) throws DatabaseException {
        NodeEntity nodeEntity = entityStore.getPrimaryIndex(String.class, NodeEntity.class).put(node.toNodeEntity());
        entityStore.sync();
        environment.sync();
        return nodeEntity;
    }

    public List<Node> getAll() throws DatabaseException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        List<Node> list = new ArrayList<>();
        EntityCursor<NodeEntity> entities = entityStore.getPrimaryIndex(String.class, NodeEntity.class).entities();

        try {
            Iterator<NodeEntity> iterator = entities.iterator();
            while (iterator.hasNext()) {
                list.add(Node.fromNodeEntity(iterator.next()));
            }
        } finally {
            entities.close();
        }
        return list;
    }
}
