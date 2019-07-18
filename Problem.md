Problem Breakdown
=================

Using http4k http framework, we are...

1. Start a server (fake server in test like in `ProductSearchFunctionalTest`)
2. (Actually the `ProductFinderTest` is probably better for this)
3. Send a request to an endpoint (probably of type HttpHandler)
4. Get an OK response (with CORS header) and a data field in body
5. 


`AtgProductFinder` is central function to this 



This is happening... 


## Recreate CatchLensFailure() 

Recreate CatchLensFailure (not working) issue in small project to ask http4k question

PROBLEM:

- When product is not found it breaks out of the server filters flow, so does not add the CORS header into the response

- This happens when setting CORS policy in Endpoints.kt


To set up basic project…

- Need one or two http4k routes (see “livenessRoutes” for example)
- Need a server to to serve some fake data (ie products)
- Try to access a product that doesn’t exist


- Need to serialise and deserialise some json product data -> so we can test the Lens stuff

The data can just be in the tests - it doesn’t need to look too ‘real’
- But need to use Lens functionality to test our problem with `CatchLensFailure`