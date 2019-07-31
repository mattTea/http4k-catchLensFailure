package product.search

import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.ServerFilters.CatchLensFailure
import org.http4k.lens.Path
import org.http4k.lens.Query
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Http4kServer
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun PlaygroundServer(port: Int): Http4kServer = PlaygroundApp().asServer(Jetty(port))

fun PlaygroundApp(): HttpHandler = CatchLensFailure.then(
    routes(
        "/ping" bind GET to { Response(OK) },

        "/products" bind GET to { request: Request ->
            val productId = Query.optional("productid")(request)

            Response(OK).body(productId.toString())
        },

        "/products/{id}" bind GET to { request: Request ->
            val id = Path.of("id")
            val pathId = id.extract(request)
            val matchedProduct = mutableListOf<Product>()
            var response : Response

            for (product in products) if (product.id.toString() == pathId) {
                matchedProduct.add(product)
            }

            response = if (matchedProduct.size == 0) {
                Response(NOT_FOUND)
            } else {
                Response(OK).with(Product.format of(matchedProduct[0]))
            }
            response
        }
    )
)

//TODO implement CatchLensFailure as per merchandiser to understand behaviour