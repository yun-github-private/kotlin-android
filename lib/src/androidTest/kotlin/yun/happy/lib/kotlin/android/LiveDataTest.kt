package yun.happy.lib.kotlin.android

import android.arch.lifecycle.MutableLiveData
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class LiveDataTest {

    @Test
    fun map() {
        // change value test
        val data = MutableLiveData<Int>()
        val countDownLatch = CountDownLatch(1)
        data.map { it + 1 }
                .map { it * 10 }
                .observeForever {
                    Assert.assertEquals(20, it)
                    countDownLatch.countDown()
                }
        data.postValue(1)
        countDownLatch.await(1, TimeUnit.SECONDS)
        Assert.assertEquals(0, countDownLatch.count)
    }

    @Test
    fun switchMap() {
        // convert test
        val data = MutableLiveData<Int>()
        val countDownLatch = CountDownLatch(1)
        val afterData = data.switchMap { MutableLiveData<String>().apply { postValue("text:$it") } }
        afterData.observeForever {
            Assert.assertEquals("text:1", it)
            countDownLatch.countDown()
        }
        data.postValue(1)
        countDownLatch.await(1, TimeUnit.SECONDS)
        Assert.assertEquals(0, countDownLatch.count)
    }

    @Test
    fun filter() {
        // exclude test
        val data1 = MutableLiveData<Int>()
        val countDownLatch1 = CountDownLatch(1)
        data1.filter { it % 2 == 0 }.observeForever { countDownLatch1.countDown() }
        data1.postValue(1)
        countDownLatch1.await(1, TimeUnit.SECONDS)
        Assert.assertEquals(1, countDownLatch1.count)

        // include test
        val data2 = MutableLiveData<Int>()
        val countDownLatch2 = CountDownLatch(1)
        data2.filter { it % 2 == 0 }.observeForever { countDownLatch2.countDown() }
        data2.postValue(2)
        countDownLatch2.await()
        Assert.assertEquals(0, countDownLatch2.count)
    }

    @Test
    fun nonNull() {
        // exclude test
        val data1 = MutableLiveData<Int>()
        val countDownLatch1 = CountDownLatch(1)
        data1.postValue(null)
        data1.nonNull().observeForever { countDownLatch1.countDown() }
        countDownLatch1.await(1, TimeUnit.SECONDS)
        Assert.assertEquals(1, countDownLatch1.count)

        // include test
        val data2 = MutableLiveData<Int>()
        val countDownLatch2 = CountDownLatch(1)
        data2.postValue(1)
        data2.nonNull().observeForever { countDownLatch2.countDown() }
        countDownLatch2.await(1, TimeUnit.SECONDS)
        Assert.assertEquals(0, countDownLatch2.count)
    }

    @Test
    fun distinct() {
        // exclude test
        val data1 = MutableLiveData<Int>()
        val countDownLatch1 = CountDownLatch(3)
        data1.distinct().observeForever { countDownLatch1.countDown() }
        data1.postValue(1)
        Thread.sleep(1000)
        data1.postValue(1)
        Thread.sleep(1000)
        data1.postValue(1)
        countDownLatch1.await(1, TimeUnit.SECONDS)
        Assert.assertEquals(2, countDownLatch1.count)

        // include test
        val data2 = MutableLiveData<Int>()
        val countDownLatch2 = CountDownLatch(3)
        data2.distinct().observeForever { countDownLatch2.countDown() }
        data2.postValue(1)
        Thread.sleep(1000)
        data2.postValue(2)
        Thread.sleep(1000)
        data2.postValue(1)
        countDownLatch1.await(1, TimeUnit.SECONDS)
        Assert.assertEquals(0, countDownLatch2.count)
    }
}
