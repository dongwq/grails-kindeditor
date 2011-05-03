package com.dongwq

import grails.test.*

class KindeditorTagLibTests extends TagLibUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testResourceTag() {
		
		taglib.resource()
		
		assertTrue "ok", taglib.out.toString()
		println taglib.out.toString()
    }
}
