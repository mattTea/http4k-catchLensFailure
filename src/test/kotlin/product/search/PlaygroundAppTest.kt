package product.search

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import org.http4k.client.OkHttp
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object PlaygroundServerTest : Spek({

    val client = OkHttp()
    val server = PlaygroundServer(0)

    val productId = "1234"
    val productNameResponse = "Magic trousers"

    val secondProductId = "1235"
    val secondProductNameResponse = "Shiny saucepan"


    beforeEachTest {
        server.start()
    }

    afterEachTest {
        server.stop()
    }

    describe("Server") {

        it("responds to '/ping'") {
            val response = client(Request(GET, "http://localhost:${server.port()}/ping"))
            assertThat(response.status).isEqualTo(OK)
        }

        it("responds to '/products?productid'") {
            val response = client(Request(GET, "http://localhost:${server.port()}/products?productid=$productId"))
            assertThat(response.body.toString()).contains(productId)
        }

        it("responds to '/products/:id' and returns product id") {
            val response = client(Request(GET, "http://localhost:${server.port()}/products/$productId"))
            assertThat(response.body.toString()).contains(productId)
        }

        it("responds to '/products/1234' and returns correct product description") {
            val response = client(Request(GET, "http://localhost:${server.port()}/products/$productId"))
            assertThat(response.body.toString()).contains(productNameResponse)
        }

        it("responds to '/products/1235' and returns correct product description") {
            val response = client(Request(GET, "http://localhost:${server.port()}/products/$secondProductId"))
            assertThat(response.body.toString()).contains(secondProductNameResponse)
        }

        it("returns 'Product not found' for incorrect productId") {
            val notFoundResponse = client(Request(GET, "http://localhost:${server.port()}/products/unknown-product"))
            assertThat(notFoundResponse.status).isEqualTo(NOT_FOUND)
        }

    }

})