package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return true when cars exist"

    request {
        method GET()
        url '/api/v1/cars/exist/2'
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body(Boolean.TRUE.toString())
    }
}