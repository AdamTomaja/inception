package com.cydercode.inception.game;

import com.cydercode.inception.model.Node;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class TreeTraverserTest {

    @Test
    public void shouldFindParent() throws Exception {
        // given
        Node master = new Node();
        Node parent = new Node();
        master.getChildren().add(parent);
        Node child = new Node();
        parent.getChildren().add(child);
        TreeTraverser treeTraverser = new TreeTraverser();

        // when
        Node foundParent = treeTraverser.findParent(child, master).get();

        // then
        Assertions.assertThat(foundParent).isSameAs(parent);
    }
}