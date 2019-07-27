package product.search

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import org.http4k.client.OkHttp
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.lens.Path
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object PlaygroundServerTest : Spek({

    val client = OkHttp()
    val server = PlaygroundServer(0)

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
            val queryParam = "1234"
            val response = client(Request(GET, "http://localhost:${server.port()}/products?productid=$queryParam"))
            assertThat(response.body.toString()).contains(queryParam)
        }

        it("responds to '/products/:id'") {
            val productId = "1234"
            val response = client(Request(GET, "http://localhost:${server.port()}/products/$productId"))
            assertThat(response.body.toString()).contains(productId)
        }

        it("returns product description") {
            val productId = "1234"
            val productNameResponse = "Magic trousers"
            val response = client(Request(GET, "http://localhost:${server.port()}/products/$productId"))
            assertThat(response.body.toString()).contains(productNameResponse)
        }

    }

})