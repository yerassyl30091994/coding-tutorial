import io.kotlintest.matchers.collections.shouldContain
import io.kotlintest.matchers.numerics.*
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

    feature("mapOf") {
        scenario("returns count fruits") {
            val list = listOf("banana","apple","orange","apple")
            fruitsCount(list) shouldBe mapOf("banana" to 1,"orange" to 1,"apple" to 2)
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
    val list = listOf(5, 6, 7, 8, 9)
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

//Home work  Collection(mapOf)
fun fruitsCount(listFruits: List<String>): Map<String, Int> {
    val appleCount = listFruits.filter { it.contains("apple") }.count()
    val orangeCount = listFruits.filter { it.contains("orange") }.count()
    val bananaCount = listFruits.filter { it.contains("banana") }.count()
    val map = mapOf("apple" to appleCount, "orange" to orangeCount, "banana" to bananaCount)

    return map
}

//or

//fun fruitsCount(list: List<String>): Map<String, Int> {
//    val list = listOf("banana", "apple", "orange", "apple", "banana", "apple", "orange", "apple")
//    val map = mapOf("banana" to 2, "apple" to 4, "orange" to 2)
//
//    return map
//}