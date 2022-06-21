package com.essers.wms.movement.views;

import com.essers.wms.movement.data.entity.Pickinglist;
import com.vaadin.flow.component.grid.Grid;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PickinglistViewTest {

    @Test
    public void testGridPopulated() {
        Grid grid=new Grid(Pickinglist.class);
        Assert.assertEquals(Pickinglist.class, grid.getBeanType());
    }

}