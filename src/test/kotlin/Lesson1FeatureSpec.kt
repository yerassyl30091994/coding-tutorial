import com.github.javafaker.Bool
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.matchers.collections.shouldContain
import io.kotlintest.matchers.numerics.*
import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.shouldNot
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.FeatureSpec
import java.util.*
import kotlin.math.min

class BasicsFeatureSpec : FeatureSpec({
    feature("functions") {
        scenario("sum1 and sum2 works the same") {
            val a = 1
            val b = 2
            sum1(a, b) shouldBe 3
            sum1(a, b) shouldBeLessThan 4
            sum1(0, 3) shouldBe 3
            sum1(-1, 1) shouldBe 0


            // Add greater less checks!
            sum1(a, b) shouldBeGreaterThan 2 // больше
            sum1(a, b) shouldBeGreaterThanOrEqual 2 // больше или равно
            sum1(a, b) shouldNotBeGreaterThanOrEqual 4 // меньше
            sum1(a, b) shouldBeInRange 1..5 // диапазон
            sum1(a, b) shouldNotBeInRange 4..10 // не входит в диапазон
            sum1(a, b) shouldNotBe 1

        }
    }

    feature("variables") {

        val readOnly = 11
        var reassignable = 3
//            scenario("val can not be reassigned") {
//                readOnly = 12
//            }

        scenario("can be reassigned") {
            reassignable = 6
            reassignable shouldBe 6
        }
    }

    feature("strings") {
        val toge = "toge"
        val ther = "ther"
        val together = "together"

        scenario("concatenation works") {
            toge + ther shouldBe together
        }

        scenario("string interpolation works") {
            "$toge$ther" shouldBe together
        }

        scenario("is not empty") {
            together.isNotBlank() shouldBe true
        }
    }

    feature("conditional expressions") {
        val max = 100
        val min = 0

        scenario("returns max") {
            maxOf(min, max) shouldBe max
        }

        scenario("") {
            minOf(1, 2)
        }
    }

    // Write minOff function
    feature("finding the minimum value") {
        scenario("returns min") {
            val list = listOf(5, 6, 7, 8, 9)
            minOf1(list) shouldBe 5
        }
    }

    feature("Method releaseTesting") {
        var day = 2
        val qa = QA(day, "Yerassyl", "QA Mobile")
        val arr = arrayListOf("registration", "redesign", "addButton", "addEditText", "addTextView", "addImageView", "WebView")

        scenario("day for release equals negative value") {
            day = -2
            val qa = QA(day, "Yerassyl", "QA Mobile")
            qa.releaseTesting(arr) shouldBe false
        }

        scenario("0 days for release") {
            day = 0
            val qa = QA(day, "Yerassyl", "QA Mobile")
            qa.releaseTesting(arr) shouldBe false
        }

        scenario("day for test < count of features for release") {
            qa.releaseTesting(arr) shouldBe false
        }

        scenario("day for test = count of features for release") {
            day = 7
            val qa = QA(day, "Yerassyl", "QA Mobile")
            qa.releaseTesting(arr) shouldBe true
        }

        scenario("day for test > count of features for release") {
            day = 10
            val qa = QA(day, "Yerassyl", "QA Mobile")
            qa.releaseTesting(arr) shouldBe true
        }

        scenario("Type of result") {
            qa.releaseTesting(arr).shouldBeTypeOf<Boolean>()
        }
    }

    feature("mapOf") {
        scenario("returns count fruits") {
            val list = listOf("banana", "apple", "orange", "apple", "banana", "orange", "apple")

            list.count() shouldBe 7
            fruitsCount(list).get("banana") shouldBe 2
            fruitsCount(list).get("orange") shouldBe 2
            fruitsCount(list).get("apple") shouldBe 3
        }
    }


    feature("vararg") {
        scenario("") {

            minOf2() shouldBe null
        }
    }

    feature("rotation") {
        scenario("") {

            areRotations("swift","ftswi") shouldBe true
            areRotations("swift","FTswi") shouldBe true
            areRotations("swift","FTswi").shouldBeTypeOf<Boolean>()

        }
    }

    feature("unique letters") {
        scenario("") {

            uniqueLetter("double") shouldBe true
            uniqueLetter("integer") shouldBe false
            uniqueLetter("double").shouldBeTypeOf<Boolean>()

            uniqueLetter1("double") shouldBe true
            uniqueLetter1("integer") shouldBe false
            uniqueLetter1("double").shouldBeTypeOf<Boolean>()

            uniqueLetter2("double") shouldBe true
            uniqueLetter2("integer") shouldBe false
            uniqueLetter2("double").shouldBeTypeOf<Boolean>()
        }
    }


    feature("when expression") {
        describe(1) shouldBe "One"
        describe("hello") shouldBe "Unknown"
        // Add other checks
    }

    feature("collections") {
        val fruits = arrayListOf("Apple", "Orange", "Grapes", "Cherry")

        scenario("") {
            fruits.count() shouldBe 4
            fruits shouldContain "Apple"

            count(fruits) shouldBe 4
        }
    }
})

fun sum1(a: Int, b: Int): Int {
    return a + b
}

fun sum2(a: Int, b: Int) = a + b

fun maxOf(a: Int, b: Int) = if (a > b) a else b


fun minOf(a: Double, b: Double): Any {
    if (a < b) return a
    return Unit

}

//Home work - minOf function

fun minOf1(list: List<Int>): Int? {
    var min = list[0]
    for (i in list) {
        if (min > i) min = i
    }
    return min
}


fun describe(obj: Any): String =
        when (obj) {
            1 -> "One"
            "Hello" -> "Greeting"
            is Long -> "Long"
            !is String -> "Not a string"
            else -> "Unknown"
        }

fun count(list: ArrayList<String>): Int {
    var counter = 0
    for (i in list) {
        counter += 1
    }
    return counter
}

//Home work 2
fun fruitsCount(list: List<String>): Map<String, Int> {
    val map = mutableMapOf<String, Int>()
    for (fruit in list) {
        if (map.containsKey(fruit)) {
            map[fruit] = map.getValue(fruit) + 1
        } else {
            map[fruit] = 1
        }
    }
    return map
}

//Home work 3 (vararg)
fun minOf2(vararg values: Int): Int? {
    var min: Int = 0

    if (values.size < 1) {
        println("Нету входных элементов")
        return null
    } else {
        min = values[0]

        for (someValue in values) {
            if (min > someValue) {
                min = someValue
            }
        }
    }
    return min
}


//Home work 3 *
open class Engineer(val name: String, val department: String)

class QA(val day: Int, name: String, department: String) : Engineer(name, department) {
    fun releaseTesting(arrayList: ArrayList<String>): Boolean {
        val amount = arrayList.size

        return amount / 2 < this.day
    }
}


// Home work
fun areRotations(str1: String, str2: String): Boolean {

    return str1.length == str2.length &&
            (str1 + str1).indexOf(str2, 0, ignoreCase = true) != -1
}


fun uniqueLetter(str: String):Boolean {
    val charStr = str.toCharArray()
    for (i in charStr){
        if(charStr.count{it==i}>1){
            return false
        }
    }
    return true
}

fun uniqueLetter1(str: String): Boolean {
    for (i in 0 until str.length) {
        if (str.substring(i + 1).contains(str[i]))
            return false
    }
    return true
}


fun uniqueLetter2(str: String): Boolean {
    for (i in 0 until str.length - 1) {
        for (j in i + 1 until str.length)
            if (str[i] == str[j])
                return false
    }
    return true
}

