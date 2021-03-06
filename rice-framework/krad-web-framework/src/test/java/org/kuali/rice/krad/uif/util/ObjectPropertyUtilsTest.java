/**
 * Copyright 2005-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.krad.uif.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.component.BindingInfo;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.container.CollectionGroupBase;
import org.kuali.rice.krad.uif.container.CollectionGroupBuilder;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.container.GroupBase;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.uif.element.Message;
import org.kuali.rice.krad.uif.element.ViewHeader;
import org.kuali.rice.krad.uif.layout.StackedLayoutManager;
import org.kuali.rice.krad.uif.layout.StackedLayoutManagerBase;
import org.kuali.rice.krad.uif.lifecycle.ViewLifecycle;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.uif.view.FormView;
import org.kuali.rice.krad.uif.view.ViewPresentationControllerBase;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.UifFormBase;

public class ObjectPropertyUtilsTest extends ProcessLoggingUnitTest {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface TestAnnotation {
        String afoo();
    }

    public static class TestBean implements Serializable {

        private static final long serialVersionUID = 1L;

        public TestBean() {}

        private String rwProp;
        private TestBeanTwo complexProp;

        public String getRwProp() {
            return this.rwProp;
        }

        public void setRwProp(String r) {
            this.rwProp = r;
        }

        private String woProp;

        public void setWoProp(String w) {
            this.woProp = w;
        }

        private String roProp;

        @TestAnnotation(afoo = "abar")
        public String getRoProp() {
            return this.roProp;
        }

        private Boolean bitProp;

        public boolean isBitProp() {
            return bitProp != null && bitProp;
        }

        public Boolean getBitProp() {
            return bitProp;
        }

        public void setBitProp(Boolean bitProp) {
            this.bitProp = bitProp;
        }

        private boolean booleanProp;

        public boolean isBooleanProp() {
            return booleanProp;
        }

        public void setBooleanProp(boolean booleanProp) {
            this.booleanProp = booleanProp;
        }

        private Timestamp timestampProp;

        public Timestamp getTimestampProp() {
            return timestampProp;
        }

        public void setTimestampProp(Timestamp timestampProp) {
            this.timestampProp = timestampProp;
        }

        private Date dateProp;

        public Date getDateProp() {
            return dateProp;
        }

        public void setDateProp(Date dateProp) {
            this.dateProp = dateProp;
        }

        private int intProp;

        public int getIntProp() {
            return intProp;
        }

        private BigDecimal bigDecimalProp;

        public BigDecimal getBigDecimalProp() {
            return bigDecimalProp;
        }

        public void setBigDecimalProp(BigDecimal bigDecimalProp) {
            this.bigDecimalProp = bigDecimalProp;
        }

        public void setIntProp(int intProp) {
            this.intProp = intProp;
        }

        private Integer integerProp;

        public Integer getIntegerProp() {
            return integerProp;
        }

        public void setIntegerProp(Integer integerProp) {
            this.integerProp = integerProp;
        }

        private TestBean next;

        public TestBean getNext() {
            return next;
        }

        public void setNext(TestBean next) {
            this.next = next;
        }

        private List<String> stuffs;

        public List<String> getStuffs() {
            return stuffs;
        }

        public void setStuffs(List<String> stuffs) {
            this.stuffs = stuffs;
        }

        private Object[] arrayProp;

        public Object[] getArrayProp() {
            return arrayProp;
        }

        public void setArrayProp(Object[] arrayProp) {
            this.arrayProp = arrayProp;
        }

        private Map<String, Object> mapProp;

        public Map<String, Object> getMapProp() {
            return this.mapProp;
        }

        public void setMapProp(Map<String, Object> mapProp) {
            this.mapProp = mapProp;
        }

        /**
         * @return the complexProp
         */
        public TestBeanTwo getComplexProp() {
            return this.complexProp;
        }

        /**
         * @param complexProp the complexProp to set
         */
        public void setComplexProp(TestBeanTwo complexProp) {
            this.complexProp = complexProp;
        }
    }

    public static class TestBeanTwo {

        private String fooProp;

        /**
         * @return the fooProp
         */
        public String getFooProp() {
            return this.fooProp;
        }

        /**
         * @param fooProp the fooProp to set
         */
        public void setFooProp(String fooProp) {
            this.fooProp = fooProp;
        }
    }

    @Test
    public void testSetBoolean() {
        TestBean tb = new TestBean();
        ObjectPropertyUtils.setPropertyValue(tb, "booleanProp", "true");
        assertTrue(tb.isBooleanProp());
    }

    @Test
    public void testGetPropertyDescriptor() {
        Map<String, PropertyDescriptor> pds = ObjectPropertyUtils.getPropertyDescriptors(TestBean.class);
        assertNotNull(pds.get("rwProp"));
        assertNotNull(pds.get("roProp"));
        assertNotNull(pds.get("woProp"));
        assertNull(pds.get("foobar"));
    }

    @Test
    public void testGet() {
        TestBean tb = new TestBean();
        tb.setRwProp("foobar");
        assertEquals("foobar", ObjectPropertyUtils.getPropertyValue(tb, "rwProp"));

        tb.roProp = "barbaz";
        assertEquals("barbaz", ObjectPropertyUtils.getPropertyValue(tb, "roProp"));

        try {
            ObjectPropertyUtils.getPropertyValue(tb, "woProp");
            // KULRICE-10677 - should return null - fail("expected exception");
        } catch (RuntimeException e) {
            // KULRICE-10677 - should return null
            throw e;
        }
    }

    @Test
    public void testLookup() {
        TestBean tb = new TestBean();
        tb.roProp = "barbaz";
        assertEquals("barbaz", ObjectPropertyUtils.getPropertyValue(tb, "roProp"));

        Map<String, Object> tm = new java.util.HashMap<String, Object>();
        tb.setMapProp(tm);
        tm.put("barbaz", "hooray!");
        tm.put("bar.baz", "hoorah!");
        tm.put("bar.[baz]", "foobah!");
        tm.put("b'('r.[\"ain)\"s]", "zombie!");
        assertEquals("hooray!", ObjectPropertyUtils.getPropertyValue(tb, "mapProp[barbaz]"));
        assertEquals("hooray!", ObjectPropertyUtils.getPropertyValue(tb, "mapProp['barbaz']"));
        assertEquals("hooray!", ObjectPropertyUtils.getPropertyValue(tb, "mapProp[\"barbaz\"]"));
        assertEquals("hoorah!", ObjectPropertyUtils.getPropertyValue(tb, "mapProp[bar.baz]"));
        assertEquals("foobah!", ObjectPropertyUtils.getPropertyValue(tb, "mapProp[bar.[baz]]"));
        assertEquals("zombie!", ObjectPropertyUtils.getPropertyValue(tb, "mapProp['b'('r.[\"ain)\"s]']"));
        assertEquals("zombie!", ObjectPropertyUtils.getPropertyValue(tb, "mapProp[b'('r.[\"ain)\"s]]"));

        TestBean tb2 = new TestBean();
        tb2.setRwProp("foodbar");
        tb.setNext(tb2);
        tm.put("blah", new Object[]{"next", "rwProp"});
        tm.put("baz", tb2);
        assertTrue(ObjectPropertyUtils.isReadableProperty(tb, "mapProp[\"baz\"].rwProp"));
        assertEquals("barbaz", ObjectPropertyUtils.getPropertyValue(tb, "roProp"));
        assertEquals("foodbar", ObjectPropertyUtils.getPropertyValue(tb, "next.rwProp"));

        tb.setStuffs(Arrays.asList(new String[]{"foo", "bar", "baz",}));
        assertEquals("bar", ObjectPropertyUtils.getPropertyValue(tb, "stuffs[1]"));

        TestBean rb = new TestBean();
        TestBean nb = new TestBean();
        TestBean lb = new TestBean();
        rb.setNext(nb);
        nb.setNext(lb);
        assertEquals(String.class, ObjectPropertyUtils.getPropertyType(rb, "next.next.rwProp"));
        rb.setRwProp("baz");
        nb.setRwProp("bar");
        lb.setRwProp("foo");
        assertEquals("foo", ObjectPropertyUtils.getPropertyValue(rb, "next.next.rwProp"));
    }

    @Test
    public void testSet() {
        TestBean tb = new TestBean();
        ObjectPropertyUtils.setPropertyValue(tb, "rwProp", "foobar");
        assertEquals("foobar", tb.getRwProp());

        ObjectPropertyUtils.setPropertyValue(tb, "woProp", "barbaz");
        assertEquals("barbaz", tb.woProp);

        try {
            ObjectPropertyUtils.setPropertyValue(tb, "roProp", "bazfoo");
            fail("expected exception");
        } catch (Exception E) {
            // OK!
        }

        long now = System.currentTimeMillis();
        ObjectPropertyUtils.setPropertyValue(tb, "dateProp", new java.sql.Date(now));
        assertEquals(now, tb.getDateProp().getTime());
    }

    @Test
    public void testPathSet() {
        TestBean tb = new TestBean();
        ObjectPropertyUtils.setPropertyValue(tb, "rwProp", "bar");
        assertEquals("bar", tb.getRwProp());
        ObjectPropertyUtils.setPropertyValue(tb, "next", new TestBean());
        ObjectPropertyUtils.setPropertyValue(tb, "next.next", new TestBean());
        ObjectPropertyUtils.setPropertyValue(tb, "next.next.woProp", "baz");
        assertEquals("baz", tb.getNext().getNext().woProp);
    }

    @Test
    public void testBulk() {
        Map<String, String> pd = new java.util.HashMap<String, String>();
        pd.put("rwProp", "foobar");
        pd.put("intProp", "3");
        pd.put("booleanProp", "true");
        pd.put("stuffs", "foo,bar,baz");
        for (int i = 0; i < 10000; i++) {
            TestBean tb = new TestBean();
            ObjectPropertyUtils.copyPropertiesToObject(pd, tb);
            assertEquals("foobar", tb.getRwProp());
            assertEquals(3, tb.getIntProp());
            assertEquals(true, tb.isBooleanProp());
            assertEquals(3, tb.getStuffs().size());
            assertEquals("foo", tb.getStuffs().get(0));
            assertEquals("bar", tb.getStuffs().get(1));
            assertEquals("baz", tb.getStuffs().get(2));
        }
    }

    @Test
    public void testReadWriteCheck() {
        TestBean tb = new TestBean();
        assertTrue(ObjectPropertyUtils.isReadableProperty(tb, "rwProp"));
        assertTrue(ObjectPropertyUtils.isWritableProperty(tb, "rwProp"));
        assertTrue(ObjectPropertyUtils.isReadableProperty(tb, "roProp"));
        assertFalse(ObjectPropertyUtils.isWritableProperty(tb, "roProp"));
        assertFalse(ObjectPropertyUtils.isReadableProperty(tb, "woProp"));
        assertTrue(ObjectPropertyUtils.isWritableProperty(tb, "woProp"));
    }

    @Test
    public void testKradUifTemplateHeaderMetadata() {
        FormView formView = new FormView();
        ViewHeader viewHeader = new ViewHeader();
        formView.setHeader(viewHeader);
        Message headerMetadataMessage = new Message();
        viewHeader.setMetadataMessage(headerMetadataMessage);
        assertSame(headerMetadataMessage, ObjectPropertyUtils.getPropertyValue(formView, "header.metadataMessage"));
    }

    /**
     * Collection list item type, for testing UIF interaction with ObjectPropertyUtils.
     */
    public static class CollectionTestItem {

        /**
         * A string property, called foobar.
         */
        String foobar;

        /**
         * @return the foobar
         */
        public String getFoobar() {
            return this.foobar;
        }

        /**
         * @param foobar the foobar to set
         */
        public void setFoobar(String foobar) {
            this.foobar = foobar;
        }

    }

    /**
     * Reference to a collection, for testing UIF interaction with ObjectPropertyUtils.
     */
    public static class CollectionTestListRef {

        /**
         * The collection.
         */
        List<CollectionTestItem> bar;

        /**
         * Mapping of new line items.
         */
        Map<String, CollectionTestItem> baz;

        /**
         * @return the bar
         */
        public List<CollectionTestItem> getBar() {
            return this.bar;
        }

        /**
         * @param bar the bar to set
         */
        public void setBar(List<CollectionTestItem> bar) {
            this.bar = bar;
        }

        /**
         * @return the baz
         */
        public Map<String, CollectionTestItem> getBaz() {
            return this.baz;
        }

        /**
         * @param baz the baz to set
         */
        public void setBaz(Map<String, CollectionTestItem> baz) {
            this.baz = baz;
        }

    }

    /**
     * Mock collection form for UIF interaction with ObjectPropertyUtils.
     */
    public static class CollectionTestForm extends UifFormBase {

        private static final long serialVersionUID = 1798800132492441253L;

        /**
         * Reference to a data object that has a collection.
         */
        CollectionTestListRef foo;

        /**
         * @return the foo
         */
        public CollectionTestListRef getFoo() {
            return this.foo;
        }

        /**
         * @param foo the foo to set
         */
        public void setFoo(CollectionTestListRef foo) {
            this.foo = foo;
        }

    }

    @Test
    public void testKradUifCollectionGroupBuilder() throws Throwable {
        UifUnitTestUtils.establishMockConfig(ObjectPropertyUtilsTest.class.getSimpleName());
        UifUnitTestUtils.establishMockUserSession("testuser");
        try {
        // Performance medium generates this property path:
        // newCollectionLines['newCollectionLines_'mediumCollection1'_.subList']

        // Below recreates the stack trace that ensued due to poorly escaped quotes,
        // and proves that the parser works around bad quoting in a manner similar to BeanWrapper 

        final CollectionGroupBuilder collectionGroupBuilder = new CollectionGroupBuilder();
        final CollectionTestForm form = new CollectionTestForm();
        CollectionTestItem item = new CollectionTestItem();
        item.setFoobar("barfoo");
        ObjectPropertyUtils.setPropertyValue(form, "foo.baz['foo_bar_'badquotes'_.foobar']", item);
        assertEquals("barfoo", form.foo.baz.get("foo_bar_'badquotes'_.foobar").foobar);

        final FormView view = new FormView();
        view.setFormClass(CollectionTestForm.class);
        view.setViewHelperService(new ViewHelperServiceImpl());
        view.setPresentationController(new ViewPresentationControllerBase());
        view.setAuthorizer(UifUnitTestUtils.getAllowMostViewAuthorizer());

        final CollectionGroup collectionGroup = new CollectionGroupBase();
        collectionGroup.setCollectionObjectClass(CollectionTestItem.class);
        collectionGroup.setAddLinePropertyName("addLineFoo");

        StackedLayoutManager layoutManager = new StackedLayoutManagerBase();
        Group lineGroupPrototype = new GroupBase();
        layoutManager.setLineGroupPrototype(lineGroupPrototype);
        collectionGroup.setLayoutManager(layoutManager);

        BindingInfo addLineBindingInfo = new BindingInfo();
        addLineBindingInfo.setBindingPath("foo.baz['foo_bar_'badquotes'_.foobar']");
        collectionGroup.setAddLineBindingInfo(addLineBindingInfo);

        BindingInfo collectionBindingInfo = new BindingInfo();
        collectionBindingInfo.setBindingPath("foo.bar");
        collectionGroup.setBindingInfo(collectionBindingInfo);

        ViewLifecycle.encapsulateLifecycle(view, form, null, null, new Runnable() {
            @Override
            public void run() {
                collectionGroupBuilder.build(view, form, collectionGroup.<CollectionGroup> copy());
            }});
    } finally {
        GlobalVariables.setUserSession(null);
        GlobalVariables.clear();
        GlobalResourceLoader.stop();
    }
    }

    @Test
    public void testSetStringMapFromInt() {
        Action action = new Action();
        ObjectPropertyUtils.setPropertyValue(action, "actionParameters['lineIndex']", 34);
        assertEquals("34", action.getActionParameter("lineIndex"));
    }

    @Test
    public void testClassNavigation() {
        assertEquals(String.class, ObjectPropertyUtils.getPropertyType(TestBean.class, "complexProp.fooProp"));
        
        try {
            // valid first reference, invalid second reference
            assertEquals(null, ObjectPropertyUtils.getPropertyType(TestBean.class, "complexProp.foobar"));
            // NULL is ok - fail("KULRICE-10677 - is this ok?");
        } catch (IllegalArgumentException e) {
            // IAE is not ok - KULRICE-10677 is this ok?
            throw e;
        }
        
        try {
            // invalid single reference
            assertEquals(null, ObjectPropertyUtils.getPropertyType(TestBean.class, "foo"));
            // NULL is ok - fail("KULRICE-10677 - is this ok?");
        } catch (IllegalArgumentException e) {
            // IAE is not ok - KULRICE-10677 is this ok?
            throw e;
        }

        try {
            // invalid first reference
            assertEquals(null, ObjectPropertyUtils.getPropertyType(TestBean.class, "foo.bar"));
            // NULL is ok - fail("KULRICE-10677 - is this ok?");
        } catch (IllegalArgumentException e) {
            // IAE is not ok - KULRICE-10677 is this ok?
            throw e;
        }
    }

    @Test
    public void testPropertySplitPath() {
        String path = "foo.foo1.foo2";
        String[] splitPaths = ObjectPropertyUtils.splitPropertyPath(path);

        assertEquals(3, splitPaths.length);
        assertEquals("foo", splitPaths[0]);
        assertEquals("foo1", splitPaths[1]);
        assertEquals("foo2", splitPaths[2]);

        path = "foo[1]";
        splitPaths = ObjectPropertyUtils.splitPropertyPath(path);

        assertEquals(1, splitPaths.length);
        assertEquals("foo[1]", splitPaths[0]);

        path = "foo.foo1['key.nested'].foo2";
        splitPaths = ObjectPropertyUtils.splitPropertyPath(path);

        assertEquals(3, splitPaths.length);
        assertEquals("foo", splitPaths[0]);
        assertEquals("foo1['key.nested']", splitPaths[1]);
        assertEquals("foo2", splitPaths[2]);

        path = "foo.foo1['key.nested'].foo2.foo3['key.nest.nest'].foo4";
        splitPaths = ObjectPropertyUtils.splitPropertyPath(path);

        assertEquals(5, splitPaths.length);
        assertEquals("foo", splitPaths[0]);
        assertEquals("foo1['key.nested']", splitPaths[1]);
        assertEquals("foo2", splitPaths[2]);
        assertEquals("foo3['key.nest.nest']", splitPaths[3]);
        assertEquals("foo4", splitPaths[4]);
    }

}
