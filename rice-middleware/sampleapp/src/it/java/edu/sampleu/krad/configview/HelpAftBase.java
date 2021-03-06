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
package edu.sampleu.krad.configview;

import org.kuali.rice.testtools.common.JiraAwareFailable;
import org.kuali.rice.testtools.selenium.WebDriverLegacyITBase;

/**
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public abstract class HelpAftBase extends WebDriverLegacyITBase {

    /**
     * /kr-krad/configuration-test-view-uif-controller?viewId=ConfigurationTestView&methodToCall=start&pageId=ConfigurationTestView-Help-Page
     */
    public static final String BOOKMARK_URL = "/kr-krad/configuration-test-view-uif-controller?viewId=ConfigurationTestView&methodToCall=start&pageId=ConfigurationTestView-Help-Page";
   
    @Override
    protected String getBookmarkUrl() {
        return BOOKMARK_URL;
    }

    protected void navigation() throws Exception {
        waitAndClickKRAD();
        waitAndClickByXpath(CONFIGURATION_VIEW_XPATH);
        switchToWindow(CONFIGURATION_VIEW_WINDOW_TITLE);
        waitAndClickByLinkText("Help");
        Thread.sleep(5000);
    }

    protected void testHelpNav(JiraAwareFailable failable) throws Exception {
        navigation();
        testViewHelp();
        testPageHelp();
        testTooltipHelp();
        testDisplayOnlyTooltipHelp();
        testMissingTooltipHelp();
        passed();
    }

    protected void testHelpBookmark(JiraAwareFailable failable) throws Exception {
        testViewHelp();
        testPageHelp();
        testTooltipHelp();
        testDisplayOnlyTooltipHelp();
        testMissingTooltipHelp();
        passed();
    }


    /**
     * Test the tooltip and external help on the page
     */
    protected void testPageHelp() throws Exception {
        // test tooltip help
        fireMouseOverEventByXpath("//a/span[@class='uif-headerText-span']");
        waitForTextPresent("Sample text for section help - tooltip help");

        // test external help
        waitAndClickByXpath("//input[@alt='Help for Configuration Test View']");
        Thread.sleep(5000);
        switchToWindow("Kuali Foundation");
        Thread.sleep(5000);
        switchToWindow(CONFIGURATION_VIEW_WINDOW_TITLE);
    }

    /**
     * Test the tooltip help on the section and fields
     */
    protected void testTooltipHelp() throws Exception {
        // verify that no tooltips are displayed initially
        if (isElementPresentByXpath("//td[contains(text(),'Sample text for section help - tooltip help')]")) {
            assertFalse(isVisibleByXpath("//td[contains(text(),'Sample text for section help - tooltip help')]"));
        }

        if (isElementPresentByXpath("//td[contains(text(),'Sample text for field help - label left')]")) {
            assertFalse(isVisibleByXpath("//td[contains(text(),'Sample text for field help - label left')]"));
        }

        if (isElementPresentByXpath("//td[contains(text(),'Sample text for field help - label right')]")) {
            assertFalse(isVisibleByXpath("//td[contains(text(),'Sample text for field help - label right')]"));
        }

        if (isElementPresentByXpath("//td[contains(text(),'Sample text for field help - label top')]")) {
            assertFalse(isVisibleByXpath("//td[contains(text(),'Sample text for field help - label top')]"));
        }

        if (isElementPresentByXpath("//td[contains(text(),'Sample text for standalone help widget tooltip which will never be rendered')]")) {
            assertFalse(isVisibleByXpath("//td[contains(text(),'Sample text for standalone help widget tooltip which will never be rendered')]"));
        }

        if (isElementPresentByXpath("//td[contains(text(),'Sample text for field help - there is also a tooltip on the label but it is overridden by the help tooltip')]")) {
            assertFalse(isVisibleByXpath("//td[contains(text(),'Sample text for field help - there is also a tooltip on the label but it is overridden by the help tooltip')]"));
        }

        if (isElementPresentByXpath("//td[contains(text(),'Sample text for label tooltip - this will not be rendered as it is overridden by the help tooltip')]")) {
            assertFalse(isVisibleByXpath("//td[contains(text(),'Sample text for label tooltip - this will not be rendered as it is overridden by the help tooltip')]"));
        }

        if (isElementPresentByXpath("//td[contains(text(),'Sample text for field help - there is also an on-focus tooltip')]")) {
            assertFalse(isVisibleByXpath("//td[contains(text(),'Sample text for field help - there is also an on-focus tooltip')]"));
        }

        if (isElementPresentByXpath("//td[contains(text(),'Sample text for on-focus event tooltip')]")) {
            assertFalse(isVisibleByXpath("//td[contains(text(),'Sample text for on-focus event tooltip')]"));
        }

        if (isElementPresentByXpath("//td[contains(text(),'Sample text for check box help')]")) {
            assertFalse(isVisibleByXpath("//td[contains(text(),'Sample text for check box help')]"));
        }

        // test tooltip help of section header
        fireMouseOverEventByXpath("//section[@id='ConfigurationTestView-Help-Section1']/header/h3[@class='uif-headerText']");
        Thread.sleep(500);
        assertTrue(isVisibleByXpath("//td[contains(text(),'Sample text for section help - tooltip help')]"));

        // verify that no external help exist
        assertFalse(isElementPresent("#ConfigurationTestView-Help-Section1 input.uif-helpImage"));

        // test tooltip help of field with label to the left
        fireMouseOverEventByXpath("//label[@id='field-label-left_label']");
        Thread.sleep(3000);
        assertTrue(isVisibleByXpath("//td[contains(text(),'Sample text for field help - label left')]"));

        // test tooltip help of field with label to the right
        fireMouseOverEventByXpath("//label[@id='field-label-right_label']");
        Thread.sleep(3000);
        assertTrue(isVisibleByXpath("//td[contains(text(),'Sample text for field help - label righ')]"));

        // test tooltip help of field with label to the top
        fireMouseOverEventByXpath("//label[@id='field-label-top_label']");
        Thread.sleep(3000);
        assertTrue(isVisibleByXpath("//td[contains(text(),'Sample text for field help - label top')]"));

        // verify that standalone help with tooltip is not rendered
        assertFalse(isElementPresentByXpath("//*[@id='standalone-help-not-rendered']"));

        // test tooltip help when it overrides a tooltip
        fireMouseOverEventByXpath("//label[@id='override-tooltip_label']");
        Thread.sleep(3000);
        assertTrue(isVisibleByXpath("//td[contains(text(),'Sample text for field help - there is also a tooltip on the label but it is overridden by the help tooltip')]"));
        if (isElementPresentByXpath("//td[contains(text(),'Sample text for label tooltip - this will not be rendered as it is overridden by the help tooltip')]")) {
            assertFalse(isVisibleByXpath("//td[contains(text(),'Sample text for label tooltip - this will not be rendered as it is overridden by the help tooltip')]"));
        }

        // test tooltip help in conjunction with a focus event tooltip
        fireMouseOverEventByXpath("//input[@id='on-focus-tooltip_control']");
        assertTrue(isVisibleByXpath("//td[contains(text(),'Sample text for on-focus event tooltip')]"));
        fireMouseOverEventByXpath("//label[@id='on-focus-tooltip_label']");
        assertTrue(isVisibleByXpath("//td[contains(text(),'Sample text for field help - there is also an on-focus tooltip')]"));

        // test tooltip help against a check box - help contains html
        fireMouseOverEventByXpath("//label[@id='checkbox_label']");
        assertTrue(isVisibleByXpath("//td[contains(text(),'Sample text for check box help')]"));
    }

    /**
     * Test the tooltip help on the sub-section and fields that are display only
     */
    protected void testDisplayOnlyTooltipHelp() throws Exception {

        // test tooltip help of sub-section header
        fireMouseOverEventByXpath("//section[@data-parent='ConfigurationTestView-Help-Section1']/header/h4");
        waitForElementPresentByXpath("//td[contains(text(),'Sample text for sub-section help')]");

        // test tooltip help of display only data field
        fireMouseOverEventByXpath("//label[@for='display-field_control']");
        waitForElementPresentByXpath("//td[contains(text(),'Sample text for read only field help')]");
    }

    /**
     * Test the tooltip help on the section and fields with no content
     */
    protected void testMissingTooltipHelp() throws Exception {

        // test tooltip help of section header
        fireMouseOverEventByXpath("//section[@id='ConfigurationTestView-Help-Section2']/div");
        assertFalse(isElementPresentByXpath("//div[@class='jquerybubblepopup jquerybubblepopup-black' and @style='opacity: 0; top: 627px; left: 2px; position: absolute; display: block;']"));

        // test tooltip help of field
        fireMouseOverEventByXpath("//label[@id='missing-tooltip-help_label']");
        assertFalse(isElementPresentByXpath("//div[@class='jquerybubblepopup jquerybubblepopup-black' and @style='opacity: 0; top: 627px; left: 2px; position: absolute; display: block;']"));
    }

    /**
     * Test the tooltip and external help on the view
     */
    protected void testViewHelp() throws Exception {
        // test tooltip help
        fireEvent("field102", "blur");
        fireMouseOverEventByXpath("//label[@id='field-label-left_label']");
        waitForTextPresent("Sample text for field help - label left");

        // test external help
        waitAndClickByXpath("//input[@alt='Help for Configuration Test View']");
        Thread.sleep(5000);
        switchToWindow("Kuali Foundation");
        Thread.sleep(5000);
        switchToWindow(CONFIGURATION_VIEW_WINDOW_TITLE);
    }
}
