package yun.happy.lib.kotlin.android

import android.arch.lifecycle.MutableLiveData
import junit.framework.Assert
import org.junit.Test

class ExampleTest {

    @Test
    fun all() {
        val liveData = MutableLiveData<Int>()
        liveData.map { it + 1 }
                .map { it * 10 }
                .observeForever {
                    Assert.assertEquals(20, it)
                }
        liveData.postValue(1)
    }
}