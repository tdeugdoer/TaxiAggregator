package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return average rating"

    request {
        method GET()
        url '/api/v1/ratings/avg/0ef7de40-7c91-46b7-8ecb-d68f7eb28ed5'
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body(Double.toString(3.0))
    }
}
