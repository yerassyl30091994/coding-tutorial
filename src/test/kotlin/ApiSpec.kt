import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.moshi.responseObject
import io.kotlintest.shouldBe
import io.kotlintest.specs.FeatureSpec
import models.Post

const val POSTS_COUNT = 100

class ApiSpec : FeatureSpec({
    feature("posts") {
        FuelManager.instance.basePath = "http://localhost:3000"

        val postsCount = 106
        val newPost = Post(0.0, postsCount + 1.0, "new one", "new one")

        scenario("gets all posts").config(enabled = false) {
            val (request, response, result) = Fuel.get("/posts")
                    //.responseString()
                    .responseObject<List<Post>>()
            response.statusCode shouldBe 200
            //listOf(request, response, result).forEach { println(it) }
            result.component1()?.count() shouldBe postsCount
        }

        scenario("adds new post").config(enabled = false) {
            val (_, response, result) = "/posts"
                    .httpPost(listOf("userId" to newPost.userdId, "id" to newPost.id, "title" to newPost.title, "body" to newPost.body))
                    .responseObject<Post>()
            response.statusCode shouldBe 201
            result.component1() shouldBe newPost
        }

        scenario("get post").config(enabled = false) {
            val (_, response, result) = "/posts/${newPost.id}"
                    .httpGet()
                    .responseObject<Post>()
            response.statusCode shouldBe 200
            result.component1() shouldBe newPost
        }

        scenario("deletes the last post") {
            val (_, response, result) = "/posts/105.0"
                    .httpDelete()
                    .responseString()
            response.statusCode shouldBe 200
        }
    }
})