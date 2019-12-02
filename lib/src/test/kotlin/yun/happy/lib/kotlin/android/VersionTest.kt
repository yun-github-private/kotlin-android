package yun.happy.lib.kotlin.android

import org.junit.Assert
import org.junit.Test
import yun.lib.kotlin.android.Version

class VersionTest {

    @Test
    fun testAll() {

        //check invalid
        Assert.assertNull(Version.of("1.0.beta"))
        Assert.assertNull(Version.of(""))
        Assert.assertNull(Version.of(" "))
        Assert.assertNull(Version.of("1.0.0.0.0"))

        //check valid
        Assert.assertNotNull(Version.of("1"))
        Assert.assertNotNull(Version.of("1.0"))
        Assert.assertNotNull(Version.of("1.0.0"))
        Assert.assertNotNull(Version.of("1.0.0.0"))

        //check equals
        Assert.assertNotEquals(Version.of("1.0.0"), Version.of("1.0.1"))
        Assert.assertNotEquals(Version.of("1.0.0"), Version.of("1.1.0"))
        Assert.assertNotEquals(Version.of("1.0.0"), Version.of("2.0.0"))
        Assert.assertNotEquals(Version.of("1.0"), Version.of("1.0.1"))
        Assert.assertEquals(Version.of("1.0.0"), Version.of("1.0"))

        //check operators
        Assert.assertTrue(Version.of("1.0.0")!! < Version.of("1.0.1")!!)
        Assert.assertTrue(Version.of("1.0.0")!! < Version.of("1.1.0")!!)
        Assert.assertTrue(Version.of("1.0.0")!! < Version.of("2.0.0")!!)
        Assert.assertTrue(Version.of("1.0.0")!! <= Version.of("1.0.0")!!)
        Assert.assertTrue(Version.of("1.0.0")!! <= Version.of("1.0.1")!!)
        Assert.assertTrue(Version.of("1.0.0")!! <= Version.of("1.1.0")!!)
        Assert.assertTrue(Version.of("1.0.0")!! <= Version.of("2.0.0")!!)
        Assert.assertTrue(Version.of("1.0.0")!! == Version.of("1.0.0")!!)
        Assert.assertTrue(Version.of("1.0.0")!! != Version.of("2.0.0")!!)

        //check length & operators
        Assert.assertTrue(Version.of("1.0")!! == Version.of("1.0.0")!!)
        Assert.assertTrue(Version.of("1.0")!! < Version.of("1.0.1")!!)
        Assert.assertTrue(Version.of("2.0")!! > Version.of("1.0.1")!!)

        //check version string
        Assert.assertEquals(Version.of("1.2.3")?.name, "1.2.3")
        Assert.assertEquals(Version.of("1.0")?.name, "1.0")
    }
}